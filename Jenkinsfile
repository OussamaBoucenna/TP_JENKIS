pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                script {
                    bat './gradlew.bat test --tests "acceptation.DeterminantCalculatorFeature"'
                    bat './gradlew.bat test'
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
                withSonarQubeEnv('SONARQUBE') {
                    bat './gradlew.bat sonar'
                }
            }
        }

        stage('Code Quality') {
            steps {
                script {
                    def qualityGate = waitForQualityGate()
                    if (qualityGate.status != 'OK') {
                        error "Pipeline failed due to Quality Gate failure: ${qualityGate.status}"
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Générer le fichier JAR
                    bat 'gradlew.bat jar'

                    // Générer la documentation
                    bat 'gradlew.bat javadoc'
                }

                // Archiver les artifacts
                archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
                archiveArtifacts artifacts: '**/build/docs/javadoc/**', allowEmptyArchive: true
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Déployer avec Gradle sur Windows
                    bat 'gradlew.bat publish'
                }

                // Archiver les fichiers de publication, si nécessaire
                archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
            }
        }

        stage('Email Notification') {
            steps {
                script {
                    currentBuild.result = currentBuild.result ?: 'SUCCESS'
                    if (currentBuild.result == 'SUCCESS') {
                        echo 'Sending success notifications...'
                        mail to: 'lo_boucenna@esi.dz',
                             subject: "Build Success: ",
                             body: "The build and deployment for was successful."
                    } else {
                        echo 'Sending failure notifications...'
                        mail to: 'lo_boucenna@esi.dz',
                             subject: "Build Failed: ",
                             body: "The build for failed. Check the logs for details."
                    }
                }
            }
        }

        stage('Slack Notification') {
            steps {
                slackSend channel: '#news-deployement',
                          color: 'good',
                          message: "Build completed successfully 100%."
            }
        }
    }
}
