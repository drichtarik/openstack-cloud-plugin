package jenkins.plugins.openstack.pipeline;

import jenkins.plugins.openstack.compute.slaveopts.BootSource;
import jenkins.plugins.openstack.compute.slaveopts.LauncherFactory;
import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.pipeline.modeldefinition.agent.DeclarativeAgent;
import org.jenkinsci.plugins.pipeline.modeldefinition.agent.DeclarativeAgentDescriptor;
import org.jenkinsci.plugins.variant.OptionalExtension;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.Map;
import java.util.TreeMap;


public class DeclarativeOpenstackAgent extends DeclarativeAgent<DeclarativeOpenstackAgent> {

    private String cloud;

    private BootSource bootSource;
    private String hardwareId;
    private String networkId;
    private String userDataId;
    private String floatingIpPool;
    private String securityGroups;
    private String availabilityZone;
    private Integer startTimeout;
    private String keyPairName;
    private String jvmOptions;
    private String fsRoot;
    private LauncherFactory launcherFactory;

    @DataBoundConstructor
    public DeclarativeOpenstackAgent(String cloud, BootSource bootSource, String hardwareId, String networkId, String userDataId, String floatingIpPool, String securityGroups, String availabilityZone, Integer startTimeout, String keyPairName, String jvmOptions, String fsRoot, LauncherFactory launcherFactory) {
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

    public Map<String,Object> getAsArgs() {
        Map<String,Object> argMap = new TreeMap<>();

        argMap.put("cloud", cloud);
        argMap.put("bootSource", bootSource);
        argMap.put("hardwareId", hardwareId);
        argMap.put("networkId", networkId);
        argMap.put("userDataId", userDataId);
        argMap.put("floatingIpPool", floatingIpPool);
        argMap.put("securityGroups", securityGroups);
        argMap.put("availabilityZone", availabilityZone);
        argMap.put("startTimeout", startTimeout);
        argMap.put("keyPairName", keyPairName);
        argMap.put("jvmOptions", jvmOptions);
        argMap.put("fsRoot", fsRoot);
        argMap.put("launcherFactory", launcherFactory);

        return argMap;
    }

    @OptionalExtension(requirePlugins = "pipeline-model-extensions") @Symbol("openstack")
    public static class DescriptorImpl extends DeclarativeAgentDescriptor<DeclarativeOpenstackAgent> {
    }
}
