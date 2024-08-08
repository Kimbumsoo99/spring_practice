pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials') // DockerHub Credentials ID
        GIT_CREDENTIALS = 'github-credentials' // GitHub Credentials ID
    }

    triggers {
        // 5분마다 빌드를 실행
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                // Git 리포지토리에서 소스 코드 체크아웃
                git url: 'https://github.com/Kimbumsoo99/spring_practice.git', branch: 'main', credentialsId: env.GIT_CREDENTIALS
            }
        }
        stage('Build') {
            steps {
                dir('cicd') {
                    // Gradle 빌드
                    bat './gradlew build'
                }
            }
        }
        stage('Test') {
            steps {
                dir('cicd') {
                    // Gradle 테스트 실행
                    bat './gradlew test'
                }
            }
            post {
                always {
                    // JUnit 테스트 결과 보고서 수집
                    junit 'cicd/build/test-results/test/*.xml'
                }
                failure {
                    // 테스트 실패 시 아티팩트 아카이브
                    archiveArtifacts 'cicd/build/reports/tests/test/*.html'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Dockerfile이 있는 디렉토리로 이동하여 Docker 이미지를 빌드
                    dir('cicd') {
                        dockerImage = docker.build("your-dockerhub-username/cicd:latest", ".")
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
                    dockerImage.run('-p 8080:8080")
                }
            }
        }
    }
}
