pipeline {
    agent {
        openstack {
            label 'none'
        }
    }
    stages {
        stage('Example Build') {
            steps {
                echo 'Hello World!'
            }
        }
    }
}