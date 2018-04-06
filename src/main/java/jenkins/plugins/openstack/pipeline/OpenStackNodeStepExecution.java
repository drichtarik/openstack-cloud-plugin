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

    OpenStackNodeStepExecution(OpenStackNodeStep openStackNodeStep, StepContext context) {
        super(context);
        this.cloudName = openStackNodeStep.getCloud();
        if (openStackNodeStep.getSlaveOptions() != null) {
            this.slaveOptions = openStackNodeStep.getSlaveOptions();
        }
        this.temporaryServer = new TemporaryServer(slaveOptions);
    }

    @Override
    protected JCloudsSlave run() throws Exception {
        JCloudsCloud jcl = JCloudsCloud.getByName(cloudName);
        ProvisioningActivity.Id id = new ProvisioningActivity.Id(this.cloudName);
        return temporaryServer.provisionSlave(jcl, id, null);
    }
}
