#! /bin/bash

set -e

project_root="$GAUGE_PROJECT_ROOT"
default_build_dir="gauge_bin"
plugin_dir=$(pwd)
compile_dir="$gauge_custom_compile_dir"
TMP_DIR="$(dirname $(mktemp -u))/"

JAVA_CMD=java
JAVAC_CMD=javac

if [ ! -z "${gauge_java_home}" ]; then
    JAVA_CMD="${gauge_java_home}/bin/${JAVA_CMD}"
    JAVAC_CMD="${gauge_java_home}/bin/${JAVAC_CMD}"
elif [ ! -z "${JAVA_HOME}" ]; then
    JAVA_CMD="${JAVA_HOME}/bin/${JAVA_CMD}"
    JAVAC_CMD="${JAVA_HOME}/bin/${JAVAC_CMD}"
fi

REQUIRED_MAJOR_VERSION=9 # minimum java version supported
INSTALLED_JAVA_VERSION=$($JAVA_CMD -version 2>&1 | awk '/version [0-9]*/ {print $3;}')

# Remove double quotes, remove leading "1." if it exists and remove everything apart from the major version number.
INSTALLED_MAJOR_VERSION=$(echo $INSTALLED_JAVA_VERSION | sed -e 's/"//g' -e 's/^1\.//' -e 's/\..*//')

if (( INSTALLED_MAJOR_VERSION < REQUIRED_MAJOR_VERSION ))
then
    echo -e "This version of gauge-java plugin does not support Java versions < 1.9";
    echo -e "Please upgrade your Java version or use a version of gauge-java <= v0.7.4"
    exit 1;
fi

cd "$project_root"

