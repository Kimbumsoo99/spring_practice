pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Run Docker Compose') {
            steps {
                script {
                    if (isUnix()) {
                        dir('cicd') {
                            sh 'docker-compose -f docker-compose.dev.yml up --build -d'
                        }
                    } else {
                        dir('cicd') {
                            bat 'docker-compose -f docker-compose.dev.yml up --build -d'
                        }
                    }
                }
            }
        }
    }
    post {
        failure {
            script {
                dir('cicd') {
                    if (isUnix()) {
                        sh 'docker-compose -f docker-compose.dev.yml down'
                    } else {
                        bat 'docker-compose -f docker-compose.dev.yml down'
                    }
                }
            }
        }
    }
}
