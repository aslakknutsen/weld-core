/*
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
package org.jboss.webbeans.ejb.spi;

import org.jboss.webbeans.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.webbeans.bootstrap.spi.Deployment;

/**
 * Represents an EJB bean deployment archive.
 * 
 * If a bean deployment archive is identified as an EJB bean deployment, an
 * instance of {@link EJBModule} should be returned instead of
 * {@link BeanDeploymentArchive}; the Java EE container is responsible for
 * identifying EJB bean deployment archives.
 * 
 * @see BeanDeploymentArchive
 * @see Deployment
 * 
 * @author Pete Muir
 * 
 */
public interface EJBModule extends BeanDeploymentArchive
{

   /**
    * Get all the EJBs in the deployment archive 
    * 
    * @return an iteration of the EJBs, or empty if no EJBs are present
    */
   public Iterable<EjbDescriptor<?>> getEjbs();

}
