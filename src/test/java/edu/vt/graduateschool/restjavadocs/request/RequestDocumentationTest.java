/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.request;

import java.util.HashMap;
import java.util.Map;
import edu.vt.graduateschool.restjavadocs.controller.NoParametersController;
import edu.vt.graduateschool.restjavadocs.controller.SpringRestController;
import edu.vt.graduateschool.restjavadocs.controller.VerySimpleRestController;
import edu.vt.graduateschool.restjavadocs.util.LangUtils;
import org.json.JSONException;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests {@link RequestDocumentation} methods.
 *
 * @author Graduate School
 */
public class RequestDocumentationTest
{

  //CheckStyle:MethodName OFF
  /**
   * Test 00
   */
  @Test
  public void test_00_controller_with_single_param()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    VerySimpleRestController.class);
    Assert.assertEquals(descriptors.length, 1);
  }

  /**
   * Test 01
   */
  @Test
  public void test_01_controller_with_no_params()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    NoParametersController.class);
    Assert.assertEquals(descriptors.length, 0);
  }

  /**
   * Test 02
   */
  @Test
  public void test_02_count_of_controller_all_params()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    SpringRestController.class);
    Assert.assertEquals(descriptors.length > 0, true);
  }

  /**
   * Test 03
   */
  @Test
  public void test_03_count_of_string_endpoint_no_params()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    SpringRestController.class, "{method:'RequestMethod.GET',path:'/stringEndpointNoParams'}");
    Assert.assertEquals(descriptors.length, 0);
  }

  /**
   * Test 04
   */
  @Test
  public void test_04_string_endpoint_non_required_params_by_json_filter()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    SpringRestController.class, "{produces:'text/plain',path:'/stringEndpointNonRequiredParams'}");
    Assert.assertEquals(descriptors.length, 2);
    Assert.assertEquals(descriptors[0].isOptional(), true);
    Assert.assertEquals(descriptors[1].isOptional(), true);
  }

  /**
   * Test 05
   */
  @Test
  public void test_05_string_endpoint_non_required_params_by_request_mapping_filter()
  {
    final RequestMappingFilter filter = new RequestMappingFilter();
    filter.setProduces(new String[]{"text/plain"});
    filter.setMethod(new String[]{"RequestMethod.GET"});
    filter.setPath(new String[]{"/stringEndpointNonRequiredParams"});
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    SpringRestController.class, filter);
    Assert.assertEquals(descriptors.length, 2);
    Assert.assertEquals(descriptors[0].isOptional(), true);
    Assert.assertEquals(descriptors[1].isOptional(), true);
  }

  /**
   * Test 06
   */
  @Test
  public void test_06_string_endpoint_non_required_params_by_map()
  {
    final Map<String, String[]> filterMap = new HashMap<>();
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_METHOD, new String[]{"RequestMethod.GET"});
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PATH,
            new String[]{"/stringEndpointNonRequiredParams"});
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PRODUCES, new String[]{"text/plain"});
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    SpringRestController.class, filterMap);
    Assert.assertEquals(descriptors.length, 2);
    Assert.assertEquals(descriptors[0].isOptional(), true);
    Assert.assertEquals(descriptors[1].isOptional(), true);
  }

  /**
   * Test 07
   */
  @Test
  public void test_07_string_endpoint_non_required_params_by_map_empty_method()
  {
    final Map<String, String[]> filterMap = new HashMap<>();
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_METHOD, new String[]{});
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PATH,
            new String[]{"/stringEndpointNonRequiredParams"});
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PRODUCES, new String[]{"text/plain"});
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    SpringRestController.class, filterMap);
    Assert.assertEquals(descriptors.length, 2);
    Assert.assertEquals(descriptors[0].isOptional(), true);
    Assert.assertEquals(descriptors[1].isOptional(), true);
  }

  /**
   * Test 08
   */
  @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = ".*JSR-308.*")
  public void test_08_string_endpoint_non_required_params_by_map_null_method()
  {
    final Map<String, String[]> filterMap = new HashMap<>();
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_METHOD, new String[]{null});
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PATH,
            new String[]{"/stringEndpointNonRequiredParams"});
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PRODUCES, new String[]{"text/plain"});
    RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
            SpringRestController.class, filterMap);
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 09
   */
  @Test
  public void test_09_string_endpoint_non_required_params_by_map_unknown_method()
  {
    final Map<String, String[]> filterMap = new HashMap<>();
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_METHOD, new String[]{"JOE"});
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PATH,
            new String[]{"/stringEndpointNonRequiredParams"});
    filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PRODUCES, new String[]{"text/plain"});
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    SpringRestController.class, filterMap);
    Assert.assertEquals(descriptors.length, 0);
  }

  /**
   * Test 10
   */
  @Test
  public void test_10_string_endpoint_with_params_by_request_filter_using_all()
  {
    final RequestMappingFilter filter = new RequestMappingFilter(
            "all",
            new String[]{"/stringEndpointWithParams"},
            new String[]{"RequestMethod.GET"}, new String[]{"match=one"},
            new String[]{"content-type=text/*"},
            new String[]{"application/*"},
            new String[]{"text/plain"});
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    SpringRestController.class, filter);
    Assert.assertEquals(descriptors.length, 3);
    Assert.assertEquals(descriptors[0].getName(), "requiredOne");
    Assert.assertEquals(descriptors[1].getName(), "nonRequiredTwo");
    Assert.assertEquals(descriptors[2].getName(), "nonRequiredThree");
    Assert.assertEquals(descriptors[0].getDescription().toString().trim(), "description for requiredOne");
    Assert.assertEquals(descriptors[1].getDescription().toString().trim(), "description for nonRequiredTwo");
    Assert.assertEquals(descriptors[2].getDescription().toString().trim(), "description for nonRequiredThree");
    Assert.assertEquals(descriptors[0].isOptional(), false);
    Assert.assertEquals(descriptors[1].isOptional(), true);
    Assert.assertEquals(descriptors[2].isOptional(), true);
  }

  /**
   * Test 10.1
   */
  @Test
  public void test_10_1_string_endpoint_with_params_by_request_filter_using_all_with_field_expressions()
  {
    final String path = "/stringEndpointWithFieldExpressionParams" +
            "java.lang.Long.MAX_VALUE" + '/' + "RESPONSE" + '/' + "true" + "1.1" + "3";
    final RequestMappingFilter filter = new RequestMappingFilter(
            new String[]{path}, new String[]{"RequestMethod.GET"});
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    SpringRestController.class, filter);
    Assert.assertEquals(descriptors.length, 1);
    Assert.assertEquals(descriptors[0].getName(), "stringEndpointWithFieldExpressionParam");
    Assert.assertEquals(descriptors[0].getDescription().toString().trim(),
            "description for stringEndpointWithFieldExpressionParam");
    Assert.assertEquals(descriptors[0].isOptional(), false);
  }

  /**
   * Test 11
   */
  @Test
  public void test_11_class_with_no_mappings_with_no_filter()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.class);
    Assert.assertEquals(descriptors.length, 0);
  }

  /**
   * Test 12
   */
  @Test
  public void test_12_class_with_no_mappings_with_request_mapping_filter()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.class, new RequestMappingFilter());
    Assert.assertEquals(descriptors.length, 0);
  }

  /**
   * Test 13
   */
  @Test
  public void test_13_class_with_no_mappings_with_json()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.class, "{}");
    Assert.assertEquals(descriptors.length, 0);
  }

  /**
   * Test 14
   */
  @Test
  public void test_14_class_with_no_mappings_with_empty_map()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.class, new HashMap<>());
    Assert.assertEquals(descriptors.length, 0);
  }

  /**
   * Test 15
   */
  @Test
  public void test_15_count_of_pagination_request_parameters()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.paginationRequestParameters();
    Assert.assertEquals(descriptors.length, 3);
  }

  /**
   * Test 16
   */
  @Test
  public void test_16_class_with_no_mappings_with_null_json_filter()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.class, (String) null);
    Assert.assertEquals(descriptors.length, 0);
  }

  /**
   * Test 17
   */
  @Test
  public void test_17_class_with_no_mappings_with_complete_json_filter()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.class,
                    "{method: ['RequestMethod.GET', 'RequestMethod.PUT'], path:'/stringEndpointNonRequiredParams'}");
    Assert.assertEquals(descriptors.length, 0);
  }

  /**
   * Test 18
   */
  @Test
  public void test_18_string_endpoint_non_required_params_by_strange_json_filter()
  {
    final ParameterDescriptor[] descriptors =
            RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
                    SpringRestController.class,
                    "{method: ['RequestMethod.GET'], path:'/stringEndpointNonRequiredParams'}");
    Assert.assertEquals(descriptors.length, 2);
    Assert.assertEquals(descriptors[0].isOptional(), true);
    Assert.assertEquals(descriptors[1].isOptional(), true);
  }

  /**
   * Test 19
   */
  @Test(expectedExceptions = JSONException.class)
  public void test_19_string_endpoint_non_required_params_by_bad_json_filter()
  {
    RequestDocumentation.descriptors(LangUtils.JAVA_SOURCE_TEST_PATH,
            SpringRestController.class, "{invalidRequest");
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 20
   */
  @Test
  public void test_20_filter_to_map()
  {
    final Map<String, String[]> filterMapFromNull = RequestDocumentation.filterToMap(null);
    final Map<String, String[]> filterMap =
            RequestDocumentation.filterToMap(new RequestMappingFilter(new String[]{"/path"}));
    Assert.assertEquals(filterMap.keySet().size(), 1);
    Assert.assertEquals(filterMapFromNull.isEmpty(), true);
  }
  //CheckStyle:MethodName ON
}
