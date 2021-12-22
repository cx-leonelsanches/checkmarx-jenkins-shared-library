# Checkmarx Jenkins Library

This is a collection of methods that we can use in a Jenkins Pipeline.

## Sample pipeline for Jenkins

```Jenkinsfile
@Library('checkmarx-jenkins-shared-library')_

pipeline {
    agent any
    
    stages {
        stage('Hello') {
            steps {
                script {
                    // To authenticate
                    def authData = checkmarxAuthenticate()
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
                }
            }
        }
    }
}
```