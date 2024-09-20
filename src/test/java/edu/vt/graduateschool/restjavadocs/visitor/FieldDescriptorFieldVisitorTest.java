/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import edu.vt.graduateschool.restjavadocs.beans.JacksonPOJO;
import edu.vt.graduateschool.restjavadocs.beans.LessCommonPOJO;
import edu.vt.graduateschool.restjavadocs.util.LangUtils;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.testng.Assert;
import org.testng.annotations.Test;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getFilePathFromClass;
import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getResolvingSourceRoot;

/**
 * Tests {@link FieldDescriptorFieldVisitor} methods.
 *
 * @author Graduate School
 */
public class FieldDescriptorFieldVisitorTest
{

  /**
   * Number of parameters in {@link JacksonPOJO}, minus one for the uncommented field.
   */
  private static final int COUNT_JACKSON_POJO_FIELDS =
          Arrays.asList(JacksonPOJO.class.getDeclaredFields())
                  .stream()
                  .collect(Collectors.toList()).size() - 1;

  /**
   * Number of fields annotated by {@link JsonProperty} in {@link LessCommonPOJO}.
   */
  private static final int COUNT_LESS_COMMON_POJO_JSON_PROPERTY_FIELD =
          Arrays.asList(LessCommonPOJO.class.getDeclaredFields())
                  .stream()
                  .filter(field -> field.isAnnotationPresent(JsonProperty.class))
                  .collect(Collectors.toList()).size();

  //CheckStyle:MethodName OFF
  /**
   * Test 00
   */
  @Test
  public void test_00_get_parameter_descriptors()
  {
    final List<FieldDescriptor> descriptors = new ArrayList<>();
    final FieldDescriptorFieldVisitor visitor = new FieldDescriptorFieldVisitor();
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            getFilePathFromClass(JacksonPOJO.class));
    visitor.visit(compilationUnit, null);
    descriptors.addAll(visitor.getFieldDescriptors());
    Assert.assertEquals(descriptors.size(), COUNT_JACKSON_POJO_FIELDS);
  }

  /**
   * Test 01
   */
  @Test(expectedExceptions = IllegalStateException.class)
  public void test_01_get_parameter_descriptors_no_init_by_visit()
  {
    final FieldDescriptorFieldVisitor visitor = new FieldDescriptorFieldVisitor();
    visitor.getFieldDescriptors();
    Assert.fail("Test should have failed but it did not");
  }

  /**
   * Test 02
   */
  @Test
  public void test_02_get_parameter_descriptors_with_annotation()
  {
    final List<FieldDescriptor> descriptors = new ArrayList<>();
    final FieldDescriptorFieldVisitor visitor = new FieldDescriptorFieldVisitor();
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            getFilePathFromClass(JacksonPOJO.class));
    visitor.visit(compilationUnit, Deprecated.class);
    descriptors.addAll(visitor.getFieldDescriptors());
    Assert.assertEquals(descriptors.size(), 1);
    Assert.assertEquals(descriptors.get(0).getPath(), "diffy");
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
    final List<FieldDescriptor> descriptors = new ArrayList<>();
    final FieldDescriptorFieldVisitor visitor = new FieldDescriptorFieldVisitor();
    final SourceRoot root = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    final CompilationUnit compilationUnit = root.parse("",
            getFilePathFromClass(LessCommonPOJO.class));
    visitor.visit(compilationUnit, JsonProperty.class);
    descriptors.addAll(visitor.getFieldDescriptors());
    Assert.assertEquals(descriptors.size(), COUNT_LESS_COMMON_POJO_JSON_PROPERTY_FIELD);
    Assert.assertEquals(descriptors.get(0).getPath(), "one");
    Assert.assertEquals(descriptors.get(1).getPath(), "two");
    Assert.assertEquals(descriptors.get(2).getPath(), "three");
    Assert.assertEquals(descriptors.get(3).getPath(), "shouldnt");
    Assert.assertEquals(descriptors.get(4).getPath(), "find");
    Assert.assertEquals(descriptors.get(5).getPath(), "any");
    Assert.assertEquals(descriptors.get(6).getPath(), "ofthese");
    Assert.assertEquals(descriptors.get(0).getDescription().toString(),
            "Three fields at once as marker annotation.");
    Assert.assertEquals(descriptors.get(1).getDescription().toString(),
            "Three fields at once as marker annotation.");
    Assert.assertEquals(descriptors.get(2).getDescription().toString(),
            "Three fields at once as marker annotation.");
    Assert.assertEquals(descriptors.get(3).getDescription().toString(),
            "Four fields at once with a defined name.");
    Assert.assertEquals(descriptors.get(4).getDescription().toString(),
            "Four fields at once with a defined name.");
    Assert.assertEquals(descriptors.get(5).getDescription().toString(),
            "Four fields at once with a defined name.");
    Assert.assertEquals(descriptors.get(6).getDescription().toString(),
            "Four fields at once with a defined name.");
  }
  //CheckStyle:MethodName ON
}
