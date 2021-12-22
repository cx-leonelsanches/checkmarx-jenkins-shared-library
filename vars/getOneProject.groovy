#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable VariableTypeRequired */
/* groovylint-disable CompileStatic */
/* groovylint-disable UnnecessaryObjectReferences */
/* groovylint-disable MethodReturnTypeRequired */
/* groovylint-disable DuplicateNumberLiteral */

import groovy.json.JsonSlurper

def call(String bearerToken, int projectId) {
    URL url = new URL("${env.CX_SERVER_URL}/cxrestapi/projects/${projectId}")
    URLConnection conn = url.openConnection()

    String basicAuth = "Bearer ${bearerToken}"
    conn.setRequestProperty('Authorization', basicAuth)

    def getOneProjectResponseCode = conn.responseCode

    if (getOneProjectResponseCode == 200) {
        JsonSlurper jsonSlurper = new JsonSlurper()
        def getOneProjectJsonObject = jsonSlurper.parseText(conn.inputStream.text)
        return getOneProjectJsonObject
    }

    return [:]
}
