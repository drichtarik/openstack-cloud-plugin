import jenkins.plugins.openstack.compute.slaveopts.BootSource
import jenkins.plugins.openstack.compute.slaveopts.BootSource.Image
import jenkins.plugins.openstack.compute.slaveopts.LauncherFactory
import jenkins.plugins.openstack.compute.slaveopts.LauncherFactory.SSH



pipeline {
    agent {
        openstack {
            cloud 'openstack'
            options {
                $class BootSource BootSource {
                    $class Image Image {
                        name 'dummyImageId'
                    }
                }
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
                $class LauncherFactory LauncherFactory {
                    $class SSH SSH {
                        credentialsId ''
                    }
                }
                retentionTime 1
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