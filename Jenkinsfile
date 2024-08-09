pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials') // DockerHub Credentials ID
        GIT_CREDENTIALS = 'github-credentials' // GitHub Credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                // Git 리포지토리에서 소스 코드 체크아웃
                git url: 'https://github.com/Kimbumsoo99/spring_practice.git', branch: 'main', credentialsId: env.GIT_CREDENTIALS
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Dockerfile이 있는 디렉토리로 이동하여 Docker 이미지를 빌드 (캐시를 무시)
                    dir('cicd') {
                        dockerImage = docker.build("kimbumsoo99/cicd:latest", ".", "--no-cache")
                    }
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    // Docker 이미지 DockerHub에 푸시
                    docker.withRegistry('https://index.docker.io/v1/', env.DOCKERHUB_CREDENTIALS) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // MySQL 자격 증명을 안전하게 주입하기 위해 withCredentials 블록 사용
                    withCredentials([usernamePassword(credentialsId: 'mysql-credentials', usernameVariable: 'DB_USER', passwordVariable: 'DB_PASS')]) {
                        dockerImage.run("-e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/ssafytest -e SPRING_DATASOURCE_USERNAME=${DB_USER} -e SPRING_DATASOURCE_PASSWORD=${DB_PASS} -p 8080:8080")
                    }
                }
            }
        }
    }
}
