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
            // Use the SonarQube environment wrapper
            withSonarQubeEnv('SONARQUBE') { // Assurez-vous que 'SONARQUBE' est le nom exact de votre serveur SonarQube configuré dans Jenkins
                bat './gradlew.bat sonar'
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
         stage('Build') {
             steps {
                 script {
                     // Générer le fichier JAR
                     bat 'gradlew.bat jar'

                     // Générer la documentation
                     bat 'gradlew.bat javadoc'
                 }
             }

         }
         stage('Deploy') {
             steps {
                 script {
                     // Déployer avec Gradle sur Windows
                     bat 'gradlew.bat publish'
                 }
             }
         }


    }
     post {
              success {
                    // Archiver le fichier JAR généré et la documentation
                  //  archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                   // archiveArtifacts artifacts: 'build/docs/javadoc/**/*', fingerprint: true

                      mail(
                          to: 'lo_boucenna@esi.dz',
                          subject: 'Deployment Success - Project BOOUCENNA ',
                          body: 'The deployment for the project BOUCENNA was successful.'
                             )
                     }
                    failure {
                                         // Email Notification for Pipeline Failure
                   mail(
                     to: 'lo_boucenna@esi.dz',
                      subject: 'Pipeline Failed - Project BOUCENNA',
                      body: 'The Jenkins pipeline for project BOUCENNA has failed. Please check the logs for more details.'
                     )

                   }
          }
}