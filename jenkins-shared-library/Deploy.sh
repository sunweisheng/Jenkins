#!/bin/sh
rm -rf ../../../WorkCode/jenkins-shared-library/resources
rm -rf ../../../WorkCode/jenkins-shared-library/src
rm -rf ../../../WorkCode/jenkins-shared-library/vars
cp -r ./shared-library/resources ../../../WorkCode/jenkins-shared-library/resources
cp -r ./shared-library/src ../../../WorkCode/jenkins-shared-library/src
cp -r ./shared-library/vars ../../../WorkCode/jenkins-shared-library/vars

cd ../../../WorkCode/jenkins-shared-library/

git add .
git commit -m "部署共享类库"
git push origin

cd ../../HomeCode/Jenkins/jenkins-shared-library/