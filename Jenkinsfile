pipeline {
    agent any

    tools {
        maven 'maven-3.9.9'
        jdk 'jdk-17'
    }

    environment {
        DOCKER_CREDENTIALS_ID = 'dockerhub_credentials' 
        DOCKER_IMAGE_NAME = 'karnamshyam1947/springboot-sqlite-crud' 
    }

    stages {

        stage('GitHub Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/KarnamShyam1947/springboot-sqlite-crud' 
            }
        }

        stage('Maven Check') {
            steps {
                sh 'mvn --version' 
            }
        }
        
        stage('Build Spring Boot Project') {
            steps {

                
                sh "mvn -Dmaven.test.failure.ignore=true -DskipTests clean package"
            }
        }

        stage('SonarQube analysis'){
            steps{
                withSonarQubeEnv('SonaeQubeServer') { 
                    sh "mvn sonar:sonar  \
                        -Dsonar.projectKey=sqlite-crud"
                }
            }

        }

        stage('Build Docker Image') {
            steps {
                script {
                    
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:latest ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    
                    withCredentials([
                        usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", 
                        passwordVariable: 'DOCKER_PASSWORD', 
                        usernameVariable: 'DOCKER_USERNAME')
                    ]) {
                        sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"
                    }

                    
                    sh "docker push ${DOCKER_IMAGE_NAME}:latest"
                }
            }
        }
    }

    post {
        success {
            echo 'Build and push to Docker Hub was successful!'
        }
        failure {
            echo 'Build and push to Docker Hub was not successful!'
        }
    }
}