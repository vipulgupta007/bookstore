def AGENT_LABEL = null
node('built-in') {
    stage('Set Agent Label') {
        AGENT_LABEL = "jenkins_agent"
    }
}

pipeline {
    agent {
        label "${AGENT_LABEL}"
    }

    stages {
        stage('Initialise') {
            steps {
                script {
                    checkoutAndBuild()
                }
            }
        }
    }
}

def checkoutAndBuild() {
    echo "Branch $env.branchName is checked out"
    checkout([$class           : 'GitSCM',
              branches         : [[name: "origin/main"]],
              extensions       : [],
              userRemoteConfigs: [[credentialsId: 'GIT_SSH',
                                   url          : 'git@github.com:vipulgupta007/bookstore.git'
                                  ]]
    ])

    echo "Build successfull"
}