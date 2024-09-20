/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.visitor;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import edu.vt.graduateschool.restjavadocs.beans.JacksonPOJO;
import edu.vt.graduateschool.restjavadocs.beans.JacksonPOJOIgnoreProperties;
import edu.vt.graduateschool.restjavadocs.beans.LessCommonPOJO;
import edu.vt.graduateschool.restjavadocs.util.JavaParserUtils;
import edu.vt.graduateschool.restjavadocs.util.LangUtils;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.testng.Assert;
import org.testng.annotations.Test;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getResolvingSourceRoot;

/**
 * Tests {@link JacksonAwareParameterDescriptorFieldVisitor} methods.
 *
 * @author Graduate School
 */
public class JacksonAwareParameterDescriptorFieldVisitorTest
{

  //CheckStyle:MethodName OFF
  /**
   * Test 00
   */
  @Test
  public void test_00_get_parameter_descriptors()
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final JacksonAwareParameterDescriptorFieldVisitor visitor = new JacksonAwareParameterDescriptorFieldVisitor();
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            JavaParserUtils.getFilePathFromClass(JacksonPOJO.class));
    visitor.visit(compilationUnit, null);
    descriptors.addAll(visitor.getParameterDescriptors());
    Assert.assertEquals(descriptors.size(), 4);
  }

  /**
   * Test 01
   */
  @Test(expectedExceptions = IllegalStateException.class)
  public void test_01_get_field_descriptors_no_init_by_visit()
  {
    final JacksonAwareParameterDescriptorFieldVisitor visitor = new JacksonAwareParameterDescriptorFieldVisitor();
    visitor.getParameterDescriptors();
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 02
   */
  @Test
  public void test_02_get_parameter_descriptors_with_annotation()
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final JacksonAwareParameterDescriptorFieldVisitor visitor = new JacksonAwareParameterDescriptorFieldVisitor();
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            JavaParserUtils.getFilePathFromClass(JacksonPOJO.class));
    visitor.visit(compilationUnit, Deprecated.class);
    descriptors.addAll(visitor.getParameterDescriptors());
    Assert.assertEquals(descriptors.size(), 1);
    Assert.assertEquals(descriptors.get(0).getName(), "differentNameWithValue");
    Assert.assertEquals(descriptors.get(0).getDescription()
            .toString()
            .contains("different name than the field using value expression explicitly"), true);
  }

  /**
   * Test 03
   */
  @Test
  public void test_03_get_parameter_descriptors_with_annotation_multiple_variables()
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final JacksonAwareParameterDescriptorFieldVisitor visitor = new JacksonAwareParameterDescriptorFieldVisitor();
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            JavaParserUtils.getFilePathFromClass(LessCommonPOJO.class));
    visitor.visit(compilationUnit, JsonProperty.class);
    descriptors.addAll(visitor.getParameterDescriptors());
    Assert.assertEquals(descriptors.size(), 3);
    Assert.assertEquals(descriptors.get(0).getName(), "one");
    Assert.assertEquals(descriptors.get(1).getName(), "two");
    Assert.assertEquals(descriptors.get(2).getName(), "three");
    Assert.assertEquals(descriptors.get(0).getDescription().toString(), "Three fields at once as marker annotation.");
    Assert.assertEquals(descriptors.get(1).getDescription().toString(), "Three fields at once as marker annotation.");
    Assert.assertEquals(descriptors.get(2).getDescription().toString(), "Three fields at once as marker annotation.");
  }

  /**
   * Test 04
   */
  @Test
  public void test_04_ignore_properties_on_type()
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final JacksonAwareParameterDescriptorFieldVisitor visitor = new JacksonAwareParameterDescriptorFieldVisitor();
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            JavaParserUtils.getFilePathFromClass(JacksonPOJOIgnoreProperties.class));
    visitor.visit(compilationUnit, null);
    descriptors.addAll(visitor.getParameterDescriptors());
    Assert.assertEquals(descriptors.size(), 3);
    Assert.assertEquals(descriptors.get(0).getName(), "nameFromGetter");
    Assert.assertEquals(descriptors.get(1).getName(), "notAnnotatedOptional");
    Assert.assertEquals(descriptors.get(2).getName(), "optionalOnGetter");
    Assert.assertEquals(descriptors.get(0).isOptional(), false);
    Assert.assertEquals(descriptors.get(1).isOptional(), true);
    Assert.assertEquals(descriptors.get(2).isOptional(), true);
    Assert.assertEquals(descriptors.get(0).getDescription().toString().contains("Getter for annotatedOnGetter"),
            true);
    Assert.assertEquals(descriptors.get(1).getDescription().toString().contains("an optional property by Java 8"),
            true);
    Assert.assertEquals(descriptors.get(2).getDescription().toString().contains("Getter for optionalOnGetter"),
            true);
  }
  //CheckStyle:MethodName ON
}
