#!/bin/bash

set -e

function checkCommand() {
    command -v $1 >/dev/null 2>&1 || {
        echo >&2 "$1 is not installed, aborting."
        exit 1
    }
}

function build() {
    checkCommand "java"
    checkCommand "javac"
    checkCommand "mvn"
    rm -rf target libs
    echo "Packaging gauge-java..."
    mvn -q package
    echo "Copying  dependencies to libs..."
    mvn dependency:copy-dependencies -DoutputDirectory=libs -DexcludeTransitive=true -DincludeScope=runtime
    cp target/gauge-java-$(version).jar libs
}

function test() {
    mvn test
}

function version() {
    checkCommand "jq"
    echo $(cat java.json | jq -r .version)
}

function package() {
    build
    checkCommand "zip"
    rm -rf deploy artifacts
    mkdir -p deploy
    cp bin/launcher.sh deploy
    cp bin/launcher.cmd deploy
    cp java.json deploy
    cp -r libs deploy
    mkdir -p artifacts
    (export version=$(version) && cd deploy && zip -r ../artifacts/gauge-java-$version.zip .)
}

function install() {
    package
    gauge install java -f ./artifacts/gauge-java-$(version).zip
}

function uninstall() {
    gauge uninstall java -v $(version)
}

function forceinstall() {
    uninstall
    install
}

tasks=(build test package install uninstall forceinstall)
if [[ " ${tasks[@]} " =~ " $1 " ]]; then
    $1
    exit 0
fi

echo Options: [build \| test \| package \| install \| uninstall \| forceinstall]
