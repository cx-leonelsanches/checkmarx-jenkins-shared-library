#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable VariableTypeRequired */
/* groovylint-disable CompileStatic */
/* groovylint-disable UnnecessaryObjectReferences */
/* groovylint-disable MethodReturnTypeRequired */
/* groovylint-disable DuplicateNumberLiteral */

/*
function: GetProjectTeam(projectName) {
    if there is an environment variable projectTeam defined:
        use the environment variable
    else if the project already exists in checkmarx:
        fetch the team from checkmarx and re-use it
        # how multiple projects /w same team name will be handled
    else if a default can be used:
        use a default value of CxServer
    else
        look up the team from somewhere.. somehow
}
*/

import groovy.json.JsonSlurper

def getTeams(String bearerToken) {
    URL url = new URL("${env.CX_SERVER_URL}/cxrestapi/auth/teams")
    URLConnection conn = url.openConnection()

    String basicAuth = "Bearer ${bearerToken}"
    conn.setRequestProperty('Authorization', basicAuth)

    def getTeamsResponseCode = conn.responseCode

    if (getTeamsResponseCode == 200) {
        JsonSlurper jsonSlurper = new JsonSlurper()
        def getTeamsJsonObject = jsonSlurper.parseText(conn.inputStream.text)
        println(getTeamsJsonObject)
    }
}
