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

environment {
    DOCKER_CREDENTIALS = credentials['DOCKER_CREDENTIALS']
}

pipeline {
    agent {
        label "${AGENT_LABEL}"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'development', description: 'Enter target branch.')
    }
    
    stages {
        stage('Checkout & Build') {
            steps {
                script {
                    checkoutAndBuild()
                }
            }
        }

        stage('Push to Docker') {
            steps {
                script {
                    pushToDocker()
                }
            }
        }

    }     
}


def pushToDocker() {
    sh "docker login -u $env.DOCKER_CREDENTIALS_USR -p $env.DOCKER_CREDENTIALS_PSW"
    sh "docker logout"
}

def checkoutAndBuild() {
    echo "Branch $env.branchName is checked out"
    checkout([$class           : 'GitSCM',
              branches         : [[name: "origin/$env.branchName"]],
              extensions       : [],
              userRemoteConfigs: [[credentialsId: 'JENKINS_SSH',
                                   url          : 'git@github.com:vipulgupta007/bookstore.git'
                                  ]]
    ])
    sh "mvn clean package"
    echo "Build successfull with option $env.Data"
}
