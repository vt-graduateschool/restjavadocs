/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.request;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests {@link RequestMappingFilter} methods.
 *
 * @author Graduate School
 */
public class RequestMappingFilterTest
{

  //CheckStyle:MethodName OFF
  /**
   * Test of methodNames method, of class RequestMappingFilter.
   */
  @Test
  public void test_00_constructors()
  {
    final String nameParam = "nameParam";
    final String[] pathParam = new String[]{"pathParam"};
    final String[] methodParam = new String[]{"RequestMethod.GET"};
    final String[] paramsParam = new String[]{"paramsParam"};
    final String[] headersParam = new String[]{"headersParam"};
    final String[] consumesParam = new String[]{"consumesParam"};
    final String[] producesParam = new String[]{"producesParam"};

    final RequestMappingFilter one = new RequestMappingFilter();
    final RequestMappingFilter three = new RequestMappingFilter("nameParam");
    final RequestMappingFilter four = new RequestMappingFilter(pathParam);
    final RequestMappingFilter five = new RequestMappingFilter("nameParam", pathParam);
    final RequestMappingFilter six = new RequestMappingFilter(pathParam, methodParam);
    final RequestMappingFilter seven = new RequestMappingFilter(pathParam, methodParam,
            consumesParam);
    final RequestMappingFilter nine = new RequestMappingFilter(pathParam, methodParam, consumesParam, producesParam);
    final RequestMappingFilter ten =
            new RequestMappingFilter(pathParam, methodParam, headersParam, consumesParam, producesParam);
    final RequestMappingFilter eleven =
            new RequestMappingFilter(pathParam, methodParam, paramsParam, headersParam, consumesParam, producesParam);
    final RequestMappingFilter twelve =
            new RequestMappingFilter(nameParam, pathParam, methodParam, paramsParam, headersParam, consumesParam,
                    producesParam);
    Assert.assertEquals(one.getValue(), one.getPath());
    Assert.assertEquals(three.getValue(), three.getPath());
    Assert.assertEquals(four.getValue(), four.getPath());
    Assert.assertEquals(five.getValue(), five.getPath());
    Assert.assertEquals(six.getValue(), six.getPath());
    Assert.assertEquals(seven.getValue(), seven.getPath());
    Assert.assertEquals(nine.getValue(), nine.getPath());
    Assert.assertEquals(ten.getValue(), ten.getPath());
    Assert.assertEquals(eleven.getValue(), eleven.getPath());
    Assert.assertEquals(twelve.getValue(), twelve.getPath());
  }

  /**
   * Test of name method, of class RequestMappingFilter.
   */
  @Test
  public void test_01_get_name()
  {
    final RequestMappingFilter instance = new RequestMappingFilter("name");
    final String expResult = "name";
    final String result = instance.getName();
    Assert.assertEquals(expResult, result);
  }

  /**
   * Test of value method, of class RequestMappingFilter.
   */
  @Test
  public void test_02_get_value()
  {
    final String[] expResult = new String[]{"value"};
    final RequestMappingFilter instance = new RequestMappingFilter(expResult);
    final String[] result = instance.getValue();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of path method, of class RequestMappingFilter.
   */
  @Test
  public void test_03_get_path()
  {
    final String[] expResult = new String[]{"path"};
    final RequestMappingFilter instance = new RequestMappingFilter(expResult);
    final String[] result = instance.getPath();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of method method, of class RequestMappingFilter.
   */
  @Test
  public void test_04_get_method()
  {
    final String[] expResult = new String[]{"RequestMethod.GET"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setMethod(expResult);
    final String[] result = instance.getMethod();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of params method, of class RequestMappingFilter.
   */
  @Test
  public void test_05_get_params()
  {
    final String[] expResult = new String[]{"params"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setParams(expResult);
    final String[] result = instance.getParams();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of headers method, of class RequestMappingFilter.
   */
  @Test
  public void test_06_get_headers()
  {
    final String[] expResult = new String[]{"headers"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setHeaders(expResult);
    final String[] result = instance.getHeaders();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of consumes method, of class RequestMappingFilter.
   */
  @Test
  public void test_07_get_consumes()
  {
    final String[] expResult = new String[]{"consumes"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setConsumes(expResult);
    final String[] result = instance.getConsumes();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of produces method, of class RequestMappingFilter.
   */
  @Test
  public void test_08_get_produces()
  {
    final String[] expResult = new String[]{"produces"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setProduces(expResult);
    final String[] result = instance.getProduces();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of setName method, of class RequestMappingFilter.
   */
  @Test
  public void test_09_set_name()
  {
    final String nameParam = "setName";
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setName(nameParam);
    Assert.assertEquals(nameParam, instance.getName());
  }

  /**
   * Test of setValue method, of class RequestMappingFilter.
   */
  @Test
  public void test_10_set_value()
  {
    final String[] valueParam = new String[]{"value"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setValue(valueParam);
    assertArrayEquals(valueParam, instance.getValue());
  }

  /**
   * Test of setPath method, of class RequestMappingFilter.
   */
  @Test
  public void test_11_set_path()
  {
    final String[] pathParam = new String[]{"path"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setPath(pathParam);
    assertArrayEquals(pathParam, instance.getPath());
  }

  /**
   * Test of setParams method, of class RequestMappingFilter.
   */
  @Test
  public void test_12_set_params()
  {
    final String[] paramsParam = new String[]{"params"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setParams(paramsParam);
    assertArrayEquals(paramsParam, instance.getParams());
  }

  /**
   * Test of setHeaders method, of class RequestMappingFilter.
   */
  @Test
  public void test_13_set_headers()
  {
    final String[] headersParam = new String[]{"headers"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setHeaders(headersParam);
    assertArrayEquals(headersParam, instance.getHeaders());
  }

  /**
   * Test of setConsumes method, of class RequestMappingFilter.
   */
  @Test
  public void test_14_set_consumes()
  {
    final String[] consumesParam = new String[]{"consumes"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setConsumes(consumesParam);
    assertArrayEquals(consumesParam, instance.getConsumes());
  }

  /**
   * Test of setProduces method, of class RequestMappingFilter.
   */
  @Test
  public void test_15_set_produces()
  {
    final String[] producesParam = new String[]{"produces"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setProduces(producesParam);
    assertArrayEquals(producesParam, instance.getProduces());
  }

  //CheckStyle:MethodName ON
  /**
   * Compares two arrays
   *
   * @param actual actual
   * @param expected expected
   */
  private static void assertArrayEquals(final Object[] actual, final Object[] expected)
  {
    Assert.assertEquals(actual.length, expected.length, "arrays differ in length");
    for (int i = 0; i < actual.length; i++) {
      Assert.assertEquals(actual[i], expected[i]);
    }
  }

}
