def AGENT_LABEL = null
node('built-in') {
    stage('Set Agent Label') {
        AGENT_LABEL = "jenkins_agent"
    }
}

@Library("shared_lib@main") _
properties([
        parameters([
                [$class: 'StringParameterValue', name: 'Data', description: 'This will print some data',
                 script: [
                         $class        : 'GroovyScript',
                         fallbackScript: [classpath: [], sandbox: true, script: "return['Error']"],
                         script        : [classpath: [], sandbox: false, script: shared_lib.printData('stockartifacts')]
                 ]
                ]
        ])
])

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
              userRemoteConfigs: [[credentialsId: 'JENKINS_SSH',
                                   url          : 'git@github.com:vipulgupta007/bookstore.git'
                                  ]]
    ])

    echo "Build successfull"
}
