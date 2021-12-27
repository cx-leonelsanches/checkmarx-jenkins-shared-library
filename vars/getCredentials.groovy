#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable MethodReturnTypeRequired */

import com.checkmarx.jenkinssharedlibrary.JenkinsCredentialsParser

def call(String hashedSecret) {
    JenkinsCredentialsParser jenkinsCredentialsParser = new JenkinsCredentialsParser()
    return jenkinsCredentialsParser.getSecret(hashedSecret)
}
