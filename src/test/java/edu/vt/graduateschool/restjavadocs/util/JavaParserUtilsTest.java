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

  /**
   * Test type for a constructor enclosed class.
   */
  private static Class aClass;

  /**
   * Default constructor.
   */
  public JavaParserUtilsTest()
  {
    /**
     * Constructor enclosed class type.
     */
    class EnclosedConstructorClass
    {

    }
    aClass = EnclosedConstructorClass.class;
  }

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

  /**
   * Test 02
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_02_get_file_path_from_class_enclosed_class_in_class()
  {
    JavaParserUtils.getFilePathFromClass(EnclosedClass.class);
  }

  /**
   * Test 03
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_03_get_file_path_from_class_enclosed_class_in_method()
  {
    /**
     * Method enclosed class type.
     */
    class EnclosedMethodClass
    {

    }
    JavaParserUtils.getFilePathFromClass(EnclosedMethodClass.class);
  }

  /**
   * Test 04
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_04_get_file_path_from_class_enclosed_class_in_constructor()
  {
    JavaParserUtils.getFilePathFromClass(aClass);
  }

  /**
   * Test 05
   */
  @Test
  public void test_05_get_file_path_from_class_java_class()
  {
    Assert.assertEquals(JavaParserUtils.getFilePathFromClass(String.class), "java/lang/String.java");
  }

  /**
   * Test 06
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_06_get_file_path_from_class_enclosed_class_in_constructor()
  {
    JavaParserUtils.getFilePathFromClass(null);
  }

  /**
   * Class enclosed class type.
   */
  class EnclosedClass
  {

  }
  //CheckStyle:MethodName ON
}
