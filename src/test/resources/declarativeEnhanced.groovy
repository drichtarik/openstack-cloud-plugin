import jenkins.plugins.openstack.compute.slaveopts.BootSource

pipeline {
    agent {
        openstack {
            cloud 'openstack'
            bootSource $class: 'Image', name: 'bootSource image name'
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
            launcherFactory $class: 'SSH', credentialsId: ''
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