def AGENT_LABEL = null
node('built-in') {
    stage('Set Agent Label') {
        AGENT_LABEL = "jenkins_agent"
    }
}

@Library("shared_lib@main") _

properties([
        parameters([
                [$class: 'ChoiceParameter', choiceType: 'PT_SINGLE_SELECT', name: 'Data', description: 'This will print some data', filterLength: 1, filterable: false,
                 script: [
                         $class        : 'GroovyScript',
                         fallbackScript: [classpath: [], sandbox: true, script: "return['Error']"],
                         script        : [classpath: [], sandbox: false, script: commons.printData('stockartifacts')]
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
    echo ${Data}
    echo "Build successfull"
}
