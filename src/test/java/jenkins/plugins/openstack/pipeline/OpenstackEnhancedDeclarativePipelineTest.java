package jenkins.plugins.openstack.pipeline;

import hudson.model.Node;
import jenkins.plugins.openstack.PluginTestRule;
import jenkins.plugins.openstack.compute.JCloudsCloud;
import jenkins.plugins.openstack.compute.JCloudsSlave;
import jenkins.plugins.openstack.compute.internal.Openstack;
import org.apache.commons.compress.utils.IOUtils;
import org.hamcrest.Matchers;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.compute.builder.ServerCreateBuilder;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.endsWith;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OpenstackEnhancedDeclarativePipelineTest {
    @Rule
    public PluginTestRule j = new PluginTestRule();
    private JCloudsCloud cloud;
    private Openstack openstack;

    @Before
    public void setup () {
        cloud = j.configureSlaveLaunching(j.dummyCloud());
        //cloud = j.createCloudLaunchingDummySlaves("whatever");
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

    @Test
    public void testIfSlaveCreated() throws Exception {
        WorkflowJob boot = j.jenkins.createProject(WorkflowJob.class, "testIfSlaveCreated");
        boot.setDefinition(new CpsFlowDefinition(loadPipelineScript("declarativeEnhanced.groovy"), true));
        WorkflowRun b = j.assertBuildStatusSuccess(boot.scheduleBuild2(0));
        j.assertLogContains("Hello World!", b);

        ArgumentCaptor<ServerCreateBuilder> captor = ArgumentCaptor.forClass(ServerCreateBuilder.class);
        verify(openstack, times(1)).bootAndWaitActive(captor.capture(), any(Integer.class));
        List<ServerCreateBuilder> builders = captor.getAllValues();
        assertEquals(1, builders.size());
        ServerCreate build = builders.get(0).build();
        assertEquals("kiPerNejm", build.getKeyName());
    }

    protected String loadPipelineScript(String name) {
        try {
            return new String(IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream(name)));
        } catch (Throwable t) {
            throw new RuntimeException("Could not read resource:[" + name + "].");
        }
    }

}
