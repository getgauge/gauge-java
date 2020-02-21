import os
import json
import re
import sys
from subprocess import check_output

file_name = "java.json"


def update_version():
    with open(file_name, 'r') as f:
        data = json.load(f)
        new_version = re.sub('\d$', lambda x: str(
            int(x.group(0)) + 1), data["version"])
        data["version"] = new_version

    os.remove(file_name)

    with open(file_name, 'w') as f:
        json.dump(data, f, indent=4)

    return new_version


def update_pom(version):
    c = "mvn versions:set-property -Dproperty=projectVersion -DnewVersion={0}"
    check_output(c.format(version), shell=True)
    os.remove("pom.xml.versionsBackup")


if __name__ == "__main__":
    version = update_version()
    update_pom(version)
    print(version)
