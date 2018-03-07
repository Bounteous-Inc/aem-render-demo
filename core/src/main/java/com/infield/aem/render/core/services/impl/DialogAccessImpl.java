package com.infield.aem.render.core.services.impl;

import java.util.Arrays;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import com.infield.aem.render.core.services.DialogAccess;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.base.util.AccessControlUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service Implementation for {@link DialogAccess}.
 */
@Component(service = DialogAccess.class, configurationPolicy = ConfigurationPolicy.OPTIONAL)
@Designate(ocd = DialogAccessImpl.Config.class)
public class DialogAccessImpl implements DialogAccess {

    private static final Logger log = LoggerFactory.getLogger(DialogAccess.class);
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_GROUP = "administrators";

    String[] defaultPrincipals;

    @Override
    public boolean canDisplay(ResourceResolver resourceResolver, String... principals) {

        // Admin user should always have access
        if (resourceResolver.getUserID().equals(ADMIN_USER)) {
            return true;
        }

        // Default to the list of principals in the OSGi configuration
        if (principals == null || principals.length == 0) {
            principals = defaultPrincipals;
        }
        Session session = resourceResolver.adaptTo(Session.class);
        UserManager userManager;
        try {
            userManager = AccessControlUtil.getUserManager(session);
        } catch (RepositoryException e) {
            log.error("Could not get UserManager. ", e);
            return false;
        }
        try {
            // Get current user from Resource Resolver ID
            Authorizable user = userManager.getAuthorizable(resourceResolver.getUserID());

            // Iterate over groups and check user membership
            for (String groupName : principals) {
                Authorizable group = userManager.getAuthorizable(groupName);
                if (group != null && group.isGroup()) {

                    // Using isMember to respect group hierarchy
                    if (((Group) group).isMember(user)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (RepositoryException e) {
            log.error("Error checking user groups. ", e);
            return false;
        }
    }

    /**
     * Activates the service with the configuration values.
     *
     * @param config The OSGi config bound with the service implementation.
     */
    @Activate
    public void activate(Config config) {
        this.defaultPrincipals = config.default_principals();
        if (defaultPrincipals != null && defaultPrincipals.length > 0) {

            // If the current list does not contain the administrators group it will be added
            List<String> listPrincipals = Arrays.asList(defaultPrincipals);
            if (!listPrincipals.contains(ADMIN_GROUP)) {
                listPrincipals.add(ADMIN_GROUP);
                defaultPrincipals = listPrincipals.toArray(new String[defaultPrincipals.length + 1]);
            }
        }
    }

    @ObjectClassDefinition(name = "Infield Digital Dialog Access Service")
    public @interface Config {

        /**
         * Sets the default.principals config property. The list will automatically add the
         * 'administrators' group if not already defined in the array of groups.
         *
         * @return The List of default principals.
         */
        @AttributeDefinition(name = "Default Principals",
            description = "List of default principals for dialog access. Automatically includes the "
                    + ADMIN_GROUP + " group.", defaultValue = {"administrators"})
        String[] default_principals();
    }

}
