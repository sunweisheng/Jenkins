#!/bin/sh
rm -rf ../../jenkins-shared-library/resources
rm -rf ../../jenkins-shared-library/src
rm -rf ../../jenkins-shared-library/vars
cp -r ./shared-library/resources ../../jenkins-shared-library/resources
cp -r ./shared-library/src ../../jenkins-shared-library/src
cp -r ./shared-library/vars ../../jenkins-shared-library/vars