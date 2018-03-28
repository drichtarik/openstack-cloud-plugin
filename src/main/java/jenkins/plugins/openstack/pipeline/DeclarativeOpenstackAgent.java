package jenkins.plugins.openstack.pipeline;

import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.pipeline.modeldefinition.agent.DeclarativeAgent;
import org.jenkinsci.plugins.pipeline.modeldefinition.agent.DeclarativeAgentDescriptor;
import org.jenkinsci.plugins.variant.OptionalExtension;
import org.kohsuke.stapler.DataBoundConstructor;


public class DeclarativeOpenstackAgent extends DeclarativeAgent<DeclarativeOpenstackAgent> {

    private final String label;

    @DataBoundConstructor
    public DeclarativeOpenstackAgent(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @OptionalExtension(requirePlugins = "pipeline-model-extensions") @Symbol("openstack")
    public static class DescriptorImpl extends DeclarativeAgentDescriptor<DeclarativeOpenstackAgent> {
    }
}
