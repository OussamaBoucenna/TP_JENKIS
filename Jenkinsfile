pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                echo 'Testing & archiving..'
                bat "./gradlew test"
                archiveArtifacts '**/build/libs/*.jar'
            }
        }

         stage('Code Analysis') {
            steps {
                echo 'Starting Code Analysis with SonarQube...'
                withSonarQubeEnv('Sonar') {
                    bat './gradlew sonar'
                }
            }
         }

         /*stage('Code Quality') {
             steps {
                 echo 'Checking SonarQube Quality Gates...'
                 waitForQualityGate abortPipeline: true
            }
         }*/

        stage('Build') {
                 steps {
                     echo 'Building the Project...'
                     bat './gradlew build'
                     echo 'Generating Documentation...'
                     bat './gradlew javadoc' // Génération de la documentation
                     archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
                     archiveArtifacts artifacts: '**/build/docs/javadoc/**', fingerprint: true
                 }
        }

         stage('Deploy') {
            steps {
                echo 'Deploying with Maven...'
                bat './gradlew publish'
            }
         }

         stage('Email Notification') {
             steps {
                 script {
                     currentBuild.result = currentBuild.result ?: 'SUCCESS'
                     if (currentBuild.result == 'SUCCESS') {
                         echo 'Sending success notifications...'
                         mail to: 'lr_gueddouche@esi.dz',
                              subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                              body: "The build and deployment for ${env.JOB_NAME} #${env.BUILD_NUMBER} was successful."
                     } else {
                         echo 'Sending failure notifications...'
                         mail to: 'lr_gueddouche@esi.dz',
                              subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                              body: "The build for ${env.JOB_NAME} #${env.BUILD_NUMBER} failed. Check the logs for details."
                     }

                 }
             }
         }

         stage('Slack Notification') {
             steps {
                 slackSend channel: '#tp7',
                           color: 'good',
                           message: "Build ${env.JOB_NAME} #${env.BUILD_NUMBER} completed successfully."
             }
         }


    }
}