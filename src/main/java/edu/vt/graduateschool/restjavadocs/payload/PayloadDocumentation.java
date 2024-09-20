/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.payload;

import java.lang.annotation.Annotation;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.utils.SourceRoot;
import edu.vt.graduateschool.restjavadocs.util.LangUtils;
import edu.vt.graduateschool.restjavadocs.visitor.JacksonAwareFieldDescriptorFieldVisitor;
import org.springframework.restdocs.payload.FieldDescriptor;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getFilePathFromClass;
import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getResolvingSourceRoot;
import static edu.vt.graduateschool.restjavadocs.util.LangUtils.JAVA_SOURCE_MAIN_PATH;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

/**
 * @author Graduate School
 */
public final class PayloadDocumentation
{

  /**
   * Default constructor.
   */
  private PayloadDocumentation()
  {
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs with basic Spring
   * pagination fields appended.
   *
   * @param beanClass beanClass
   * @return {@link FieldDescriptor}[]
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static FieldDescriptor[] paginatedFields(final Class beanClass)
          throws ParseProblemException
  {
    return paginatedFields((String) null, beanClass, null);
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs with basic Spring
   * pagination fields appended.
   *
   * @param sourceRoot base path of the sources folder (if null
   * <a href="https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html">
   * "./src/main/java/"</a> is used)
   * @param beanClass beanClass
   * @return {@link FieldDescriptor}[]
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static FieldDescriptor[] paginatedFields(final String sourceRoot,
          final Class beanClass)
          throws ParseProblemException
  {
    return paginatedFields(sourceRoot, beanClass, null);
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs with basic Spring
   * pagination fields appended.
   *
   * @param beanClass beanClass
   * @param annotated specified annotation on fields
   * @return {@link FieldDescriptor}[]
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static FieldDescriptor[] paginatedFields(final Class beanClass,
          final Class<? extends Annotation> annotated)
          throws ParseProblemException
  {
    return paginatedFields((String) null, beanClass, annotated);
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs with basic Spring
   * pagination fields appended.
   *
   * @param sourceRoot base path of the sources folder (if null
   * <a href="https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html">
   * "./src/main/java/"</a> is used)
   * @param beanClass beanClass
   * @param annotated specified annotation on fields
   * @see #fields(java.lang.String, java.lang.Class, java.lang.Class)
   * @return {@link FieldDescriptor}[]
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static FieldDescriptor[] paginatedFields(final String sourceRoot,
          final Class beanClass, final Class<? extends Annotation> annotated)
          throws ParseProblemException
  {
    final String sourcesBasePath = sourceRoot == null ? JAVA_SOURCE_MAIN_PATH : sourceRoot;
    return paginatedFields(getResolvingSourceRoot(sourcesBasePath), beanClass, annotated);
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs with basic Spring
   * pagination fields appended.
   *
   * @param sourceRoot base path of the sources folder (if null
   * <a href="https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html">
   * "./src/main/java/"</a> is used)
   * @param beanClass beanClass
   * @param annotated specified annotation on fields
   * @see #fields(java.lang.String, java.lang.Class, java.lang.Class)
   * @return {@link FieldDescriptor}[]
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static FieldDescriptor[] paginatedFields(final SourceRoot sourceRoot,
          final Class beanClass, final Class<? extends Annotation> annotated)
          throws ParseProblemException
  {
    final FieldDescriptor[] pageDescriptors = paginationFields();
    final FieldDescriptor[] beanDescriptors = fields(sourceRoot, beanClass, annotated);
    final FieldDescriptor[] finalResult = new FieldDescriptor[(pageDescriptors.length - 1) +
            beanDescriptors.length];
    for (int i = 0; i < beanDescriptors.length; i++) {
      beanDescriptors[i] = fieldWithPath("content[]." + beanDescriptors[i].getPath()).description(
              beanDescriptors[i].getDescription());
    }
    System.arraycopy(beanDescriptors, 0, finalResult, 0, beanDescriptors.length);
    System.arraycopy(pageDescriptors, 1, finalResult, beanDescriptors.length, pageDescriptors.length - 1);
    return finalResult;
  }

  /**
   * Returns a basic description for all Spring pagination response fields as {@link FieldDescriptor}[].
   *
   * @return {@link FieldDescriptor}[]
   */
  public static FieldDescriptor[] paginationFields()
  {
    return LangUtils.all(FieldDescriptor.class, subsectionWithPath("content")
            .description("Content array consisting of elements requested for this page"),
            subsectionWithPath("pageable")
                    .description("Pagination information that was used to request this page"),
            subsectionWithPath("sort")
                    .description("Sorting information on this page"),
            subsectionWithPath("number")
                    .description("Non-negative number assigned to this page"),
            subsectionWithPath("size")
                    .description("Non-negative number indicating the size of the page"),
            subsectionWithPath("numberOfElements")
                    .description("Number of elements returned with this page"),
            subsectionWithPath("totalElements")
                    .description("Total number of elements available in the collection"),
            subsectionWithPath("totalPages")
                    .description("Total number of pages available in the collection"),
            subsectionWithPath("first")
                    .description("True if this is the first page in the collection"),
            subsectionWithPath("last")
                    .description("True if this is the last page in the collection"));
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs.
   *
   * @param beanClass The class of the bean that contains JacksonAnnotation fields
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static FieldDescriptor[] fields(final Class beanClass)
          throws ParseProblemException
  {
    return fields((String) null, beanClass, null);
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs.
   *
   * @param sourceRoot base path of the sources folder (if null
   * <a href="https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html">
   * "./src/main/java/"</a> is used)
   * @param beanClass The class of the bean that contains JacksonAnnotation fields
   * @see edu.vt.graduateschool.restjavadocs.util.LangUtils
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static FieldDescriptor[] fields(final String sourceRoot, final Class beanClass)
          throws ParseProblemException
  {
    return fields(sourceRoot, beanClass, null);
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs.
   *
   * @param beanClass The class of the bean that contains JacksonAnnotation fields
   * @param annotated specified annotation on fields
   * @see edu.vt.graduateschool.restjavadocs.util.LangUtils
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static FieldDescriptor[] fields(final Class beanClass, final Class<? extends Annotation> annotated)
          throws ParseProblemException
  {
    return fields((String) null, beanClass, annotated);
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs.
   *
   * @param sourceRoot source root
   * @param beanClass The class of the bean that contains JacksonAnnotation fields
   * @param annotated specified annotation on fields
   * @see edu.vt.graduateschool.restjavadocs.util.LangUtils
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static FieldDescriptor[] fields(final SourceRoot sourceRoot, final Class beanClass,
          final Class<? extends Annotation> annotated)
          throws ParseProblemException
  {
    return fields(sourceRoot, getFilePathFromClass(beanClass), annotated);
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs.
   *
   * @param sourceRoot base path of the sources folder (if null
   * <a href="https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html">
   * "./src/main/java/"</a> is used)
   * @param beanClass The class of the bean that contains JacksonAnnotation fields
   * @param annotated specified annotation on fields
   * @see edu.vt.graduateschool.restjavadocs.util.LangUtils
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static FieldDescriptor[] fields(final String sourceRoot, final Class beanClass,
          final Class<? extends Annotation> annotated)
          throws ParseProblemException
  {
    final String sourcesBasePath = sourceRoot == null ? JAVA_SOURCE_MAIN_PATH : sourceRoot;
    return fields(sourcesBasePath, getFilePathFromClass(beanClass), annotated);
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs.
   *
   * @param sourceRoot base path of the sources folder
   * @param sourceFile path to the .java file
   * @param annotated specified annotation on fields
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  private static FieldDescriptor[] fields(final String sourceRoot, final String sourceFile,
          final Class<? extends Annotation> annotated)
          throws ParseProblemException
  {
    return fields(getResolvingSourceRoot(sourceRoot), sourceFile, annotated);
  }

  /**
   * Returns {@link FieldDescriptor}[] from a given java class source by using field's Javadocs.
   *
   * @param sourceRoot source root
   * @param sourceFile path to the .java file
   * @param annotated specified annotation on fields
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  private static FieldDescriptor[] fields(final SourceRoot sourceRoot, final String sourceFile,
          final Class<? extends Annotation> annotated)
          throws ParseProblemException
  {
    final JacksonAwareFieldDescriptorFieldVisitor visitor = new JacksonAwareFieldDescriptorFieldVisitor();
    visitor.visit(sourceRoot.parse("", sourceFile), annotated);
    return visitor.getFieldDescriptors().toArray(FieldDescriptor[]::new);
  }

}
