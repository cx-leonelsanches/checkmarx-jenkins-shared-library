#!/usr/bin/env groovy

/* groovylint-disable NoDef */
/* groovylint-disable MethodReturnTypeRequired */
/* groovylint-disable NoWildcardImports */

import com.checkmarx.jenkinssharedlibrary.RestClient
import com.checkmarx.jenkinssharedlibrary.dtos.CreateTeamDto

import groovy.json.*

def call(String bearerToken, String name, Integer parentId) {
    assert env.CX_SERVER_URL : 'Variable CX_SERVER_URL not set'
    assert name?.trim() : 'Invalid team name'
    assert parentId > 0 : 'Invalid parent team id'

    CreateTeamDto createTeamDto = new CreateTeamDto()
    createTeamDto.name = name
    createTeamDto.parentId = parentId

    RestClient restClient = new RestClient()
    String jsonPayload = new JsonBuilder(createTeamDto).toPrettyString()

    return restClient.post("${env.CX_SERVER_URL}/cxrestapi/auth/teams", bearerToken, jsonPayload)
}
