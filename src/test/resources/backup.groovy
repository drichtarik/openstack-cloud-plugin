pipeline {
    agent {
        openstack {
            cloud 'openstack'
            slaveOptions {
                bootSource null
                hardwareId 'hardverAjDi'
                networkId 'netvorkAjDi'
                userDataId 'juzrDejtaAjDi'
                instanceCap 1 //nope
                floatingIpPool 'floutinAjPiPul'
                securityGroups 'sekjurityGrups'
                availabilityZone 'evejlabilityZoun'
                startTimeout 1
                keyPairName 'kiPerNejm'
                numExecutors 1 //nope
                jvmOptions 'jvmOpsons'
                fsRoot 'groot'
                launcherFactory null
                retentionTime 1 //nope
            }
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