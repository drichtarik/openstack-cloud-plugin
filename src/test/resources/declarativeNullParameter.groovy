pipeline {
    agent {
        openstack {
            cloud null
            bootSource null
            hardwareId null
            networkId null
            userDataId null
            floatingIpPool null
            securityGroups null
            availabilityZone null
            startTimeout 1
            keyPairName 'kiPerNejm'
            jvmOptions null
            fsRoot null
            launcherFactory $class: 'SSH', credentialsId: ''
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