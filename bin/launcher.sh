#! /bin/bash

set -e

default_build_dir="gauge_bin"
class_path="$gauge_custom_classpath"
plugin_dir=$(pwd)
project_root="$GAUGE_PROJECT_ROOT"
compile_dir="$gauge_custom_compile_dir"

cd $project_root

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
        find "$(cd ${d}; pwd)" -name "*.java"
    done
}


function build_project() {
    rm -rf $default_build_dir
    mkdir $default_build_dir
    target_file="$TMPDIR/files.txt"
    echo $(list_files) > $target_file
    args="-encoding UTF-8 -d ${default_build_dir} @${target_file}"
    javac -cp $class_path $args
    rm $target_file
}

function add_class_path_requiresd_for_execution() {
    # Add addlitional libs
    additional_path=$(get_additional_path "${gauge_additional_libs}")
    if [ ! -z "$additional_path" ]; then
        class_path="${class_path}:${additional_path}"
    fi

    # Add user specified class path
    user_path=$(get_additional_path "${gauge_custom_build_path}")

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

function start() {
    if [ -z "${class_path}" ]; then
        add_runner_in_classpath
        add_class_path_requiresd_for_execution
    fi
    args="-Dfile.encoding=UTF-8"
    if [ ! -z "${gauge_jvm_args}"]; then
        args="${args} ${gauge_jvm_args}"
    fi

    if [ ! -z "${GAUGE_DEBUG_OPTS}" ]; then
        args="${args} -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=${GAUGE_DEBUG_OPTS},timeout=25000"
        echo -e "\nRunner Ready for Debugging"
    fi
    CLASSPATH=${class_path} java ${args} com.thoughtworks.gauge.GaugeRuntime --start
}

function init() {
    add_runner_in_classpath
    CLASSPATH=${class_path} java com.thoughtworks.gauge.GaugeRuntime --init
}

tasks=(init start)
if [[ " ${tasks[@]} " =~ " $1 " ]]; then
    $1
    exit 0
fi

echo Options: [init \| start]
