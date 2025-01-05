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
                // Use the SonarQube environment wrapper
                withSonarQubeEnv('sonar') { // Replace 'SonarQube' with the name of your configured SonarQube server in Jenkins
                    sh './gradlew sonar'
                }
            }
        }

        stage('Code Quality') {
             steps {
                 script {
                     def qualityGate = waitForQualityGate() // Wait for SonarQube's analysis result
                     if (qualityGate.status != 'OK') {
                         error "Pipeline failed due to Quality Gate failure: ${qualityGate.status}"
                     }
                 }
             }
         }
    }
}