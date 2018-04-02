package jenkins.plugins.openstack.pipeline;

import jenkins.plugins.openstack.compute.JCloudsCloud;
import jenkins.plugins.openstack.compute.JCloudsSlaveTemplate;
import jenkins.plugins.openstack.compute.ServerScope;
import jenkins.plugins.openstack.compute.SlaveOptions;
import jenkins.plugins.openstack.compute.internal.DestroyMachine;
import jenkins.plugins.openstack.compute.internal.Openstack;
import org.jenkinsci.plugins.resourcedisposer.AsyncResourceDisposer;
import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.Whitelisted;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.openstack4j.model.compute.Server;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * Server wrapper for pipeline use.
 */
@Restricted(NoExternalUse.class)
public class SimplifiedServer implements Serializable {
    private static final long serialVersionUID = 860084023141474202L;

    // Null after destroyed
    private @CheckForNull Server srv;
    private @Nonnull String cloud;
    private @Nonnull String template;
    private @Nonnull String scope;
    private @Nonnull SlaveOptions slaveOptions;

    public SimplifiedServer(@Nonnull String cloud, @Nonnull String template, @Nonnull String scope) {
        this.template = template;
        this.cloud = cloud;
        this.scope = scope;

        ServerScope serverscope = ServerScope.parse(scope);
        JCloudsCloud jcl = JCloudsCloud.getByName(cloud);
        JCloudsSlaveTemplate t = jcl.getTemplate(template);
        if (t == null) throw new IllegalArgumentException("Invalid template: " + template);

        this.srv = t.provision(jcl, serverscope);
    }

    //@NotNull on SlaveOptions removed for simlified test to work
    public SimplifiedServer(@Nonnull String cloud, SlaveOptions slaveOptions, @Nonnull String scope) {
        this.cloud = cloud;
        this.slaveOptions = slaveOptions;
        this.scope = scope;

        ServerScope serverScope = ServerScope.parse(scope);
        JCloudsCloud jCloudsCloud = JCloudsCloud.getByName(cloud);
    }

    @Whitelisted
    public void destroy() {
        if (srv == null) return; // Already terminated.

        DestroyMachine dm = new DestroyMachine(this.cloud, srv.getId());
        AsyncResourceDisposer.get().dispose(dm);
        srv = null;
    }

    @Whitelisted
    public String getAddress() {
        if (srv == null) return null;

        return Openstack.getPublicAddress(srv);
    }

    @Whitelisted
    public String getStatus() {
        return srv != null
                ? srv.getStatus().name()
                : null
        ;
    }

    @Whitelisted
    public String getId() {
        return srv != null
                ? srv.getId()
                : null
        ;
    }
}
