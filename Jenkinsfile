pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials') // DockerHub Credentials ID
        GIT_CREDENTIALS = 'github-credentials' // GitHub Credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Kimbumsoo99/spring_practice.git', branch: 'main', credentialsId: env.GIT_CREDENTIALS
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    dir('cicd') {
                        // Windows 환경에서는 bat 명령어 사용
                        bat 'docker build --no-cache -t kimbumsoo99/cicd:latest .'
                    }
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'mysql-credentials', usernameVariable: 'DB_USER', passwordVariable: 'DB_PASS')]) {
                        bat 'docker run -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/ssafytest ' +
                            '-e SPRING_DATASOURCE_USERNAME=%DB_USER% ' +
                            '-e SPRING_DATASOURCE_PASSWORD=%DB_PASS% ' +
                            '-p 8080:8080 kimbumsoo99/cicd:latest'
                    }
                }
            }
        }
    }
}
