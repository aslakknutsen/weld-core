package org.jboss.weld.test.tomcat.examples;

import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.weld.test.AbstractWeldTest;
import org.jboss.weld.test.Utils;
import org.jboss.weld.test.harness.ServletLifecycleContainersImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Artifact
public class ExampleTest extends AbstractWeldTest
{
   
   @Test
   public void testGameGenerator() throws Exception 
   {
      Game game1 = Utils.getReference(getCurrentManager(), Game.class);
      Game game2 = Utils.getReference(getCurrentManager(), Game.class);
      assert game1!=game2;
      assert game1.getNumber()!=game2.getNumber();
      Generator gen1 = Utils.getReference(getCurrentManager(), Generator.class);
      Generator gen2 = Utils.getReference(getCurrentManager(), Generator.class);
      assert gen1.getRandom()!=null;
      assert gen1.getRandom()==gen2.getRandom();
   }

   @Test
   public void testSentenceTranslator() throws Exception 
   {
        
      TextTranslator tt1 = Utils.getReference(getCurrentManager(), TextTranslator.class);
      try 
      {
         tt1.translate("hello world");
         assert false;
      }
      catch (UnsupportedOperationException uoe)
      {
         //expected
      }
   }
   
}
