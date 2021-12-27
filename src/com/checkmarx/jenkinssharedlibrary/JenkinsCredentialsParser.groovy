package com.checkmarx.jenkinssharedlibrary

/* groovylint-disable NoDef */
/* groovylint-disable VariableTypeRequired */
/* groovylint-disable CompileStatic */
/* groovylint-disable UnnecessaryObjectReferences */
/* groovylint-disable MethodReturnTypeRequired */
/* groovylint-disable DuplicateNumberLiteral */

import hudson.util.Secret

/**
 * Class to retrieve CxSAST username and password from Jenkins secrets.
*/
class JenkinsCredentialsParser {

    def getSecret(String hashedSecret) {
        // return Secret.fromString(hashedSecret).encryptedValue
        return Secret.fromString(hashedSecret).plainText
    }

}
