/*
 * This file was automatically generated by EvoSuite
 * Fri Jun 14 18:26:39 GMT 2024
 */

package ir.ramtung.impl1;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import ir.ramtung.impl1.Magazine;
import ir.ramtung.impl1.Reference;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class Document_ESTest extends Document_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      Magazine magazine0 = new Magazine((String) null, 1, 1);
      boolean boolean0 = magazine0.isTitled("Iz~AQCMj#-l?RuDt!E");
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      Magazine magazine0 = new Magazine((String) null, 1, 1);
      magazine0.title = "Magazine with zero or negative publication year";
      String string0 = magazine0.getTitle();
      assertEquals("Magazine with zero or negative publication year", string0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      Reference reference0 = new Reference("ir.ramtung.impl1.Book");
      reference0.title = "";
      String string0 = reference0.getTitle();
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      Magazine magazine0 = null;
      try {
        magazine0 = new Magazine("", 3445, 0);
        fail("Expecting exception: Exception");
      
      } catch(Throwable e) {
         //
         // Documents with empty title are not allowed
         //
         verifyException("ir.ramtung.impl1.Document", e);
      }
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      Reference reference0 = new Reference((String) null);
      boolean boolean0 = reference0.isTitled((String) null);
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      Reference reference0 = new Reference((String) null);
      String string0 = reference0.getTitle();
      assertNull(string0);
  }
}
