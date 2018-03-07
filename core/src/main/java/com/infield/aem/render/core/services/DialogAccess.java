package com.infield.aem.render.core.services;

import org.apache.sling.api.resource.ResourceResolver;

/**
 * Service used to define access to certain dialog resources.
 */
public interface DialogAccess {

    /**
     * Checks if the current {@link ResourceResolver} user ID has the correct permissions to view
     * the resource given the optionally passed principals or the OSGi configuration.
     *
     * @param resourceResolver Current ResourceResolver from the Request.
     * @param principals Optional list of principals to override from OSGi config.
     * @return A boolean whether or not the resource should be displayed.
     */
    boolean canDisplay(ResourceResolver resourceResolver, String... principals);
}
