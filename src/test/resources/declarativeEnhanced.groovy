pipeline {
    agent {
        openstack {
            cloud 'openstack'
            bootSource 'bootSourceID'
            hardwareId 'hardverAjDi'
            networkId 'netvorkAjDi'
            userDataId 'juzrDejtaAjDi'
            instanceCap 1
            floatingIpPool 'floutinAjPiPul'
            securityGroups 'sekjurityGrups'
            availabilityZone 'evejlabilityZoun'
            startTimeout 1
            keyPairName 'kiPerNejm'
            numExecutors 1
            jvmOptions 'jvmOpsons'
            fsRoot 'groot'
            launcherFactory 'SSH'
            retentionTime 1
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