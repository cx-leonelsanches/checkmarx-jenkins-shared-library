#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable MethodReturnTypeRequired */

def call(String bearerToken, int projectId) {
    RestClient restClient = new RestClient()
    return restClient.get("${env.CX_SERVER_URL}/cxrestapi/projects/${projectId}", bearerToken)
}
