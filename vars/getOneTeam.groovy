#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable MethodReturnTypeRequired */

import com.checkmarx.jenkinssharedlibrary.RestClient

def call(String bearerToken, int teamId) {
    RestClient restClient = new RestClient()
    return restClient.get("${env.CX_SERVER_URL}/cxrestapi/auth/teams/${teamId}", bearerToken)
}
