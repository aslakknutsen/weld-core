/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
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
package org.jboss.weld.tests.ejb.mdb;

import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.weld.tests.category.Broken;
import org.jboss.weld.tests.category.Integration;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import javax.jms.*;
import javax.naming.InitialContext;

@Category(Integration.class)
@RunWith(Arquillian.class)
public class EJBTest
{
   public static final String MESSAGE = "Hello!";

   @Deployment
   public static Archive<?> deploy()
   {
      return ShrinkWrap.create(EnterpriseArchive.class, "test.ear")
         .addAsModule(
               ShrinkWrap.create(JavaArchive.class)
                  .addPackage(EJBTest.class.getPackage())
                  .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                  //.addAsManifestResource(EJBTest.class.getPackage(), "test-destinations-service.xml", "test-destinations-service.xml")
         );
   }

   @Category(Broken.class)
   @Test
   // TODO Need a way to deploy test-destinations-service.xml to JBoss AS
   public void testMdbUsable(Control control) throws Exception
   {
      InitialContext ctx = new InitialContext();
      QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
      QueueConnection connection = factory.createQueueConnection();
      QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
      Queue queue = (Queue) ctx.lookup("queue/testQueue");
      QueueSender sender = session.createSender(queue);
      sender.send(session.createTextMessage(MESSAGE));

      // TODO: rewrite to use CountDownLatch, avoid Thread.sleep in tests
      Thread.sleep(1000);
      Assert.assertTrue(control.isMessageDelivered());
      Assert.assertTrue(control.isContextSet());
   }

}
