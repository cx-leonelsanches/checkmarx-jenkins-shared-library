# Checkmarx Jenkins Library

This is a collection of methods that we can use in a Jenkins Pipeline.

## Prerequisites

This collection of libraries requires three environment variables. You can set either in your pipeline script or in your Jenkins configuration (Manage Jenkins > Configure System > Global properties > check "Environment variables" option):

- `CX_SERVER_URL`: Your CxSAST instane URL. For instance, https://mycompany.checkmarx.net
- `CX_USERNAME`: A login that can read at least projects and teams information;
- `CX_PASSWORD`: The corresponding password for the login provided. 

## Sample pipeline for Jenkins

```Groovy
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