/**
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.environment.servlet.deployment;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for {@link Scanner} providing common functionality
 * 
 * This class provides file-system orientated scanning
 * 
 * @author Pete Muir
 * 
 */
public abstract class AbstractScanner implements Scanner
{

   private static final Logger log = LoggerFactory.getLogger(Scanner.class);
   
   private final ClassLoader classLoader;
   private final WebAppBeanDeploymentArchive webAppBeanDeploymentArchive;
   
   public AbstractScanner(ClassLoader classLoader, WebAppBeanDeploymentArchive webBeanDiscovery)
   {
      this.classLoader = classLoader;
      this.webAppBeanDeploymentArchive = webBeanDiscovery;
   }
   
   protected void handle(String name, URL url)
   {
      if (name.endsWith(".class"))
      {
         String className = filenameToClassname(name);
         try
         {
            webAppBeanDeploymentArchive.getBeanClasses().add(getClassLoader().loadClass(className));
         }
         catch (NoClassDefFoundError e)
         {
            log.error("Error loading " + name, e);
         }
         catch (ClassNotFoundException e)
         {
            log.error("Error loading " + name, e);
         }
      }
      else if (name.equals(WebAppBeanDeploymentArchive.META_INF_BEANS_XML))
      {
         webAppBeanDeploymentArchive.getWeldUrls().add(url);
      }
   }
   
   public ClassLoader getClassLoader()
   {
      return classLoader;
   }
   
   /**
    * Convert a path to a class file to a class name
    */
   public static String filenameToClassname(String filename)
   {
      return filename.substring( 0, filename.lastIndexOf(".class") )
            .replace('/', '.').replace('\\', '.');
   }
   
}
