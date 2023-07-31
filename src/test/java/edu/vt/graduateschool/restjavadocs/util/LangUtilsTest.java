/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.util;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests {@link LangUtils} methods.
 *
 * @author Graduate School
 */
public class LangUtilsTest
{

  /**
   * Test type for a constructor enclosed class.
   */
  private static Class aClass;

  /**
   * Default constructor.
   */
  public LangUtilsTest()
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
  public void test_00_all_with_null_type()
  {
    LangUtils.all(null, new Object[]{});
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 01
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_01_all_with_null_inputs()
  {
    LangUtils.all(Object.class, (Object[]) null);
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 02
   */
  @Test
  public void test_02_all_with_all_inputs()
  {
    final String[] collected = LangUtils.all(String.class, "Hello", "World", new Object[]{new Object[]{"nested"}});
    Assert.assertEquals(collected.length, 3);
    Assert.assertEquals(collected[0], "Hello");
    Assert.assertEquals(collected[1], "World");
    Assert.assertEquals(collected[2], "nested");
  }

  /**
   * Test 03
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_03_get_file_path_from_class_enclosed_class_in_class()
  {
    LangUtils.getFilePathFromClass(EnclosedClass.class);
  }

  /**
   * Test 04
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_04_get_file_path_from_class_enclosed_class_in_method()
  {
    /**
     * Method enclosed class type.
     */
    class EnclosedMethodClass
    {

    }
    LangUtils.getFilePathFromClass(EnclosedMethodClass.class);
  }

  /**
   * Test 05
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_05_get_file_path_from_class_enclosed_class_in_constructor()
  {
    LangUtils.getFilePathFromClass(aClass);
  }

  /**
   * Test 06
   */
  @Test
  public void test_06_get_file_path_from_class_java_class()
  {
    Assert.assertEquals(LangUtils.getFilePathFromClass(String.class), "java/lang/String.java");
  }

  /**
   * Test 07
   */
  @Test
  public void test_07_spaghettify()
  {
    final List<String> testLeft = new ArrayList<>();
    final List<String> testRight = new ArrayList<>();
    testRight.add("I am right 1");
    testRight.add("I am right 2");
    testLeft.add("I am left 1");
    testLeft.add("I am left 2");
    final String[] result = LangUtils.spaghettify(String.class, testLeft, testRight);
    Assert.assertEquals(result.length, 4);
    Assert.assertEquals(result[0], "I am left 1");
    Assert.assertEquals(result[result.length - 1], "I am right 2");
  }

  /**
   * Test 08
   */
  @Test
  public void test_08_spaghettify()
  {
    final List<String> testLeft = new ArrayList<>();
    testLeft.add("I am left");
    final String[] result = LangUtils.spaghettify(String.class, testLeft, "I am right");
    Assert.assertEquals(result.length, 2);
    Assert.assertEquals(result[0], "I am left");
    Assert.assertEquals(result[1], "I am right");
  }

  /**
   * Test 09
   */
  @Test
  public void test_09_spaghettify()
  {
    final List<String> testLeft = new ArrayList<>();
    testLeft.add("I am left");
    final String[] testRight = new String[]{"I am right"};
    final String[] result = LangUtils.spaghettify(String.class, testLeft, testRight);
    Assert.assertEquals(result.length, 2);
    Assert.assertEquals(result[0], "I am left");
    Assert.assertEquals(result[1], "I am right");
  }

  /**
   * Test 10
   */
  @Test
  public void test_10_spaghettify()
  {
    final Object[] test = new Object[]{"I am right", 137};
    final String[] result = LangUtils.spaghettify(String.class, "I am left", test);
    Assert.assertEquals(result.length, 2);
    Assert.assertEquals(result[0], "I am left");
    Assert.assertEquals(result[1], "I am right");
  }

  /**
   * Test 11
   */
  @Test
  public void test_11_spaghettify()
  {
    final String[] result = LangUtils.spaghettify(String.class, 5);
    Assert.assertEquals(result.length, 0);
  }

  /**
   * Test 12
   */
  @Test
  public void test_12_spaghettify()
  {
    final String[] result = LangUtils.spaghettify(String.class, "I am right");
    Assert.assertEquals(result.length, 1);
    Assert.assertEquals(result[0], "I am right");
  }

  /**
   * Test 13
   */
  @Test
  public void test_13_spaghettify()
  {
    final Object[] test = new Object[]{"I am left", new Object[]{"I am right", 137}};
    final String[] result = LangUtils.spaghettify(String.class, test);
    Assert.assertEquals(result.length, 2);
    Assert.assertEquals(result[0], "I am left");
    Assert.assertEquals(result[1], "I am right");
  }

  /**
   * Test 14
   */
  @Test
  public void test_14_spaghettify()
  {
    final Object[] test = new Object[]{"I am left", new Object[]{"I am nested now", 137}};
    final String[] result = LangUtils.spaghettify(String.class, test, "I am right");
    Assert.assertEquals(result.length, 2);
    Assert.assertEquals(result[0], "I am left");
    Assert.assertEquals(result[1], "I am right");
  }

  /**
   * Test 15
   */
  @Test
  public void test_15_spaghettify()
  {
    final Object[] test = new Object[]{"I am left", new Object[]{"I am nested now", 137}};
    final String[] result = LangUtils.spaghettify(1, String.class, test, "I am right");
    Assert.assertEquals(result.length, 3);
    Assert.assertEquals(result[0], "I am left");
    Assert.assertEquals(result[1], "I am nested now");
    Assert.assertEquals(result[2], "I am right");
  }

  /**
   * Test 16
   */
  @Test
  public void test_16_spaghettify()
  {
    final List<String> nestedCollection = new ArrayList<>();
    nestedCollection.add("I am nested now");
    final Object[] test = new Object[]{"I am left", nestedCollection};
    final String[] result = LangUtils.spaghettify(1, String.class, test, "I am right");
    Assert.assertEquals(result.length, 3);
    Assert.assertEquals(result[0], "I am left");
    Assert.assertEquals(result[1], "I am nested now");
    Assert.assertEquals(result[2], "I am right");
  }

  /**
   * Test 17
   */
  @Test
  public void test_17_spaghettify()
  {
    final List<String> nestedCollection = new ArrayList<>();
    nestedCollection.add("I am nested now");
    final List<String> veryNestedCollection = new ArrayList<>();
    veryNestedCollection.add("I am middle middle");
    final Object[] test = new Object[]{"I am left", nestedCollection,
      new Object[]{new Object[]{new Object[]{"I am middle left", veryNestedCollection}}, "I am middle right"}, };
    final String[] result = LangUtils.spaghettify(3, String.class, test, "I am right");
    Assert.assertEquals(result.length, 5);
    Assert.assertEquals(result[0], "I am left");
    Assert.assertEquals(result[1], "I am nested now");
    Assert.assertEquals(result[2], "I am middle left");
    Assert.assertEquals(result[3], "I am middle right");
    Assert.assertEquals(result[4], "I am right");
  }

  /**
   * Test 18
   */
  @Test
  public void test_18_spaghettify()
  {
    final List<String> nestedCollection = new ArrayList<>();
    nestedCollection.add("I am nested now");
    final List<String> veryNestedCollection = new ArrayList<>();
    veryNestedCollection.add("I am middle middle");
    final Object[] test = new Object[]{"I am left", nestedCollection,
      new Object[]{new Object[]{new Object[]{"I am middle left", veryNestedCollection}}, "I am middle right"}, };
    final String[] result = LangUtils.spaghettify(10, String.class, test, "I am right");
    Assert.assertEquals(result.length, 6);
    Assert.assertEquals(result[0], "I am left");
    Assert.assertEquals(result[1], "I am nested now");
    Assert.assertEquals(result[2], "I am middle left");
    Assert.assertEquals(result[3], "I am middle middle");
    Assert.assertEquals(result[4], "I am middle right");
    Assert.assertEquals(result[5], "I am right");
  }

  /**
   * Test 19
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_19_spaghettify_class_null()
  {
    LangUtils.spaghettify(10, null, new ArrayList<>(), new Object[]{});
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 20
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_20_spaghettify_inputs_null()
  {
    LangUtils.spaghettify(10, String.class, new ArrayList<>(), (Object[]) null);
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 21
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_21_spaghettify_initial_null()
  {
    LangUtils.spaghettify(10, String.class, null, new Object[]{});
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 22
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_22_get_file_path_from_class_enclosed_class_in_constructor()
  {
    LangUtils.getFilePathFromClass(null);
  }

  /**
   * Class enclosed class type.
   */
  class EnclosedClass
  {

  }
  //CheckStyle:MethodName ON
}
