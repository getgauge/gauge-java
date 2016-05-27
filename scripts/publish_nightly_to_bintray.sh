#!/bin/sh
set -e

if [ -z "$PACKAGE" ]; then
  echo "PACKAGE is not set"
  exit 1
fi

if [ -z "$BINTRAY_USER" ]; then
  echo "BINTRAY_USER is not set"
  exit 1
fi

if [ -z "$BINTRAY_API_KEY" ]; then
  echo "BINTRAY_API_KEY is not set"
  exit 1
fi

if [ -z "$BINTRAY_PACKAGE" ]; then
    BINTRAY_PACKAGE="Nightly"
fi

if [ -z "$PASSPHRASE" ]; then
  echo "PASSPHRASE is not set"
  exit 1
fi

command -v jq >/dev/null 2>&1 || { echo >&2 "jq is not installed, aborting."; exit 1; }

PACKAGE_FILE_PREFIX="gauge-java"

function setVersion () {
    VERSION=$(ls $PACKAGE_FILE_PREFIX*.jar | head -1 | sed "s/\.[^\.]*$//" | sed "s/$PACKAGE_FILE_PREFIX-//" | sed "s/-javadoc//" | sed "s/-[a-z]*\.[a-z0-9_]*$//");
}

function bintrayUpload () {
    $(mv pom.xml gauge-java-$VERSION.pom)
    for i in `ls`; do
        URL="https://api.bintray.com/content/gauge/$PACKAGE/$BINTRAY_PACKAGE/$VERSION/com/thoughtworks/gauge/gauge-java/$VERSION/$i?publish=1&override=1"

        echo "Uploading to : $URL"

        RESPONSE_CODE=$(curl -H "X-GPG-PASSPHRASE: $PASSPHRASE" -T $i -u$BINTRAY_USER:$BINTRAY_API_KEY $URL -I -s -w "%{http_code}" -o /dev/null);
        if [[ "${RESPONSE_CODE:0:2}" != "20" ]]; then
            echo "Unable to upload, HTTP response code: $RESPONSE_CODE"
            exit 1
        fi
        echo "HTTP response code: $RESPONSE_CODE"
    done;
}

function bintraySetDownloads () {
    for i in `ls`; do
        URL="https://api.bintray.com/file_metadata/gauge/$PACKAGE/com/thoughtworks/gauge/gauge-java/$VERSION/$i"

        echo "Putting $i in $PACKAGE's download list"
        RESPONSE_CODE=$(curl -X PUT -d "{ \"list_in_downloads\": true }" -H "Content-Type: application/json" -u$BINTRAY_USER:$BINTRAY_API_KEY $URL -s -w "%{http_code}" -o /dev/null);
        if [[ "${RESPONSE_CODE:0:2}" != "20" ]]; then
            echo "Unable to put in download list, HTTP response code: $RESPONSE_CODE"
            exit 1
        fi
        echo "HTTP response code: $RESPONSE_CODE"
    done
}

function cleanOldNightlyVersions() {
    URL="https://api.bintray.com/packages/gauge/$PACKAGE/$BINTRAY_PACKAGE"
    versions=($(curl -X GET -H "Content-Type: application/json" -u$BINTRAY_USER:$BINTRAY_API_KEY $URL | jq -r '.versions'))
    for v in ${versions[@]:11}; do
        version=$(echo $v | sed -e 's/,//' -e 's/"//g')
        if [ $version !=  "]" ]; then
            echo "Deleting version: $version"
            DELETE_URL="$URL/versions/$version"
            RESPONSE_CODE=$(curl -X DELETE -H "Content-Type: application/json" -u$BINTRAY_USER:$BINTRAY_API_KEY $DELETE_URL -s -w "%{http_code}" -o /dev/null);
            if [[ "${RESPONSE_CODE:0:2}" != "20" ]]; then
                echo "Unable to delete version : $v, HTTP response code: $RESPONSE_CODE"
                exit 1
            fi
            echo "HTTP response code: $RESPONSE_CODE"
        fi
    done;
}

function snooze () {
    echo "\nSleeping for 30 seconds. Have a coffee..."
    sleep 30s;
    echo "Done sleeping\n"
}

function printMeta () {
    echo "Publishing package : $PACKAGE"
    echo "Version to be uploaded: $VERSION"
}

setVersion
setVersion
printMeta
bintrayUpload
snooze
bintraySetDownloads
cleanOldNightlyVersions