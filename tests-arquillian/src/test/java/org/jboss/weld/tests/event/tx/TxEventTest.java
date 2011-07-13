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
package org.jboss.weld.tests.event.tx;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.weld.tests.category.Integration;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

@Category(Integration.class)
@RunWith(Arquillian.class)
@RunAsClient
public class TxEventTest extends AbstractHtmlUnit
{
   @Deployment(testable = false)
   public static WebArchive createDeployment()
   {
      WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
               .addClasses(Foo.class, Updated.class)
               .addAsWebResource(TxEventTest.class.getPackage(), "web.xml", "web.xml")
               .addAsWebResource(TxEventTest.class.getPackage(), "faces-config.xml", "faces-config.xml")
               .addAsResource(TxEventTest.class.getPackage(), "home.xhtml", "home.xhtml")
               .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
      
      war.toString(Formatters.VERBOSE);
      
      return war;
   }

   /*
    * description = "WBRI-401"
    */
   @Test
   public void testRequestContextLifecycle() throws Exception
   {
      WebClient webClient = new WebClient();
      HtmlPage home = webClient.getPage(getPath("/home.jsf"));
      HtmlSubmitInput beginConversationButton = getFirstMatchingElement(home, HtmlSubmitInput.class, "SaveButton");
      beginConversationButton.click();
   }

   protected String getPath(String page)
   {
      // TODO: this should be moved out and be handled by Arquillian
      return "http://localhost:8080/test/" + page;
   }
}