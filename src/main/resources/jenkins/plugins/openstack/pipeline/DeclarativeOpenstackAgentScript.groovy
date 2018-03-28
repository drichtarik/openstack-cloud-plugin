package jenkins.plugins.openstack.pipeline

import org.jenkinsci.plugins.pipeline.modeldefinition.Utils
import org.jenkinsci.plugins.pipeline.modeldefinition.agent.DeclarativeAgentScript
import org.jenkinsci.plugins.pipeline.modeldefinition.agent.impl.Label

public class DeclarativeOpenstackAgentScript extends DeclarativeAgentScript<DeclarativeOpenstackAgent> {

    DeclarativeOpenstackAgentScript(org.jenkinsci.plugins.workflow.cps.CpsScript s, DeclarativeOpenstackAgent declarativeOpenstackAgent) {
        super(s, declarativeOpenstackAgent)
    }

    /*
    @Override
    Closure run(Closure closure) {
        return {
            try {
                script.node(describable?.label) {
                    CheckoutScript.doCheckout(script, describable, describable.customWorkspace, body).call()
                }
            } catch (Exception e) {
                script.getProperty("currentBuild").result = Utils.getResultFromException(e)
                throw e
            }
        }
    }

    */

    @Override
    Closure run (Closure body) {
        return body;
    }
}
