pipeline {
    agent {
        openstack {
            cloud 'openstack'
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