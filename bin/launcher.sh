#! /bin/sh

set -ef

project_root="$GAUGE_PROJECT_ROOT"
default_build_dir="gauge_bin"
plugin_dir=$(pwd)
compile_dir="$gauge_custom_compile_dir"
TMP_DIR="$(dirname $(mktemp -u))"
MINIUM_GAUGE_MVN_VERSION="1.4.3"
MINIUM_GAUGE_GRADLE_VERSION="1.8.1"

JAVA_CMD=java
JAVAC_CMD=javac
GAUGE_MAVEN_POM_FILE="${GAUGE_MAVEN_POM_FILE:-pom.xml}"
GAUGE_GRADLE_BUILD_FILE="${GAUGE_GRADLE_BUILD_FILE:-build.gradle}"

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

if [ $INSTALLED_MAJOR_VERSION -lt $REQUIRED_MAJOR_VERSION ]
then
    echo -e "This version of gauge-java plugin does not support Java versions < 1.9";
    echo -e "Please upgrade your Java version or use a version of gauge-java <= v0.7.4"
    exit 1;
fi

cd "$project_root"

get_abs() {
    if [ $( expr $1 : "/*" ) -eq 0 ]; then
        echo "$(pwd)/$1"
    else
        echo "$1"
    fi
}

split_on_commas() {
    local IFS=,
    for word in $1; do
        echo "$word"
    done
}

get_additional_path() {
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

list_files() {
    dirs="src"
    if [ ! -z "$compile_dir" ]; then
        dirs="$compile_dir"
    fi
    for d in $(split_on_commas "$dirs"); do
        find "$(cd ${d}; pwd)" -name "*.java" | xargs -I{lin} echo \"{lin}\"
    done
}

build_project() {
    rm -rf $default_build_dir
    mkdir -p $default_build_dir
    target_file=$(create_temp_file)
    echo $(list_files) > $target_file
    args="-encoding UTF-8 -d ${default_build_dir} @${target_file}"
    if [ ! -z  "$(sed '/^$/d' $target_file)" ]; then
        $JAVAC_CMD -cp "$class_path" $args
    fi
    rm $target_file
}

add_class_path_required_for_execution() {
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

create_temp_file() {
    echo `mktemp $TMP_DIR/gauge-java-args-file.XXXXX`
}

add_runner_in_classpath() {
    class_path="${plugin_dir}/*"
    class_path="${class_path}:${plugin_dir}/libs/*"
}

remove_substr_from_string() {
    string=$1
    substr=$2
    echo ${string/$substr/}
}
getInstalledGaugeJavaVersion() {
    versionInfo=$(gauge -v)
    echo $versionInfo | sed 's/.*\(java\) (\([^()]*\)).*/\2/'
}

extract_gauge_maven_plugin_version() {
    pom_data=$(mvn help:effective-pom )
    echo $pom_data | sed 's/.*<artifactId>gauge-maven-plugin<\/artifactId> <version>\([0-9]*.[0-9]*.[0-9]*\).*/\1/'
}

extract_gauge_java_version() {
    mvn_dependency_data=$(mvn dependency:tree -Dincludes=com.thoughtworks.gauge:gauge-java)
    echo $mvn_dependency_data | sed 's/.*com.thoughtworks.gauge:gauge-java:jar:\([0-9.]*\).*/\1/'
}

validate_plugins_version() {
    installed_gauge_java=$(getInstalledGaugeJavaVersion)
    if [ "$1" = "maven" ]; then
        gauge_java_version=$(extract_gauge_java_version)
        gauge_maven_plugin=$(extract_gauge_maven_plugin_version)
        if [ $gauge_maven_plugin \< $MINIUM_GAUGE_MVN_VERSION ]; then
            echo "Expected gauge-maven-plugin version to be $MINIUM_GAUGE_MVN_VERSION or greater."
        fi
    else
        gradle_data=$(./gradlew -q dependencyInsight --dependency com.thoughtworks.gauge --configuration testCompileClasspath)
        gauge_java_version=$(echo $gradle_data | sed -E -e 's/variant.*//' -e 's/[^0-9.]//g' -e 's/\.+//')
        gauge_gradle_version=$(awk '!/org.gauge/{$0=""}1' build.gradle | sed -E -e 's/[^0-9.]//g' -e 's/\.+//')
        if [ $gauge_gradle_version \< $MINIUM_GAUGE_GRADLE_VERSION ]; then
            echo "Expected gauge-gradle-plugin version to be $MINIUM_GAUGE_GRADLE_VERSION or greater."
        fi
    fi

    if [ "$installed_gauge_java" != "$gauge_java_version" ]; then
        echo "Installed version of gauge-java($installed_gauge_java) does not match with dependency gauge-java($gauge_java_version) specified in $2 file."
    fi
}

set_classpath() {
    if [ -z "${gauge_custom_classpath}" ]; then
        if test -f $GAUGE_MAVEN_POM_FILE; then
            class_path=$(mvn -q test-compile gauge:classpath)
        fi
        if test -f $GAUGE_GRADLE_BUILD_FILE; then
            class_path=$(./gradlew -q clean classpath)
        fi
    else
        class_path="$gauge_custom_classpath";
    fi
}
start() {
    if test -f $GAUGE_MAVEN_POM_FILE; then
        validate_plugins_version "maven" "$GAUGE_MAVEN_POM_FILE"
    elif test -f $GAUGE_GRADLE_BUILD_FILE; then
        validate_plugins_version "gradle" "$GAUGE_GRADLE_BUILD_FILE"
    fi
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
    target_file=$(create_temp_file)
    echo "-cp \"${class_path}\" ${args} com.thoughtworks.gauge.GaugeRuntime --start" >$target_file
    $JAVA_CMD @$target_file
    rm $target_file
}

init() {
    add_runner_in_classpath
    CLASSPATH="${class_path}" $JAVA_CMD com.thoughtworks.gauge.GaugeRuntime --init
}

if [ "init" = $1 ]; then
    init
    exit 0
elif [ "start" = $1 ]; then
    start
    exit 0
fi

echo "Options: [init | start]"
