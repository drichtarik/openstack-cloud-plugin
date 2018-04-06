package jenkins.plugins.openstack.pipeline;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import jenkins.plugins.openstack.compute.JCloudsSlaveTemplate;
import jenkins.plugins.openstack.compute.ServerScope;
import jenkins.plugins.openstack.compute.SlaveOptions;
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

    @DataBoundConstructor
    public OpenStackNodeStep(String cloud) {
        this.cloud = cloud;
    }

    @Nonnull
    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    //@Nonnull
    public SlaveOptions getSlaveOptions() {
        return slaveOptions;
    }

    @DataBoundSetter
    public void setSlaveOptions(SlaveOptions slaveOptions) {
        this.slaveOptions = slaveOptions;
    }

    @Override
    public StepExecution start(StepContext stepContext) throws Exception {
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
