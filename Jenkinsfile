pipeline {
    agent any
    tools {
        maven 'maven3'
    }
    stages {
        stage ('BUILD MAVEN'){
            steps {
                    checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/amritmaurya1504/RentEase_Backend']])
                    sh "mvn clean install"
            }    
        }
        stage ('BUILD DOCKER IMAGE'){
            steps {
                script{
                    sh 'docker build -t amritmaurya1504/rentease-backend .'
                }
            }
        }
        stage('PUSH TO DOCKER HUB'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u amritmaurya1504 -p ${dockerhubpwd}'
                    }
                    
                    sh 'docker push amritmaurya1504/rentease-backend'
                }
            }
        }
    }
}