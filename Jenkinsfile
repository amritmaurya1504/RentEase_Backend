pipeline {
    agent any
    tools {
        maven 'maven3'  // Ensure Maven is installed and configured in Jenkins
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhubpwd')  // DockerHub credentials from Jenkins credentials store
        DOCKERHUB_USER = 'amritmaurya1504'  // DockerHub username
        IMAGE_NAME = 'rentease-backend'  // Image name
        DOCKER_REPOSITORY = "${DOCKERHUB_USER}/${IMAGE_NAME}"
    }
    stages {
        stage ('BUILD MAVEN') {
            steps {
                script {
                    try {
                        checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/amritmaurya1504/RentEase_Backend']])
                        sh "mvn clean install"
                    } catch (Exception e) {
                        error("Maven build failed, stopping pipeline.")
                    }
                }
            }
        }
        stage ('BUILD DOCKER IMAGE') {
            steps {
                script {
                    try {
                        sh "docker build -t ${DOCKER_REPOSITORY} ."
                    } catch (Exception e) {
                        error("Docker image build failed, stopping pipeline.")
                    }
                }
            }
        }
        stage('PUSH TO DOCKER HUB') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'DOCKERHUB_PWD')]) {
                        sh "docker login -u ${DOCKERHUB_USER} -p ${DOCKERHUB_PWD}"
                    }
                    sh "docker push ${DOCKER_REPOSITORY}"
                }
            }
        }
        stage('CLEANUP OLD DOCKER IMAGE AND CONTAINER') {
            steps {
                script {
                    try {
                        // Stop and remove any existing container with the same name
                        sh "docker-compose down || true"
                        sh "docker rmi -f ${DOCKER_REPOSITORY} || true"
                    } catch (Exception e) {
                        echo "No existing Docker container or image to remove."
                    }
                }
            }
        }
        stage('DEPLOY TO DIGITAL OCEAN') {
            steps {
                script {
                    sh """
                            # Run Docker Compose to start both the application and PostgreSQL containers
                            docker-compose up -d
                        """
                }
            }
        }
    }
    post {
        failure {
            echo 'Pipeline failed. Check the logs for details.'
        }
        success {
            echo 'Pipeline executed successfully!'
        }
    }
}
