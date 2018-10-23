package jenkins.plugins.openstack.compute.auth;

import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.SystemCredentialsProvider;
import hudson.security.ACL;
import jenkins.model.Jenkins;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class OpenstackCredentials {

    /**
     * @return null when the credentials are not found.
     */
    public static @CheckForNull OpenstackCredential getCredential(@CheckForNull String credentialId) {
        if (credentialId == null) return null;

        List<OpenstackCredential> credentials = CredentialsProvider.lookupCredentials(
                        OpenstackCredential.class, Jenkins.getInstance(), ACL.SYSTEM,
                        Collections.emptyList()
        );
        return CredentialsMatchers.firstOrNull(credentials, CredentialsMatchers.withId(credentialId));
    }

    public static void add(@Nonnull OpenstackCredential openstackCredential) {
        SystemCredentialsProvider.getInstance().getCredentials().add(openstackCredential);
    }

    public static void save() throws IOException {
        SystemCredentialsProvider.getInstance().save();
    }
}
