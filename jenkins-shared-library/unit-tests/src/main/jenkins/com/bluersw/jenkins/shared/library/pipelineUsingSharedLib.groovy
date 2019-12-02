package com.bluersw.jenkins.shared.library

@Library('shared-library')
import com.bluersw.jenkins.shared.library.Utils

sh acme.name
acme.name = 'something'
sh acme.name

acme.caution('world')

sayHello 'World'
sayHello()

parallel(
        action1: {
            node() {
                def utils = new Utils()
                sh "${utils.gitTools()}"
                sh 'sleep 3'
                String json = libraryResource 'com/bluersw/jenkins/shared/library/request.json'
                println json
            }
        },
        action2: {
            node() {
                sh 'sleep 4'
                error 'message'
            }
        }
)