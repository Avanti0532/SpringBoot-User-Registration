pipeline {
    agent any 
    stages {
        stage('Compile') {
            steps {
                bat '.\\mvnw clean compile'
            }
        }
    
        stage('Run Unit Tests') {
            steps {
                bat '.\\mnvw test'
            }
        }
        stage('Run Integration') {
            steps {
                echo 'Run only crucial integration tests from the source code' 
            }
        }
       
    }
}