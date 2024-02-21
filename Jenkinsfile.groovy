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
}

pipeline {
    agent {
        label "${AGENT_LABEL}"
    }

    parameters {
        string(name: 'branchName', defaultValue: 'main', description: 'Enter target branch.')
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
    sh "docker login -u ${env.DOCKER_CREDENTIALS_USERNAME} -p ${env.DOCKER_CREDENTIALS_PASSWORD}"
    sh "docker build --network=host . -t vipul753/bookstore:1.0.0"
    sh "docker push vipul753/bookstore:1.0.0"
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
}