pipeline {
    agent {
        openstack {
            cloud 'openstack'
            bootSource 'bootSourceID'
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
            launcherFactory 'SSH'
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