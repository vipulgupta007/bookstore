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
    DOCKER_CREDENTIALS_USERNAME = ''
    DOCKER_CREDENTIALS_PASSWORD = ''
    AZURE_CREDENTIALS_USERNAME = ''
    AZURE_CREDENTIALS_PASSWORD = ''
    AZURE_REGISTRY = 'projectbookstore.azurecr.io'
}

pipeline {
    agent {
        label "${AGENT_LABEL}"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'main', description: 'Enter target branch.')
        string(name: 'VersionToBuild', defaultValue: '1.0.0', description: 'Enter target Version.')
    }
    
    stages {

        stage('Initialise') {
            steps {
                script {
                    initialise()
                }
            }
        }

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
    sh "docker login ${AZURE_REGISTRY} -u ${env.AZURE_CREDENTIALS_USERNAME} -p ${env.AZURE_CREDENTIALS_PASSWORD}"
    sh "docker build --network=host . -t vipul753/bookstore:1.0.1"
    sh "docker push vipul753/bookstore:$env.VersionToBuild"
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

def initialise(){
    withCredentials([usernamePassword(credentialsId: 'DOCKER_CREDENTIALS', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
        script {
            env.DOCKER_CREDENTIALS_USERNAME = "${DOCKER_USERNAME}"
            env.DOCKER_CREDENTIALS_PASSWORD = "${DOCKER_PASSWORD}"
        }
    }

    withCredentials([usernamePassword(credentialsId: 'AZURE_CREDENTIALS', usernameVariable: 'AZURE_USERNAME', passwordVariable: 'AZURE_PASSWORD')]) {
        script {
            env.AZURE_CREDENTIALS_USERNAME = "${AZURE_USERNAME}"
            env.AZURE_CREDENTIALS_PASSWORD = "${AZURE_PASSWORD}"
        }
    }
}