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
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class OpenStackNodeStep extends Step implements Serializable{

    private static final String DEFAULT_CLOUD = JCloudsSlaveTemplate.OPENSTACK_CLOUD_NAME_KEY;

    private String cloud = DEFAULT_CLOUD;
    private SlaveOptions slaveOptions;

    private final @Nonnull String bootSource;
    private final @Nonnull String hardwareId;
    private final @Nonnull String networkId;
    private final @Nonnull String userDataId;
    private Integer instanceCap;
    private final @Nonnull String floatingIpPool;
    private final @Nonnull String securityGroups;
    private final @Nonnull String availabilityZone;
    private final @Nonnull Integer startTimeout;
    private final @Nonnull String keyPairName;
    private Integer numExecutors;
    private final @Nonnull String jvmOptions;
    private final @Nonnull String fsRoot;
    private final @Nonnull String launcherFactory;
    private Integer retentionTime;

    @DataBoundConstructor
    public OpenStackNodeStep(String cloud, String bootSource, String hardwareId, String networkId, String userDataId, String floatingIpPool, String securityGroups, String availabilityZone, Integer startTimeout, String keyPairName, String jvmOptions, String fsRoot, String launcherFactory) {
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

    @Nonnull
    public String getCloud() {
        return cloud;
    }

    public SlaveOptions getSlaveOptions() {
        return slaveOptions;
    }

    @DataBoundSetter
    public void setSlaveOptions(SlaveOptions slaveOptions) {
        this.slaveOptions = slaveOptions;
    }

    @DataBoundSetter
    public void setInstanceCap(Integer instanceCap) {
        this.instanceCap = instanceCap;
    }

    @DataBoundSetter
    public void setNumExecutors(Integer numExecutors) {
        this.numExecutors = numExecutors;
    }

    @DataBoundSetter
    public void setRetentionTime(Integer retentionTime) {
        this.retentionTime = retentionTime;
    }

    public void createSlaveOptions() {
        //add conditions for user inputs
        //also add constraints on instanceCap, numExecutors, retentionTime
        this.instanceCap = 1;
        this.numExecutors = 1;
        this.retentionTime = 1;
        BootSource boot = new BootSource.VolumeSnapshot(this.bootSource);
        LauncherFactory launch = new LauncherFactory.SSH("");

        SlaveOptions opts = new SlaveOptions(
                boot,
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
                launch,
                this.retentionTime
        );
        this.slaveOptions = opts;
        System.out.println(toString());
        if (this.jvmOptions == null) {
            System.out.println("Je to null pico");
        } else System.out.println("Nie je to null pico");
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
