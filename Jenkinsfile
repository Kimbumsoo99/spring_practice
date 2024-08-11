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
                        // docker-compose 명령어를 실행하면서 Jenkins에 등록된 환경변수를 자동으로 사용
                        sh 'docker-compose -f docker-compose.dev.yml up --build -d'
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                // 빌드 후, 컨테이너를 내려도 되는 경우 이 섹션에서 처리합니다.
                dir('cicd') {
                    sh 'docker-compose -f docker-compose.dev.yml down'
                }
            }
        }
    }
}
