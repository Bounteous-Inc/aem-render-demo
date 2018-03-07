<%--
  ==============================================================================
  has property render condition
   A condition that takes a resource path and property name and makes the
   rendering decision based on whether that resource has the property.
  /**
   * The resource path of the resource to evaluate.
   */
  - resourcePath (String)
  /**
   * The property name of the resource to evaluate.
   */
  - propertyName (String)
  ==============================================================================
--%><%
%><%@include file="/libs/granite/ui/global.jsp" %><%
%><%@page session="false"
          import="com.adobe.granite.ui.components.Config,
                  com.adobe.granite.ui.components.rendercondition.RenderCondition,
                  com.adobe.granite.ui.components.rendercondition.SimpleRenderCondition,
                  com.infield.aem.render.core.dialog.ui.AccessRenderCondition,
                  org.apache.sling.api.resource.ResourceResolver,
                  org.apache.sling.api.resource.Resource"%>
<%
    ResourceResolver resolver = resourceResolver;
    if (resolver != null) {
        /*
         *
         */
        Config cfg = new Config(resource);
        String[] principals = cfg.get("principals", String[].class);

        /* Display or hide the widget */
        request.setAttribute(RenderCondition.class.getName(), new AccessRenderCondition(resourceResolver, sling, principals));
    } else {
        /* If resolver is null default to hide widget */
        request.setAttribute(RenderCondition.class.getName(), SimpleRenderCondition.FALSE);
    }
%>
