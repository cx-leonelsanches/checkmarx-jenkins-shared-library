#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable MethodReturnTypeRequired */

import com.checkmarx.jenkinssharedlibrary.RestClient

def call(String bearerToken, int projectId) {
    assert env.CX_SERVER_URL : 'Variable CX_SERVER_URL not set'

    RestClient restClient = new RestClient()
    return restClient.get("${env.CX_SERVER_URL}/cxrestapi/projects/${projectId}", bearerToken)
}