function get_abs() {
    if [[ "$1" == /* ]]; then
        echo "$1"
    else
        echo "$(pwd)/$1"
    fi
}

function split_on_commas() {
    local IFS=,
    local LIST=($1)
    for word in "${LIST[@]}"; do
        echo "$word"
    done
}

function get_additional_path() {
    if [ ! -z "$1" ]; then
        cp=""
        for p in $(split_on_commas $1); do
            if [ -z ${cp} ]; then
                cp="$(get_abs $p)"
            else
                cp="${cp}:$(get_abs $p)"
            fi
        done
        echo "${cp}"
    else
        echo ""
    fi
}

function list_files() {
    dirs="src"
    if [ ! -z "$compile_dir" ]; then
        dirs="$compile_dir"
    fi
    for d in $(split_on_commas "$dirs"); do
        find "$(cd ${d}; pwd)" -name "*.java" | xargs -I{lin} echo \"{lin}\"
    done
}

function build_project() {
    rm -rf $default_build_dir
    mkdir -p $default_build_dir
    target_file="$TMP_DIR$RANDOM-$RANDOM.txt"
    echo $(list_files) > $target_file
    args="-encoding UTF-8 -d ${default_build_dir} @${target_file}"
    if [ ! -z  "$(sed '/^$/d' $target_file)" ]; then
        $JAVAC_CMD -cp "$class_path" $args
    fi
    rm $target_file
}

function add_class_path_required_for_execution() {
    # Add addlitional libs
    additional_path=$(get_additional_path "${gauge_additional_libs}")
    if [ ! -z "$additional_path" ]; then
        class_path="${class_path}:${additional_path}"
    fi

    # Add user specified class path
    custom_build_path="${gauge_custom_build_path}"
    if [ -z $custom_build_path ]; then
        custom_build_path="${GAUGE_CUSTOM_BUILD_PATH}"
    fi
    user_path=$(get_additional_path "${custom_build_path}")

    if [ ! -z "$user_path" ]; then
        class_path="${class_path}:${user_path}"
    else
        if [ "${SHOULD_BUILD_PROJECT}" != "false" ]; then
            build_project
        fi
        class_path="${class_path}:$(get_abs ${default_build_dir})"
    fi
}

function add_runner_in_classpath() {
    class_path="${plugin_dir}/*"
    class_path="${class_path}:${plugin_dir}/libs/*"
}
function remove_substr_from_string() {
    string=$1
    substr=$2
    echo ${string/$substr/}
}
function getInstalledGaugeJavaVersion() {
    versionInfo=$(gauge -v)
    for f in ${versionInfo/java /java}; do
        if [[ "$f" == *"java"* ]]; then
            java_version=$(remove_substr_from_string "$f" java )
            java_version=$(remove_substr_from_string "$java_version" "(" )
            java_version=$(remove_substr_from_string "$java_version" ")" )
            echo "$java_version"
        fi
    done
}

function extract_gauge_plugin_version() {
    IFS=' '
    pom_data=$1
    plugin_name=$2
    echo $pom_data |
    while read -r line; do
        if [[ "$line" == *"$plugin_name"* ]]; then
            is_gauge_plugin_version="true"
        elif [[ "$is_gauge_plugin_version" == "true" ]]; then
            line=$(remove_substr_from_string "$line" "<version>")
            line=$(remove_substr_from_string "$line" "</version>")
            echo ${line//[ ]/}
            is_gauge_plugin_version="false"
        fi
    done
    unset IFS
}

function validate_plugins_version() {
    pom_data=$(mvn help:effective-pom )
    installed_gauge_java=$(getInstalledGaugeJavaVersion)
    gauge_java=$(extract_gauge_plugin_version "$pom_data" "gauge-java")
    gauge_maven_plugin=$(extract_gauge_plugin_version "$pom_data" "gauge-maven-plugin")
    if [[ "$installed_gauge_java" != "$gauge_java" ]]; then
        echo "Installed version of gauge-java($installed_gauge_java) does not match with dependency gauge-java($gauge_java) specified in pom file."
    fi
    if [[ "$gauge_maven_plugin" < "1.4.3" ]]; then
        echo "Expected gauge-maven-plugin version to be 1.4.3 or greater."
    fi
}

function set_classpath() {
    if [ -z "${gauge_custom_classpath}" ]; then
        GAUGE_MAVEN_POM_FILE="${GAUGE_MAVEN_POM_FILE:-pom.xml}"
        GAUGE_GRADLE_BUILD_FILE="${GAUGE_GRADLE_BUILD_FILE:-build.gradle}"
        if test -f $GAUGE_MAVEN_POM_FILE; then
            validate_plugins_version
            class_path=$(mvn -q test-compile gauge:classpath)
        fi
        if test -f $GAUGE_GRADLE_BUILD_FILE; then
            class_path=$(./gradlew -q clean classpath)
        fi
    else
        class_path="$gauge_custom_classpath";
    fi
}
function start() {
    set_classpath
    if [ -z "${class_path}" ]; then
        add_runner_in_classpath
        add_class_path_required_for_execution
    fi
    args="-Dfile.encoding=UTF-8"
    if [ ! -z "${gauge_jvm_args}" ]; then
        args="${args} ${gauge_jvm_args}"
    fi

    if [ ! -z "${GAUGE_DEBUG_OPTS}" ]; then
        args="${args} -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=${GAUGE_DEBUG_OPTS},timeout=25000"
        echo -e "\nRunner Ready for Debugging"
    fi
    target_file="$TMP_DIR$RANDOM-$RANDOM.txt"
    echo "-cp \"${class_path}\" ${args} com.thoughtworks.gauge.GaugeRuntime --start" >$target_file
    $JAVA_CMD @$target_file
    rm $target_file
}

function init() {
    add_runner_in_classpath
    CLASSPATH="${class_path}" $JAVA_CMDs com.thoughtworks.gauge.GaugeRuntime --init
}

tasks=(init start)
if [[ " ${tasks[*]} " =~ $1 ]]; then
    $1
    exit 0
fi

echo Options: [init \| start]
