/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.util;

import com.github.javaparser.utils.SourceRoot;
import org.testng.Assert;
import org.testng.annotations.Test;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getResolvingSourceRoot;

/**
 * Tests {@link JavaParserUtils} methods.
 *
 * @author Graduate School
 */
public class JavaParserUtilsTest
{
  //CheckStyle:MethodName OFF
  /**
   * Test 00
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_00_get_resolving_source_root_null_source_root()
  {
    getResolvingSourceRoot(null);
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 01
   */
  @Test
  public void test_01_get_resolving_source_root()
  {
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_MAIN_PATH);
    Assert.assertEquals(root.getRoot().toString(), "src/main/java");
  }
  //CheckStyle:MethodName ON
}
