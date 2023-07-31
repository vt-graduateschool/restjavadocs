/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.visitor;

import java.util.ArrayList;
import java.util.List;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import edu.vt.graduateschool.restjavadocs.controller.SpringRestController;
import edu.vt.graduateschool.restjavadocs.controller.SpringRestControllerMalformedComments;
import edu.vt.graduateschool.restjavadocs.request.RequestDocumentation;
import edu.vt.graduateschool.restjavadocs.request.RequestMappingFilter;
import edu.vt.graduateschool.restjavadocs.util.LangUtils;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.web.bind.annotation.InitBinder;
import org.testng.Assert;
import org.testng.annotations.Test;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getResolvingSourceRoot;

/**
 * Tests {@link SpringWebParameterDescriptorMethodVisitor} methods.
 *
 * @author Graduate School
 */
public class SpringWebParameterDescriptorMethodVisitorTest
{

  //CheckStyle:MethodName OFF
  /**
   * Test 00
   */
  @Test
  public void test_00_visit_with_annotation_type()
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final RequestMappingFilter filter = new RequestMappingFilter();
    filter.setConsumes(new String[]{"patchyness"});
    final SpringWebParameterDescriptorMethodVisitor visitor = new SpringWebParameterDescriptorMethodVisitor(
            RequestDocumentation.filterToMap(filter));
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            LangUtils.getFilePathFromClass(SpringRestController.class));
    visitor.visit(compilationUnit, InitBinder.class);
    descriptors.addAll(visitor.getParameterDescriptors());
    Assert.assertEquals(descriptors.size(), 2);
  }

  /**
   * Test 01
   */
  @Test
  public void test_01_get_parameter_descriptors()
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final SpringWebParameterDescriptorMethodVisitor visitor = new SpringWebParameterDescriptorMethodVisitor(
            RequestDocumentation.filterToMap(
                    new RequestMappingFilter(new String[]{"/stringEndpointRequiredParams"}))
    );
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            LangUtils.getFilePathFromClass(SpringRestController.class));
    visitor.visit(compilationUnit, null);
    descriptors.addAll(visitor.getParameterDescriptors());
    Assert.assertEquals(descriptors.size(), 2);
  }

  /**
   * Test 02
   */
  @Test
  public void test_02_visit_method_has_no_comments()
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final SpringWebParameterDescriptorMethodVisitor visitor = new SpringWebParameterDescriptorMethodVisitor(
            RequestDocumentation.filterToMap(
                    new RequestMappingFilter("nocomments"))
    );
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            LangUtils.getFilePathFromClass(SpringRestController.class));
    visitor.visit(compilationUnit, null);
    descriptors.addAll(visitor.getParameterDescriptors());
    Assert.assertEquals(descriptors.size(), 0);
  }

  /**
   * Test 03
   */
  @Test(expectedExceptions = IllegalStateException.class)
  public void test_03_get_parameter_descriptors_no_init_by_visit()
  {
    final SpringWebParameterDescriptorMethodVisitor visitor = new SpringWebParameterDescriptorMethodVisitor();
    visitor.getParameterDescriptors();
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 04
   */
  @Test
  public void test_04_visit_request_param_has_optional_type_only()
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final SpringWebParameterDescriptorMethodVisitor visitor = new SpringWebParameterDescriptorMethodVisitor(
            RequestDocumentation.filterToMap(
                    new RequestMappingFilter("strangeoptional"))
    );
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            LangUtils.getFilePathFromClass(SpringRestController.class));
    visitor.visit(compilationUnit, null);
    descriptors.addAll(visitor.getParameterDescriptors());
    Assert.assertEquals(descriptors.size(), 1);
  }

  /**
   * Test 05
   */
  @Test
  public void test_05_visit_request_param_has_no_matching_param_tag()
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final SpringWebParameterDescriptorMethodVisitor visitor = new SpringWebParameterDescriptorMethodVisitor(
            RequestDocumentation.filterToMap(
                    new RequestMappingFilter("noparamcomments"))
    );
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            LangUtils.getFilePathFromClass(SpringRestController.class));
    visitor.visit(compilationUnit, null);
    descriptors.addAll(visitor.getParameterDescriptors());
    Assert.assertEquals(descriptors.size(), 0);
  }

  /**
   * Test 06
   */
  @Test
  public void test_06_visit_request_param_has_param_tag_but_no_description()
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final SpringWebParameterDescriptorMethodVisitor visitor = new SpringWebParameterDescriptorMethodVisitor(
            RequestDocumentation.filterToMap(
                    new RequestMappingFilter("noparamcommentsdescribed"))
    );
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            LangUtils.getFilePathFromClass(SpringRestController.class));
    visitor.visit(compilationUnit, null);
    descriptors.addAll(visitor.getParameterDescriptors());
    Assert.assertEquals(descriptors.size(), 0);
  }

  /**
   * Test 07
   */
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_07_visit_request_param_has_malformed_comments()
  {
    final SpringWebParameterDescriptorMethodVisitor visitor = new SpringWebParameterDescriptorMethodVisitor(
            RequestDocumentation.filterToMap(
                    new RequestMappingFilter("badcomments"))
    );
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            LangUtils.getFilePathFromClass(SpringRestControllerMalformedComments.class));
    visitor.visit(compilationUnit, null);
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 08
   */
  @Test
  public void test_08_visit_supplementary_param_mapping()
  {
    final SpringWebParameterDescriptorMethodVisitor visitor = new SpringWebParameterDescriptorMethodVisitor(
            RequestDocumentation.filterToMap(
                    new RequestMappingFilter(new String[]{"/patchMapping"}, null, new String[]{"patchyness"}))
    );
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            LangUtils.getFilePathFromClass(SpringRestController.class));
    visitor.visit(compilationUnit, null);
    Assert.assertEquals(visitor.getParameterDescriptors().size(), 2);
  }

  /**
   * Test 09
   */
  @Test
  public void test_09_get_set_filtermap()
  {
    final SpringWebParameterDescriptorMethodVisitor visitor = new SpringWebParameterDescriptorMethodVisitor();
    visitor.setFilterMap(RequestDocumentation.filterToMap(new RequestMappingFilter("name")));
    Assert.assertEquals(visitor.getFilterMap().get(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_NAME)[0],
            "name");
  }
  //CheckStyle:MethodName ON
}
