package jenkins.plugins.openstack.pipeline;

import jenkins.plugins.openstack.compute.JCloudsCloud;
import jenkins.plugins.openstack.compute.JCloudsSlave;
import jenkins.plugins.openstack.compute.SlaveOptions;
import jenkins.plugins.openstack.compute.TemporaryServer;
import org.jenkinsci.plugins.cloudstats.ProvisioningActivity;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousNonBlockingStepExecution;

import javax.annotation.Nonnull;

public class OpenStackNodeStepExecution extends SynchronousNonBlockingStepExecution<JCloudsSlave> {

    private final @Nonnull String cloudName;
    //@NotNull on SlaveOptions removed for simlified test to work
    private SlaveOptions slaveOptions;
    private TemporaryServer temporaryServer;

    /*
    private JCloudsSlaveTemplate jCloudsSlaveTemplate = null;
    private final SlaveOptions slaveOptions = null;
    private final transient OpenStackNodeStep slaveTemplateStep;
    private final String cloudName;
    */

    OpenStackNodeStepExecution(OpenStackNodeStep openStackNodeStep, StepContext context) {
        super(context);
        this.cloudName = openStackNodeStep.getCloud();
        if (openStackNodeStep.getSlaveOptions() != null) {
            this.slaveOptions = openStackNodeStep.getSlaveOptions();
        }
        System.out.println("Prve boot " + slaveOptions.getBootSource().toString());
        this.temporaryServer = new TemporaryServer(slaveOptions);
    }

    @Override
    protected JCloudsSlave run() throws Exception {
        JCloudsCloud jcl = JCloudsCloud.getByName(cloudName);
        ProvisioningActivity.Id id = new ProvisioningActivity.Id(this.cloudName);
        return temporaryServer.provisionSlave(jcl, id, null);
    }

    /*
    @Override
    public boolean start() throws Exception {
        Cloud cloud = Jenkins.getInstance().getCloud(this.cloudName);
        if (cloud == null) {
            throw new AbortException(String.format("Cloud does not exist: %s", this.cloudName));
        }
        if (!(cloud instanceof JCloudsCloud)) {
            throw new AbortException(String.format("Cloud is not an OpenStack cloud: %s (%s)", cloudName,
                    cloud.getClass().getName()));
        }

        JCloudsCloud jCloudsCloud = (JCloudsCloud) cloud;

        Run<?, ?> run = getContext().get(Run.class);

        slaveOptions.getBuilder().hardwareId("123456");

        jCloudsSlaveTemplate = new JCloudsSlaveTemplate(slaveTemplateStep.getName(), slaveTemplateStep.getLabel(), slaveOptions);

        return false;
    }
    */
}
