#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable MethodReturnTypeRequired */

import com.checkmarx.jenkinssharedlibrary.JenkinsCredentialsParser

def call() {
    JenkinsCredentialsParser jenkinsCredentialsParser = new JenkinsCredentialsParser()
    return jenkinsCredentialsParser.secret
}
