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

import java.nio.charset.StandardCharsets
import groovy.json.JsonSlurper

def call() {
    URL url = new URL("${env.CX_SERVER_URL}/cxrestapi/auth/identity/connect/token")
    String urlParameters = "username=${env.CX_USERNAME}&" +
        "password=${env.CX_PASSWORD}&" +
        'grant_type=password&' +
        'scope=sast_rest_api&' +
        'client_id=resource_owner_client&' +
        'client_secret=014DF517-39D1-4453-B7B3-9930C563627C'

    byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8)
    int postDataLength = postData.length

    URLConnection conn = url.openConnection()
    conn.setRequestProperty('Content-Type', 'application/x-www-form-urlencoded')
    conn.setRequestProperty('Accept', 'application/json')
    conn.setRequestProperty('charset', 'UTF-8')
    conn.setRequestProperty('Content-Length', Integer.toString(postDataLength))

    conn.doOutput = true
    conn.doInput = true
    conn.instanceFollowRedirects = false
    conn.useCaches = false
    conn.requestMethod = 'POST'

    // Does not work for some reason
    /* try (DataOutputStream wr = new DataOutputStream(conn.outputStream)) {
        wr.write(postData)
    } */
    DataOutputStream wr = new DataOutputStream(conn.outputStream)
    wr.write(postData)
    wr.close()

    if (conn.responseCode == 200) {
        JsonSlurper jsonSlurper = new JsonSlurper()
        def jsonObject = jsonSlurper.parseText(conn.inputStream.text)

        return jsonObject
    }

    return [:]
}
