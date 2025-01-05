pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                script {

                    sh './gradlew test --tests "acceptation.DeterminantCalculatorFeature"'
                    sh './gradlew test'
                }
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                    cucumber 'build/reports/cucumber/*.json'
                }
            }
        }
        stage('SonarQube') {
            steps {
                sh './gradlew sonar'
            }
        }    
    }
}