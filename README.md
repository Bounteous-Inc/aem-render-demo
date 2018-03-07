# AEM Granite Render Condition Demo

This project contains all the source code for the demo given at the AEM Meetup in San Francisco on 03/05/18.
Link to Adobe Connect recording: (?)
It is a demo of how a custom Granite Render Condition can be leveraged inside of AEM to show/hide certain elements based on a user's roles.

## Code structure

The project was built using the [AEM Maven Archetype v12](https://github.com/Adobe-Marketing-Cloud/aem-project-archetype) and follows the same module structure as that project. Most of the sample code and components generated by the archetype have been removed, but the following are the relevant files added for the purpose of the demo:
* com.infield.aem.render.core.dialog.ui.AccessRenderCondition
* com.infield.aem.render.core.services.DialogAccess
* com.infield.aem.render.core.services.impl.DialogAccessImpl
* ui.apps/src/main/content/jcr_root/apps/render-demo/components/renderconditions/access/access.jsp
* ui.apps/src/main/content/jcr_root/apps/render-demo/components/content/image/_cq_dialog.xml

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with

    mvn clean install -PautoInstallPackage

Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish

Or alternatively

    mvn clean install -PautoInstallPackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
