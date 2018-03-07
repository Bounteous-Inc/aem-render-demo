package com.infield.aem.render.core.dialog.ui;

import java.io.IOException;

import javax.servlet.ServletException;

import com.adobe.granite.ui.components.rendercondition.RenderCondition;
import com.infield.aem.render.core.services.DialogAccess;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.scripting.SlingScriptHelper;

/**
 * Render condition to display based on current user roles.
 */
public class AccessRenderCondition implements RenderCondition {

    boolean decision;

    public AccessRenderCondition(ResourceResolver resolver, SlingScriptHelper sling, String[]principals) {

        // Get DialogAccess service from current Sling context to run the check.
        DialogAccess service  = sling.getService(DialogAccess.class);
        this.decision = service.canDisplay(resolver, principals);
    }

    @Override
    public boolean check() throws ServletException, IOException {
        return this.decision;
    }
}
