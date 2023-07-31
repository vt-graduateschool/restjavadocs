/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.request;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    final RequestMethod[] methodParam = new RequestMethod[]{RequestMethod.GET};
    final String[] paramsParam = new String[]{"paramsParam"};
    final String[] headersParam = new String[]{"headersParam"};
    final String[] consumesParam = new String[]{"consumesParam"};
    final String[] producesParam = new String[]{"producesParam"};

    final RequestMappingFilter one = new RequestMappingFilter();
    final RequestMappingFilter two = new RequestMappingFilter(methodParam);
    final RequestMappingFilter three = new RequestMappingFilter("nameParam");
    final RequestMappingFilter four = new RequestMappingFilter(pathParam);
    final RequestMappingFilter five = new RequestMappingFilter("nameParam", pathParam);
    final RequestMappingFilter six = new RequestMappingFilter(pathParam, methodParam);
    final RequestMappingFilter seven = new RequestMappingFilter(pathParam, methodParam,
            consumesParam);
    final RequestMappingFilter eight = new RequestMappingFilter(producesParam, pathParam,
            methodParam);
    final RequestMappingFilter nine = new RequestMappingFilter(pathParam, methodParam, consumesParam, producesParam);
    final RequestMappingFilter ten =
            new RequestMappingFilter(pathParam, methodParam, headersParam, consumesParam, producesParam);
    final RequestMappingFilter eleven =
            new RequestMappingFilter(pathParam, methodParam, paramsParam, headersParam, consumesParam, producesParam);
    final RequestMappingFilter twelve =
            new RequestMappingFilter(nameParam, pathParam, methodParam, paramsParam, headersParam, consumesParam,
                    producesParam);
    Assert.assertEquals(one.value(), one.path());
    Assert.assertEquals(two.value(), two.path());
    Assert.assertEquals(three.value(), three.path());
    Assert.assertEquals(four.value(), four.path());
    Assert.assertEquals(five.value(), five.path());
    Assert.assertEquals(six.value(), six.path());
    Assert.assertEquals(seven.value(), seven.path());
    Assert.assertEquals(eight.value(), eight.path());
    Assert.assertEquals(nine.value(), nine.path());
    Assert.assertEquals(ten.value(), ten.path());
    Assert.assertEquals(eleven.value(), eleven.path());
    Assert.assertEquals(twelve.value(), twelve.path());
  }

  /**
   * Test of name method, of class RequestMappingFilter.
   */
  @Test
  public void test_01_name()
  {
    final RequestMappingFilter instance = new RequestMappingFilter("name");
    final String expResult = "name";
    final String result = instance.name();
    Assert.assertEquals(expResult, result);
  }

  /**
   * Test of value method, of class RequestMappingFilter.
   */
  @Test
  public void test_02_value()
  {
    final String[] expResult = new String[]{"value"};
    final RequestMappingFilter instance = new RequestMappingFilter(expResult);
    final String[] result = instance.value();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of path method, of class RequestMappingFilter.
   */
  @Test
  public void test_03_path()
  {
    final String[] expResult = new String[]{"path"};
    final RequestMappingFilter instance = new RequestMappingFilter(expResult);
    final String[] result = instance.path();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of method method, of class RequestMappingFilter.
   */
  @Test
  public void test_04_method()
  {
    final RequestMethod[] expResult = new RequestMethod[]{RequestMethod.GET};
    final RequestMappingFilter instance = new RequestMappingFilter(expResult);
    final RequestMethod[] result = instance.method();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of methodNames method, of class RequestMappingFilter.
   */
  @Test
  public void test_05_method_names()
  {
    final String[] expResultValues = new String[]{RequestMethod.GET.name()};
    final RequestMappingFilter instance = new RequestMappingFilter(new RequestMethod[]{RequestMethod.GET});
    final String[] result = instance.methodNames();
    assertArrayEquals(expResultValues, result);
    instance.setMethod(null);
    Assert.assertEquals(instance.methodNames(), null);
  }

  /**
   * Test of params method, of class RequestMappingFilter.
   */
  @Test
  public void test_06_params()
  {
    final String[] expResult = new String[]{"params"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setParams(expResult);
    final String[] result = instance.params();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of headers method, of class RequestMappingFilter.
   */
  @Test
  public void test_07_headers()
  {
    final String[] expResult = new String[]{"headers"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setHeaders(expResult);
    final String[] result = instance.headers();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of consumes method, of class RequestMappingFilter.
   */
  @Test
  public void test_08_consumes()
  {
    final String[] expResult = new String[]{"consumes"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setConsumes(expResult);
    final String[] result = instance.consumes();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of produces method, of class RequestMappingFilter.
   */
  @Test
  public void test_09_produces()
  {
    final String[] expResult = new String[]{"produces"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setProduces(expResult);
    final String[] result = instance.produces();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of setName method, of class RequestMappingFilter.
   */
  @Test
  public void test_10_set_name()
  {
    final String nameParam = "setName";
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setName(nameParam);
    Assert.assertEquals(nameParam, instance.name());
  }

  /**
   * Test of setValue method, of class RequestMappingFilter.
   */
  @Test
  public void test_11_set_value()
  {
    final String[] valueParam = new String[]{"value"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setValue(valueParam);
    assertArrayEquals(valueParam, instance.value());
  }

  /**
   * Test of setPath method, of class RequestMappingFilter.
   */
  @Test
  public void test_12_set_path()
  {
    final String[] pathParam = new String[]{"path"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setPath(pathParam);
    assertArrayEquals(pathParam, instance.path());
  }

  /**
   * Test of setMethod method, of class RequestMappingFilter.
   */
  @Test
  public void test_13_set_method()
  {
    final String[] methodParam = new String[]{"GET"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setMethod(new RequestMethod[]{RequestMethod.GET});
    assertArrayEquals(methodParam, instance.methodNames());
  }

  /**
   * Test of setParams method, of class RequestMappingFilter.
   */
  @Test
  public void test_14_set_params()
  {
    final String[] paramsParam = new String[]{"params"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setParams(paramsParam);
    assertArrayEquals(paramsParam, instance.params());
  }

  /**
   * Test of setHeaders method, of class RequestMappingFilter.
   */
  @Test
  public void test_15_set_headers()
  {
    final String[] headersParam = new String[]{"headers"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setHeaders(headersParam);
    assertArrayEquals(headersParam, instance.headers());
  }

  /**
   * Test of setConsumes method, of class RequestMappingFilter.
   */
  @Test
  public void test_16_set_consumes()
  {
    final String[] consumesParam = new String[]{"consumes"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setConsumes(consumesParam);
    assertArrayEquals(consumesParam, instance.consumes());
  }

  /**
   * Test of setProduces method, of class RequestMappingFilter.
   */
  @Test
  public void test_17_set_produces()
  {
    final String[] producesParam = new String[]{"produces"};
    final RequestMappingFilter instance = new RequestMappingFilter();
    instance.setProduces(producesParam);
    assertArrayEquals(producesParam, instance.produces());
  }

  /**
   * Test of annotationType method, of class RequestMappingFilter.
   */
  @Test
  public void test_18_annotation_type()
  {
    final RequestMappingFilter instance = new RequestMappingFilter();
    Assert.assertEquals(RequestMapping.class, instance.annotationType());
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
