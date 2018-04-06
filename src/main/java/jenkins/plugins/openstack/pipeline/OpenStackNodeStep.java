package jenkins.plugins.openstack.pipeline;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import jenkins.plugins.openstack.compute.JCloudsSlaveTemplate;
import jenkins.plugins.openstack.compute.ServerScope;
import jenkins.plugins.openstack.compute.SlaveOptions;
import jenkins.plugins.openstack.compute.slaveopts.BootSource;
import jenkins.plugins.openstack.compute.slaveopts.LauncherFactory;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class OpenStackNodeStep extends Step implements Serializable{

    private static final String DEFAULT_CLOUD = JCloudsSlaveTemplate.OPENSTACK_CLOUD_NAME_KEY;

    private String cloud = DEFAULT_CLOUD;
    private SlaveOptions slaveOptions;

    //another option
    private String bootSource;
    private String hardwareId;
    private String networkId;
    private String userDataId;
    private Integer instanceCap;
    private String floatingIpPool;
    private String securityGroups;
    private String availabilityZone;
    private Integer startTimeout;
    private String keyPairName;
    private Integer numExecutors;
    private String jvmOptions;
    private String fsRoot;
    private String launcherFactory;
    private Integer retentionTime;

    @DataBoundConstructor
    public OpenStackNodeStep(String cloud) {
        this.cloud = cloud;
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
    public void setBootSource(String bootSource) {
        this.bootSource = bootSource;
    }

    @DataBoundSetter
    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    @DataBoundSetter
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    @DataBoundSetter
    public void setUserDataId(String userDataId) {
        this.userDataId = userDataId;
    }

    @DataBoundSetter
    public void setInstanceCap(Integer instanceCap) {
        this.instanceCap = instanceCap;
    }

    @DataBoundSetter
    public void setFloatingIpPool(String floatingIpPool) {
        this.floatingIpPool = floatingIpPool;
    }

    @DataBoundSetter
    public void setSecurityGroups(String securityGroups) {
        this.securityGroups = securityGroups;
    }

    @DataBoundSetter
    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    @DataBoundSetter
    public void setStartTimeout(Integer startTimeout) {
        this.startTimeout = startTimeout;
    }

    @DataBoundSetter
    public void setKeyPairName(String keyPairName) {
        this.keyPairName = keyPairName;
    }

    @DataBoundSetter
    public void setNumExecutors(Integer numExecutors) {
        this.numExecutors = numExecutors;
    }

    @DataBoundSetter
    public void setJvmOptions(String jvmOptions) {
        this.jvmOptions = jvmOptions;
    }

    @DataBoundSetter
    public void setFsRoot(String fsRoot) {
        this.fsRoot = fsRoot;
    }

    @DataBoundSetter
    public void setLauncherFactory(String launcherFactory) {
        this.launcherFactory = launcherFactory;
    }

    @DataBoundSetter
    public void setRetentionTime(Integer retentionTime) {
        this.retentionTime = retentionTime;
    }

    public void createSlaveOptions() {
        BootSource boot = new BootSource.VolumeSnapshot(this.bootSource);
        LauncherFactory launch = new LauncherFactory.SSH("");

        SlaveOptions altSlaveOptions = new SlaveOptions(
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

        this.slaveOptions = altSlaveOptions;
    }

    @Override
    public StepExecution start(StepContext stepContext) throws Exception {
        this.createSlaveOptions();
        System.out.println("Nulte hw id: " + slaveOptions.getHardwareId());
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

        /*
        public ListBoxModel doFillCloudItems() {
            ListBoxModel r = new ListBoxModel();
            r.add("", "");
            Jenkins.CloudList clouds = jenkins.model.Jenkins.getActiveInstance().clouds;
            for (Cloud cloud: clouds) {
                if (cloud instanceof JCloudsCloud) {
                    r.add(cloud.getDisplayName(), cloud.getDisplayName());
                }
            }
            return r;
        }

        public ListBoxModel doFillTemplateItems(@QueryParameter String cloud) {
            cloud = Util.fixEmpty(cloud);
            ListBoxModel r = new ListBoxModel();
            for (Cloud cl : jenkins.model.Jenkins.getActiveInstance().clouds) {
                if (cl.getDisplayName().equals(cloud) && (cl instanceof JCloudsCloud)) {
                    for (JCloudsSlaveTemplate template : ((JCloudsCloud) cl).getTemplates()) {
                        r.add(template.name);
                    }
                }
            }
            return r;
        }

        */

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return Collections.singleton(TaskListener.class);
        }
    }
}
