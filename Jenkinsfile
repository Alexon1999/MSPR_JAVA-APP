pipeline {
    
    agent {
        docker {
            image 'maven:3.8.4-openjdk-17'
            args '-v /root/.m2:/root/.m2'
        }
    }

    stages {
        stage('Preparation') {
            steps {
                // Get some code from a GitHub repository
                // Cloner le repo où sont stockés les data
                git 'https://github.com/Alexon1999/MSPR_GO-SECURI'
                
                //sh 'cd .. && rm -rf db && mkdir db'
                deleteAndCreateDirectory("../db")
                
                // sh revient au dossier de pipeline ex: /var/jenkins_home/workspace/GoSecuriJavaAppPipeline
                //sh 'ls -a'
                
                // mettre ces fichiers dans le dossier db dans workspace ex: /var/jenkins_home/workspace/
                sh 'mv ./* ../db/'
                //sh 'echo $PWD'
                
                sh 'echo "Preparation finished"'
            }

        }
        
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                // Cloner l'application java
                git 'https://github.com/Alexon1999/MSPR_JAVA-APP'
                
                // Réinitialiser
                deleteAndCreateDirectory("db")
                deleteAndCreateDirectory("web")
                deleteAndCreateDirectory("../web")
                
                sh 'mv ../db/* db/'
                //sh 'cd db && ls -a'
                //sh 'ls -a'
                
                sh 'mvn clean package'
                sh 'java -jar target/mspr-1.0-SNAPSHOT-jar-with-dependencies.jar'
                
                // Mettre les pages html dans un autre dossier dans workspace
                sh 'mv web/* ../web/'
                sh 'echo "les pages HTML générés : "'
                sh 'ls ../web'
                
                // Nettoyer
                deleteDirectory("../db")
                deleteDirectory("db")
                deleteDirectory("web")

                sh 'echo "Build finished"'
            }

        }
        
        stage('Deploy'){
            steps{
                sh 'echo "Start Deploy ...."'
            }
        }
    }
}

def deleteAndCreateDirectory(String dir) {
    sh "rm -rf ${dir} && mkdir ${dir}"
}

def deleteDirectory(String dir) {
    sh "rm -rf ${dir}"
}
