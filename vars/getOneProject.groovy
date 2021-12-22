#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable VariableTypeRequired */
/* groovylint-disable CompileStatic */
/* groovylint-disable UnnecessaryObjectReferences */
/* groovylint-disable MethodReturnTypeRequired */
/* groovylint-disable DuplicateNumberLiteral */

import groovy.json.JsonSlurper
import com.checkmarx.jenkinssharedlibrary.RestClient

def call(String bearerToken, int projectId) {
    RestClient restClient = new RestClient()
    return restClient.get("${env.CX_SERVER_URL}/cxrestapi/projects/${projectId}", bearerToken)
}
