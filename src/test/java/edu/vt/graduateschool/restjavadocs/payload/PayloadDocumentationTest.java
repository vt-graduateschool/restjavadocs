/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.payload;

import java.util.Arrays;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.utils.SourceRoot;
import edu.vt.graduateschool.restjavadocs.beans.JacksonPOJO;
import edu.vt.graduateschool.restjavadocs.util.LangUtils;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.testng.Assert;
import org.testng.annotations.Test;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getResolvingSourceRoot;

/**
 * Tests {@link PayloadDocumentation} methods.
 *
 * @author Graduate School
 */
public class PayloadDocumentationTest
{

  /**
   * Count of pagination response fields.
   */
  private static final int COUNT_PAGINATION_RESPONSE_FIELDS = PayloadDocumentation.paginationFields().length;

  /**
   * Count of pagination response fields minus .content property.
   */
  private static final int COUNT_PAGINATION_RESPONSE_FIELDS_NO_CONTENT =
          PayloadDocumentation.paginationFields().length - 1;

  /**
   * Number of fields not annotated by {@link JsonIgnore} in {@link JacksonPOJO} minus one for the field without
   * comments.
   */
  private static final int COUNT_JACKSON_POJO_JSON_PROPERTY_FIELD =
          Arrays.asList(JacksonPOJO.class.getDeclaredFields())
                  .stream()
                  .filter(field -> !field.isAnnotationPresent(JsonIgnore.class))
                  .collect(Collectors.toList()).size() - 1;

  //CheckStyle:MethodName OFF
  /**
   * Test 00
   */
  @Test
  public void test_00_count_of_pagination_fields()
  {
    final FieldDescriptor[] descriptors = PayloadDocumentation.paginationFields();
    Assert.assertEquals(descriptors.length, COUNT_PAGINATION_RESPONSE_FIELDS);
  }

  /**
   * Test 01
   */
  @Test
  public void test_01_fields_from_main()
  {
    final FieldDescriptor[] descriptors = PayloadDocumentation.fields(LangUtils.class);
    Assert.assertEquals(descriptors.length, 0);
  }

  /**
   * Test 02
   */
  @Test(expectedExceptions = ParseProblemException.class)
  public void test_02_nonexistent_source_file()
  {
    PayloadDocumentation.fields(JacksonPOJO.class);
  }

  /**
   * Test 03
   */
  @Test
  public void test_03_count_of_pojo_fields()
  {
    final FieldDescriptor[] descriptors =
            PayloadDocumentation.fields(LangUtils.JAVA_SOURCE_TEST_PATH,
                    JacksonPOJO.class);
    Assert.assertEquals(descriptors.length, COUNT_JACKSON_POJO_JSON_PROPERTY_FIELD);
  }

  /**
   * Test 04
   */
  @Test
  public void test_04_count_of_pagination_fields_with_pojo()
  {
    final FieldDescriptor[] descriptors =
            PayloadDocumentation.paginatedFields(LangUtils.JAVA_SOURCE_TEST_PATH,
                    JacksonPOJO.class);
    Assert.assertEquals(descriptors.length, COUNT_PAGINATION_RESPONSE_FIELDS_NO_CONTENT +
            COUNT_JACKSON_POJO_JSON_PROPERTY_FIELD);
  }

  /**
   * Test 05
   */
  @Test(expectedExceptions = ParseProblemException.class)
  public void test_05_nonexistent_source_file_with_pagination()
  {
    PayloadDocumentation.paginatedFields(JacksonPOJO.class);
  }

  /**
   * Test 06
   */
  @Test
  public void test_06_empty_source_root()
  {
    final FieldDescriptor[] descriptors = PayloadDocumentation.paginatedFields(LangUtils.class);
    Assert.assertEquals(descriptors.length, COUNT_PAGINATION_RESPONSE_FIELDS_NO_CONTENT);
  }

  /**
   * Test 07
   */
  @Test
  public void test_07_pojo_fields_descriptions()
  {
    final FieldDescriptor[] descriptors =
            PayloadDocumentation.fields(LangUtils.JAVA_SOURCE_TEST_PATH,
                    JacksonPOJO.class);
    Assert.assertEquals(descriptors.length, COUNT_JACKSON_POJO_JSON_PROPERTY_FIELD);
    Assert.assertEquals(descriptors[0].getDescription().toString(), "id of the entry");
    Assert.assertEquals(descriptors[1].getDescription().toString(), "different name than the field");
    Assert.assertEquals(descriptors[2].getDescription().toString()
            .contains("not annotated but included"), true);
    Assert.assertEquals(descriptors[3].getDescription().toString()
            .contains("different name than the field using value expression explicitly."), true);
  }

  /**
   * Test 08
   */
  @Test
  public void test_08_pojo_fields_field_names()
  {
    final FieldDescriptor[] descriptors =
            PayloadDocumentation.fields(LangUtils.JAVA_SOURCE_TEST_PATH,
                    JacksonPOJO.class);
    Assert.assertEquals(descriptors.length, COUNT_JACKSON_POJO_JSON_PROPERTY_FIELD);
    Assert.assertEquals(descriptors[0].getPath(), "id");
    Assert.assertEquals(descriptors[1].getPath(), "differentName");
    Assert.assertEquals(descriptors[2].getPath(), "notAnnotated");
    Assert.assertEquals(descriptors[3].getPath(), "differentNameWithValue");
  }

  /**
   * Test 09
   */
  @Test
  public void test_09_paginated_fields_from_main()
  {
    final FieldDescriptor[] descriptors = PayloadDocumentation.paginatedFields(LangUtils.class);
    Assert.assertEquals(descriptors.length, COUNT_PAGINATION_RESPONSE_FIELDS_NO_CONTENT);
  }

  /**
   * Test 10
   */
  @Test(expectedExceptions = ParseProblemException.class)
  public void test_10_nonexistent_source_file()
  {
    final SourceRoot sourceRoot = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    sourceRoot.getParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_1_0);
    PayloadDocumentation.fields(sourceRoot, JacksonPOJO.class, null);
  }

  /**
   * Test 11
   */
  @Test
  public void test_11_custom_source_root()
  {
    final SourceRoot sourceRoot = getResolvingSourceRoot(LangUtils.JAVA_SOURCE_TEST_PATH);
    sourceRoot.getParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_18);
    final FieldDescriptor[] descriptors = PayloadDocumentation.fields(sourceRoot, JacksonPOJO.class, null);
    Assert.assertEquals(descriptors.length, COUNT_JACKSON_POJO_JSON_PROPERTY_FIELD);
    Assert.assertEquals(descriptors[0].getPath(), "id");
    Assert.assertEquals(descriptors[1].getPath(), "differentName");
    Assert.assertEquals(descriptors[2].getPath(), "notAnnotated");
    Assert.assertEquals(descriptors[3].getPath(), "differentNameWithValue");
  }
  //CheckStyle:MethodName ON
}
