package org.jboss.weld.environment.servlet.test.el;

import static org.jboss.weld.environment.servlet.test.util.TomcatDeployments.CONTEXT_XML;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@RunAsClient
@RunWith(Arquillian.class)
@Ignore
// This test runs fine in the IDE, but I can't get UEL 2.2 to run on the classpath
public class JsfTest extends JsfTestBase
{

   @Deployment
   public static WebArchive deployment()
   {
      return JsfTestBase.deployment().add(CONTEXT_XML, "META-INF/context.xml");
   }
   
   @Override
   protected String getPath(String page)
   {
      return "http://localhost:8888/test/" + page;
   }
   
}
