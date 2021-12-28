# Checkmarx Jenkins Shared Library

This is a collection of methods that we can use in a Jenkins Pipeline.

## Why a separate Shared Library? What about the Checkmarx Jenkins Plugin?

This library doesn't replace the Checkmarx Jenkins Plugin. It works more like a RESTful client in Jenkins for additional operations that the plugin does not support. For instance, changing the team to execute a scan. 

## Initial Setup

This can be configured if you have admin access to Jenkins. If you don't, please check "If you don't have admin rights in Jenkins" section at the end of this document.

In Jenkins, go to Manage Jenkins → Configure System. Under Global Pipeline Libraries, add a library with the following settings:

- Name: checkmarx-jenkins-shared-library
- Default version: main
- Retrieval method: Modern SCM
- Select the Git type
- Project repository: https://github.com/cx-leonelsanches/checkmarx-jenkins-shared-library.git

## Prerequisites

In order to work properly, the first method of the collection that should be called is `checkmarxAuthenticate`, that returns a JWT token, used by all the other methods.

`checkmarxAuthenticate` works with up to three parameters. All of them are optional in the method call: 

- `serverUrl`: Your CxSAST instance URL. For instance, https://mycompany.checkmarx.net
- `userName`: A login that can read at least projects and teams information
- `password`: The corresponding password for the login provided

If not provided, `checkmarxAuthenticate` will try to search for them in `env`, respectively: 

- `env.CX_SERVER_URL`
- `env.CX_CREDENTIALS_USR`
- `env.CX_CREDENTIALS_PSW`

If no parameters are provided in either way, the method will fail, specifying which parameter is missing.

You can set them either in your pipeline script or in your Jenkins configuration (Manage Jenkins → Configure System → Global properties → check "Environment variables" option). However, it is recommended to set the user name and password as a credential. 

The Checkmarx Jenkins Plugin already requires setting a credential to fetch projects, teams, etc. You can use the same credentials here as well.

## Sample pipeline for Jenkins

### Simple pipeline

```Groovy
@Library('checkmarx-jenkins-shared-library')_

withCredentials([usernamePassword(credentialsId: 'checkmarx-credentials-id', passwordVariable: 'CX_CREDENTIALS_PSW', usernameVariable: 'CX_CREDENTIALS_USR')]) {
    // To authenticate
    authData = checkmarxAuthenticate env.CX_SERVER_URL, CX_CREDENTIALS_USR, CX_CREDENTIALS_PSW
    echo authData.toString()

    // To get projects
    projects = getProjects authData.access_token
    echo projects.toString()
                    
    // To get one project
    firstProject = getOneProject(authData.access_token, projects[0].id)
    echo firstProject.toString()
                    
    // To get teams
    teams = getTeams(authData.access_token)
    echo teams.toString()

    // To get one team
    firstTeam = getOneTeam(authData.access_token, teams[0].id)
    echo firstTeam.toString()
}
```

### Declarative pipeline

```Groovy
@Library('checkmarx-jenkins-shared-library')_

pipeline {
    agent any

    environment {
        // This creates CX_CREDENTIALS_USR and CX_CREDENTIALS_PSW automatically
        CX_CREDENTIALS = credentials('checkmarx-credentials-id')
    }
    
    stages {
        stage('GetProjectTeam') {
            steps {
                script {
                    // To authenticate
                    def authData = checkmarxAuthenticate('https://myserver.checkmarx.net') // CX_CREDENTIALS_USR and CX_CREDENTIALS_PSW are already used here when not provided
                    echo authData.toString()
                    
                    // To get projects
                    def projects = getProjects(authData.access_token)
                    echo projects.toString()
                    
                    // To get one project
                    def firstProject = getOneProject(authData.access_token, projects[0].id)
                    echo firstProject.toString()
                    
                    // To get teams
                    def teams = getTeams(authData.access_token)
                    echo teams.toString()

                    // To get one team
                    def firstTeam = getOneTeam(authData.access_token, teams[0].id)
                    echo firstTeam.toString()
                }
            }
        }
    }
}
```

### If you don't have admin rights in Jenkins

```Groovy
library identifier: 'checkmarx-jenkins-shared-library@main',
    retriever: modernSCM([
      $class: 'GitSCMSource',
      remote: 'https://github.com/cx-leonelsanches/checkmarx-jenkins-shared-library.git'
])

pipeline {
    agent any

    environment {
        // This creates CX_CREDENTIALS_USR and CX_CREDENTIALS_PSW automatically
        CX_CREDENTIALS = credentials('checkmarx-credentials-id')
    }
    
    stages {
        stage('GetProjectTeam') {
            steps {
                script {
                    // To authenticate
                    def authData = checkmarxAuthenticate('https://myserver.checkmarx.net') // CX_CREDENTIALS_USR and CX_CREDENTIALS_PSW are already used here when not provided
                    echo authData.toString()
                    
                    // To get projects
                    def projects = getProjects(authData.access_token)
                    echo projects.toString()
                    
                    // To get one project
                    def firstProject = getOneProject(authData.access_token, projects[0].id)
                    echo firstProject.toString()
                    
                    // To get teams
                    def teams = getTeams(authData.access_token)
                    echo teams.toString()

                    // To get one team
                    def firstTeam = getOneTeam(authData.access_token, teams[0].id)
                    echo firstTeam.toString()
                }
            }
        }
    }
}
```

## Contributing

Each command requires a new `.groovy` file under `vars` folder. Each file requires a `call` method. 

If necessary, you can create additional classes at `src` folder.