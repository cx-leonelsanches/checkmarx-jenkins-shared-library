package com.checkmarx.jenkinssharedlibrary

/* groovylint-disable NoDef */
/* groovylint-disable VariableTypeRequired */
/* groovylint-disable CompileStatic */
/* groovylint-disable UnnecessaryObjectReferences */
/* groovylint-disable MethodReturnTypeRequired */
/* groovylint-disable DuplicateNumberLiteral */
/* groovylint-disable DuplicateStringLiteral */

import groovy.json.JsonSlurperClassic

/**
 * Common class used for REST access to a CxSAST instance.
*/
class RestClient {

    def get(String endpoint, String bearerToken) {
        URL url = new URL(endpoint)
        URLConnection conn = url.openConnection()

        String basicAuth = "Bearer ${bearerToken}"
        conn.setRequestProperty('Authorization', basicAuth)

        if (conn.responseCode == 200) {
            JsonSlurperClassic jsonSlurper = new JsonSlurperClassic()
            return jsonSlurper.parseText(conn.inputStream.text)
        }

        return [:]
    }

    def post(String endpoint, String bearerToken, String jsonPayloadAsString) {
        URL url = new URL(endpoint)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()

        String basicAuth = "Bearer ${bearerToken}"

        conn.setRequestProperty('Authorization', basicAuth)
        conn.setRequestProperty('Content-Type', 'application/json; utf-8')

        conn.requestMethod = 'POST'
        conn.doOutput = true

        DataOutputStream wr = new DataOutputStream(conn.outputStream)
        byte[] jsonByteArray = jsonPayloadAsString.getBytes('utf-8')
        wr.write(jsonByteArray, 0, jsonByteArray.length)
        wr.close()

        if (conn.responseCode == 200) {
            JsonSlurperClassic jsonSlurper = new JsonSlurperClassic()
            return jsonSlurper.parseText(conn.inputStream.text)
        }

        return [:]
    }

}
