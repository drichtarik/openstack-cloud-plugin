package jenkins.plugins.openstack.pipeline;

import hudson.Extension;
import hudson.model.TaskListener;
import jenkins.plugins.openstack.compute.JCloudsSlaveTemplate;
import jenkins.plugins.openstack.compute.SlaveOptions;
import jenkins.plugins.openstack.compute.slaveopts.BootSource;
import jenkins.plugins.openstack.compute.slaveopts.LauncherFactory;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class OpenStackNodeStep extends Step implements Serializable{

    private static final String DEFAULT_CLOUD = JCloudsSlaveTemplate.OPENSTACK_CLOUD_NAME_KEY;

    private String cloud = DEFAULT_CLOUD;
    private SlaveOptions slaveOptions;

    private final BootSource bootSource;
    private final String hardwareId;
    private final String networkId;
    private final String userDataId;
    private Integer instanceCap;
    private final String floatingIpPool;
    private final String securityGroups;
    private final String availabilityZone;
    private final Integer startTimeout;
    private final String keyPairName;
    private Integer numExecutors;
    private final String jvmOptions;
    private final String fsRoot;
    private final LauncherFactory launcherFactory;
    private Integer retentionTime;

    @DataBoundConstructor
    public OpenStackNodeStep(String cloud, BootSource bootSource, String hardwareId, String networkId, String userDataId, String floatingIpPool, String securityGroups, String availabilityZone, Integer startTimeout, String keyPairName, String jvmOptions, String fsRoot, LauncherFactory launcherFactory) {
        this.cloud = cloud;
        this.bootSource = bootSource;
        this.hardwareId = hardwareId;
        this.networkId = networkId;
        this.userDataId = userDataId;
        this.floatingIpPool = floatingIpPool;
        this.securityGroups = securityGroups;
        this.availabilityZone = availabilityZone;
        this.startTimeout = startTimeout;
        this.keyPairName = keyPairName;
        this.jvmOptions = jvmOptions;
        this.fsRoot = fsRoot;
        this.launcherFactory = launcherFactory;
    }

    public String getCloud() {
        return cloud;
    }

    public SlaveOptions getSlaveOptions() {
        return slaveOptions;
    }

    public void createSlaveOptions() {
        //add conditions for user inputs
        //also add constraints on instanceCap, numExecutors, retentionTime
        this.instanceCap = null;
        this.numExecutors = 1;
        this.retentionTime = 0; //nemusi byt field
        //BootSource boot = new BootSource.VolumeSnapshot(this.bootSource); //
        //LauncherFactory launch = new LauncherFactory.SSH("");

        SlaveOptions opts = new SlaveOptions(
                this.bootSource,
                this.hardwareId,
                this.networkId,
                this.userDataId,
                this.instanceCap,
                this.floatingIpPool,
                this.securityGroups,
                this.availabilityZone,
                this.startTimeout,
                this.keyPairName,
                this.numExecutors,
                this.jvmOptions,
                this.fsRoot,
                this.launcherFactory,
                this.retentionTime
        );
        this.slaveOptions = opts;
        System.out.println(toString());
        if (this.jvmOptions == null) {
            System.out.println("Je to null");
        } else System.out.println("Nie je to null");
    }

    @Override
    public StepExecution start(StepContext stepContext) throws Exception {
        this.createSlaveOptions();
        return new OpenStackNodeStepExecution(this, stepContext);
    }

    @Extension(optional = true)
    public static final class DescriptorImpl extends StepDescriptor {

        @Override
        public String getFunctionName() {
            return "openStackNodeStep";
        }

        @Override
        public String getDisplayName() {
            return "Cloud instances provisioning for declarative pipeline";
        }

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return Collections.singleton(TaskListener.class);
        }
    }

    @Override
    public String toString() {
        return "OpenStackNodeStep{" + '\n' +
                "cloud='" + cloud + '\'' + '\n' +
                ", bootSource='" + bootSource + '\'' + '\n' +
                ", hardwareId='" + hardwareId + '\'' + '\n' +
                ", networkId='" + networkId + '\'' + '\n' +
                ", userDataId='" + userDataId + '\'' + '\n' +
                ", instanceCap=" + instanceCap + '\n' +
                ", floatingIpPool='" + floatingIpPool + '\'' + '\n' +
                ", securityGroups='" + securityGroups + '\'' + '\n' +
                ", availabilityZone='" + availabilityZone + '\'' + '\n' +
                ", startTimeout=" + startTimeout + '\n' +
                ", keyPairName='" + keyPairName + '\'' + '\n' +
                ", numExecutors=" + numExecutors + '\n' +
                ", jvmOptions='" + jvmOptions + '\'' + '\n' +
                ", fsRoot='" + fsRoot + '\'' + '\n' +
                ", launcherFactory='" + launcherFactory + '\'' + '\n' +
                ", retentionTime=" + retentionTime + '\n' +
                '}';
    }
}
