package com.checkmarx.jenkinssharedlibrary

/* groovylint-disable NoDef */
/* groovylint-disable VariableTypeRequired */
/* groovylint-disable CompileStatic */
/* groovylint-disable UnnecessaryObjectReferences */
/* groovylint-disable MethodReturnTypeRequired */
/* groovylint-disable DuplicateNumberLiteral */

import groovy.json.JsonSlurper

/**
 * Common class used for REST access to a CxSAST instance.
*/
class RestClient {

    def get(String endpoint, string bearerToken) {
        URL url = new URL(endpoint)
        URLConnection conn = url.openConnection()

        String basicAuth = "Bearer ${bearerToken}"
        conn.setRequestProperty('Authorization', basicAuth)

        if (conn.responseCode == 200) {
            JsonSlurper jsonSlurper = new JsonSlurper()
            return jsonSlurper.parseText(conn.inputStream.text)
        }

        return [:]
    }

}
