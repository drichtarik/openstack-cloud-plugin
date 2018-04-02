package jenkins.plugins.openstack.pipeline;

import jenkins.plugins.openstack.compute.SlaveOptions;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousNonBlockingStepExecution;

import javax.annotation.Nonnull;

public class SlaveTemplateStepExecution extends SynchronousNonBlockingStepExecution<SimplifiedServer> {

    private final @Nonnull String cloudName;
    private final @Nonnull String scope;
    //@NotNull on SlaveOptions removed for simlified test to work
    private SlaveOptions slaveOptions;

    /*
    private JCloudsSlaveTemplate jCloudsSlaveTemplate = null;
    private final SlaveOptions slaveOptions = null;
    private final transient SlaveTemplateStep slaveTemplateStep;
    private final String cloudName;
    */

    SlaveTemplateStepExecution(SlaveTemplateStep slaveTemplateStep, StepContext context) {
        super(context);
        this.cloudName = slaveTemplateStep.getCloud();
        this.scope = slaveTemplateStep.getScope();
        if (slaveTemplateStep.getSlaveOptions() != null) {
            this.slaveOptions = slaveTemplateStep.getSlaveOptions();
        }
    }

    @Override
    protected SimplifiedServer run() throws Exception {
        return new SimplifiedServer(this.cloudName, this.slaveOptions, this.scope);
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
