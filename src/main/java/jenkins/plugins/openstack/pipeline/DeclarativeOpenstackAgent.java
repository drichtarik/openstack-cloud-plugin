package jenkins.plugins.openstack.pipeline;

import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.pipeline.modeldefinition.agent.DeclarativeAgent;
import org.jenkinsci.plugins.pipeline.modeldefinition.agent.DeclarativeAgentDescriptor;
import org.jenkinsci.plugins.variant.OptionalExtension;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.Map;
import java.util.TreeMap;


public class DeclarativeOpenstackAgent extends DeclarativeAgent<DeclarativeOpenstackAgent> {

    private String cloud;

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
    public DeclarativeOpenstackAgent(String cloud) {
        this.cloud = cloud;
    }

    @DataBoundSetter
    public void setBootSource(String bootSource) { this.bootSource = bootSource; }

    @DataBoundSetter
    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    @DataBoundSetter
    public void setNetworkId(String networkId) { this.networkId = networkId; }

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

    public Map<String,Object> getAsArgs() {
        Map<String,Object> argMap = new TreeMap<>();

        argMap.put("cloud", cloud);
        argMap.put("bootSource", bootSource);
        argMap.put("hardwareId", hardwareId);
        argMap.put("networkId", networkId);
        argMap.put("userDataId", userDataId);
        argMap.put("instanceCap", instanceCap);
        argMap.put("floatingIpPool", floatingIpPool);
        argMap.put("securityGroups", securityGroups);
        argMap.put("availabilityZone", availabilityZone);
        argMap.put("startTimeout", startTimeout);
        argMap.put("keyPairName", keyPairName);
        argMap.put("numExecutors", numExecutors);
        argMap.put("jvmOptions", jvmOptions);
        argMap.put("fsRoot", fsRoot);
        argMap.put("launcherFactory", launcherFactory);
        argMap.put("retentionTime", retentionTime);

        return argMap;
    }

    @OptionalExtension(requirePlugins = "pipeline-model-extensions") @Symbol("openstack")
    public static class DescriptorImpl extends DeclarativeAgentDescriptor<DeclarativeOpenstackAgent> {
    }
}
