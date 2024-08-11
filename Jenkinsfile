pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Git 리포지토리에서 소스 코드를 체크아웃
                checkout scm
            }
        }

        stage('Build and Run Docker Compose') {
            steps {
                script {
                    // cicd 디렉토리로 이동하여 docker-compose.dev.yml 실행
                    dir('cicd') {
                        if (isUnix()) {
                            // Linux/Unix 환경
                            sh 'docker-compose -f docker-compose.dev.yml up --build -d'
                        } else {
                            // Windows 환경
                            bat 'docker-compose -f docker-compose.dev.yml up --build -d'
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                // 빌드 후, 컨테이너를 내리는 작업
                dir('cicd') {
                    if (isUnix()) {
                        // Linux/Unix 환경
                        sh 'docker-compose -f docker-compose.dev.yml down'
                    } else {
                        // Windows 환경
                        bat 'docker-compose -f docker-compose.dev.yml down'
                    }
                }
            }
        }
    }
}
