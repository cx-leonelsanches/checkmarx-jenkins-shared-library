#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable VariableTypeRequired */
/* groovylint-disable CompileStatic */
/* groovylint-disable UnnecessaryObjectReferences */
/* groovylint-disable MethodReturnTypeRequired */
/* groovylint-disable DuplicateNumberLiteral */

import groovy.json.JsonSlurper

def getOneTeam(String bearerToken, int teamId) {
    URL url = new URL("${env.CX_SERVER_URL}/cxrestapi/auth/teams/${teamId}")
    URLConnection conn = url.openConnection()

    String basicAuth = "Bearer ${bearerToken}"
    conn.setRequestProperty('Authorization', basicAuth)

    def getOneTeamResponseCode = conn.responseCode

    if (getOneTeamResponseCode == 200) {
        JsonSlurper jsonSlurper = new JsonSlurper()
        def getOneTeamJsonObject = jsonSlurper.parseText(conn.inputStream.text)
        return getOneTeamJsonObject
    }

    return [:]
}
