package jenkins.plugins.openstack.pipeline;

import jenkins.plugins.openstack.PluginTestRule;
import jenkins.plugins.openstack.compute.JCloudsCloud;
import jenkins.plugins.openstack.compute.internal.Openstack;
import org.apache.commons.compress.utils.IOUtils;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;

public class OpenstackEnhancedDeclarativePipelineTest {
    @Rule
    public PluginTestRule j = new PluginTestRule();
    private JCloudsCloud cloud;
    private Openstack openstack;

    @Before
    public void setup () {
        cloud = j.createCloudLaunchingDummySlaves("whatever");
        j.jenkins.clouds.add(cloud);
        openstack = cloud.getOpenstack();
    }

    @Test
    public void boot() throws Exception {
        WorkflowJob boot = j.jenkins.createProject(WorkflowJob.class, "boot");
        boot.setDefinition(new CpsFlowDefinition(loadPipelineScript("declarativeEnhanced.groovy"), true));
        WorkflowRun b = j.assertBuildStatusSuccess(boot.scheduleBuild2(0));
        j.assertLogContains("Hello World!", b);
        assertThat(openstack.getRunningNodes(), emptyIterable());
    }

    protected String loadPipelineScript(String name) {
        try {
            return new String(IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream(name)));
        } catch (Throwable t) {
            throw new RuntimeException("Could not read resource:[" + name + "].");
        }
    }

}
