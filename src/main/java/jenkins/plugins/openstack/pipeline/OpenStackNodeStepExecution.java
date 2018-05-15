package jenkins.plugins.openstack.pipeline;

import jenkins.model.Jenkins;
import jenkins.plugins.openstack.compute.*;
import org.jenkinsci.plugins.cloudstats.ProvisioningActivity;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousNonBlockingStepExecution;

import javax.annotation.Nonnull;

public class OpenStackNodeStepExecution extends SynchronousNonBlockingStepExecution<JCloudsSlave> {

    private final @Nonnull String cloudName;
    private final @Nonnull SlaveOptions slaveOptions;
    private TemporaryServer temporaryServer;
    private JCloudsSlave newSlave;

    OpenStackNodeStepExecution(OpenStackNodeStep openStackNodeStep, StepContext context) {
        super(context);
        this.cloudName = openStackNodeStep.getCloud();
        this.slaveOptions = openStackNodeStep.getSlaveOptions();
        this.temporaryServer = new TemporaryServer(slaveOptions);
    }

    @Override
    protected JCloudsSlave run() throws Exception {
        JCloudsCloud jcl = JCloudsCloud.getByName(cloudName);
        ProvisioningActivity.Id id = new ProvisioningActivity.Id(this.cloudName);
        newSlave = temporaryServer.provisionSlave(jcl, id, null);
        Jenkins.getInstance().addNode(newSlave);
        return newSlave;
    }

    @Override
    public void stop(Throwable cause) throws Exception {
        //((JCloudsComputer) newSlave.toComputer()).deleteSlave();
        super.stop(cause);
    }
}
