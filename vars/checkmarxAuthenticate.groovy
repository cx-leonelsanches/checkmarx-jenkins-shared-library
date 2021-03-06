#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable VariableTypeRequired */
/* groovylint-disable CompileStatic */
/* groovylint-disable UnnecessaryObjectReferences */
/* groovylint-disable MethodReturnTypeRequired */
/* groovylint-disable DuplicateNumberLiteral */

import java.nio.charset.StandardCharsets
import groovy.json.JsonSlurperClassic

def call(String serverUrl = env.CX_SERVER_URL, String userName = env.CX_CREDENTIALS_USR,
        String password = env.CX_CREDENTIALS_PSW, String scopes = 'sast_rest_api access_control_api') {

    assert serverUrl : 'Variable serverUrl not set'
    assert userName : 'Variable userName not set'
    assert password : 'Variable password not set'

    URL url = new URL("${serverUrl}/cxrestapi/auth/identity/connect/token")
    String urlParameters = "username=${userName}&" +
        "password=${password}&" +
        'grant_type=password&' +
        "scope=${scopes}&" +
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

    DataOutputStream wr = new DataOutputStream(conn.outputStream)
    wr.write(postData)
    wr.close()

    if (conn.responseCode == 200) {
        JsonSlurperClassic jsonSlurper = new JsonSlurperClassic()
        def jsonObject = jsonSlurper.parseText(conn.inputStream.text)

        return jsonObject
    }

    return [:]
}
