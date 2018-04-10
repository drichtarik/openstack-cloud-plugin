pipeline {
    agent {
        openstack {
            cloud 'openstack'
            bootSource $class: 'Image', name: 'bootSource image name'
            hardwareId 'hardverAjDi'
            networkId 'netvorkAjDi'
            userDataId 'juzrDejtaAjDi'
            floatingIpPool 'floutinAjPiPul'
            securityGroups 'sekjurityGrups'
            availabilityZone 'evejlabilityZoun'
            startTimeout 1
            keyPairName 'kiPerNejm'
            jvmOptions 'jvmOpsons'
            fsRoot 'groot'
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