/*
 * This file was automatically generated by EvoSuite
 * Fri Jun 14 19:28:49 GMT 2024
 */

package ir.ramtung.impl2;

import org.junit.Test;
import static org.junit.Assert.*;
import ir.ramtung.impl2.Book;
import ir.ramtung.impl2.Document;
import ir.ramtung.impl2.Magazine;
import ir.ramtung.impl2.Student;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class Student_ESTest extends Student_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      Student student0 = new Student("", "");
      assertTrue(student0.canBorrow());
      
      Magazine magazine0 = new Magazine("", 669, 669, 4654);
      List<Document> list0 = List.of((Document) magazine0, (Document) magazine0);
      student0.docsBorrow = list0;
      boolean boolean0 = student0.canBorrow();
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      Student student0 = new Student((String) null, (String) null);
      boolean boolean0 = student0.canBorrow();
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      Student student0 = new Student("}", "");
      assertTrue(student0.canBorrow());
      
      Book book0 = new Book("|", 1390);
      List<Document> list0 = List.of((Document) book0, (Document) book0, (Document) book0, (Document) book0, (Document) book0, (Document) book0, (Document) book0, (Document) book0, (Document) book0);
      student0.docsBorrow = list0;
      boolean boolean0 = student0.canBorrow();
      assertFalse(boolean0);
  }
}
