#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable VariableTypeRequired */
/* groovylint-disable CompileStatic */
/* groovylint-disable UnnecessaryObjectReferences */
/* groovylint-disable MethodReturnTypeRequired */
/* groovylint-disable DuplicateNumberLiteral */

import groovy.json.JsonSlurper

def call(String bearerToken) {
    URL url = new URL("${env.CX_SERVER_URL}/cxrestapi/auth/teams")
    URLConnection conn = url.openConnection()

    String basicAuth = "Bearer ${bearerToken}"
    conn.setRequestProperty('Authorization', basicAuth)

    def getTeamsResponseCode = conn.responseCode

    if (getTeamsResponseCode == 200) {
        JsonSlurper jsonSlurper = new JsonSlurper()
        def getTeamsJsonObject = jsonSlurper.parseText(conn.inputStream.text)
        return getTeamsJsonObject
    }

    return [:]
}
