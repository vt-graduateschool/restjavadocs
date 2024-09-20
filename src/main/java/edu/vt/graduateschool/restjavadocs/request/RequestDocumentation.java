/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import edu.vt.graduateschool.restjavadocs.util.LangUtils;
import edu.vt.graduateschool.restjavadocs.visitor.SpringWebParameterDescriptorMethodVisitor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.restdocs.request.ParameterDescriptor;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getFilePathFromClass;
import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getResolvingSourceRoot;
import static edu.vt.graduateschool.restjavadocs.util.LangUtils.JAVA_SOURCE_MAIN_PATH;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

/**
 * @author Graduate School
 */
public final class RequestDocumentation
{

  /**
   * Default constructor.
   */
  private RequestDocumentation()
  {
  }

  /**
   * Converts a given {@link RequestMappingFilter} to an equivalent {@link Map}. null values are treated as missing.
   *
   * @param filter {@link RequestMappingFilter} instance to convert
   * @return {@link Map}
   */
  public static Map<String, String[]> filterToMap(final RequestMappingFilter filter)
  {
    final Map<String, String[]> filterMap = new HashMap<>();
    if (filter == null) {
      return filterMap;
    }
    if (filter.getConsumes() != null) {
      filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_CONSUMES, filter.getConsumes());
    }
    if (filter.getHeaders() != null) {
      filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_HEADERS, filter.getHeaders());
    }
    if (filter.getMethod() != null) {
      filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_METHOD, filter.getMethod());
    }
    if (filter.getName() != null) {
      filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_NAME, new String[]{filter.getName()});
    }
    if (filter.getParams() != null) {
      filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PARAMS, filter.getParams());
    }
    if (filter.getPath() != null) {
      filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PATH, filter.getPath());
    }
    if (filter.getProduces() != null) {
      filterMap.put(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_PRODUCES, filter.getProduces());
    }
    return filterMap;
  }

  /**
   * Adds SpringBoot pagination documentation to a pageable endpoint (request parameters).
   *
   * @return {@link ParameterDescriptor}[]
   */
  public static ParameterDescriptor[] paginationRequestParameters()
  {
    return LangUtils.all(ParameterDescriptor.class,
            parameterWithName("sort")
                    .optional()
                    .description("A collection of sort directives in the format ($propertyname,)+[asc|desc]?."),
            parameterWithName("page")
                    .optional()
                    .description("The requested page number (0 indexed, defaults to 0)"),
            parameterWithName("size")
                    .optional()
                    .description("The requested page size"));
  }

  /**
   * Returns {@link ParameterDescriptor}[] from a given java class source by using method's Javadocs.
   *
   * @param restControllerClass class of the rest controller
   * @param annotationFilterValues value map to filter for matching annotation attributes
   * @return Generated descriptors from Javadocs.
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static ParameterDescriptor[] descriptors(
          final Class restControllerClass, final Map<String, String[]> annotationFilterValues)
          throws ParseProblemException
  {
    return descriptors(null, restControllerClass, annotationFilterValues);
  }

  /**
   * Returns {@link ParameterDescriptor}[] from a given java class source by using method's Javadocs.
   *
   * @param restControllerClass class of the rest controller
   * @param filter filter for matching annotation attributes
   * @return Generated descriptors from Javadocs.
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static ParameterDescriptor[] descriptors(
          final Class restControllerClass, final RequestMappingFilter filter)
          throws ParseProblemException
  {
    return RequestDocumentation.descriptors(null, restControllerClass, filter);
  }

  /**
   * Returns {@link ParameterDescriptor}[] from a given java class source by using method's Javadocs.
   *
   * @param restControllerClass class of the rest controller (will return all matching
   * {@link org.springframework.web.bind.annotation.RequestMapping} marker annotations)
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static ParameterDescriptor[] descriptors(
          final Class restControllerClass)
          throws ParseProblemException
  {
    return descriptors(null, restControllerClass, (Map<String, String[]>) null);
  }

  /**
   * Returns {@link ParameterDescriptor}[] from a given java class source by using method's Javadocs.
   *
   * @param sourceRoot base path of the sources folder (if null
   * <a href="https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html">
   * "./src/main/java/"</a> is used)
   * @param restControllerClass class of the rest controller (will return all matching
   * {@link org.springframework.web.bind.annotation.RequestMapping} marker annotations)
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static ParameterDescriptor[] descriptors(
          final String sourceRoot,
          final Class restControllerClass)
          throws ParseProblemException
  {
    return descriptors(sourceRoot, restControllerClass, (Map<String, String[]>) null);
  }

  /**
   * Returns {@link ParameterDescriptor}[] from a given java class source by using method's Javadocs.
   *
   * @param sourceRoot base path of the sources folder (if null
   * <a href="https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html">
   * "./src/main/java/"</a> is used)
   * @param restControllerClass class of the rest controller (will return all matching
   * {@link org.springframework.web.bind.annotation.RequestMapping} marker annotations)
   * @param filter filter for matching annotation attributes in JSON format (see
   * {@link #jsonToFilterMap(java.lang.String)}
   * @return Generated descriptors from Javadocs
   * @throws JSONException if the input is not of the proper structure
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static ParameterDescriptor[] descriptors(
          final String sourceRoot,
          final Class restControllerClass,
          final String filter)
          throws ParseProblemException, JSONException
  {
    return descriptors(sourceRoot, restControllerClass, jsonToFilterMap(filter));
  }

  /**
   * Returns {@link ParameterDescriptor}[] from a given java class source by using method's Javadocs.
   *
   * @param restControllerClass class of the rest controller (will return all matching
   * {@link org.springframework.web.bind.annotation.RequestMapping} marker annotations)
   * @param filter filter for matching annotation attributes in JSON format (see
   * {@link #jsonToFilterMap(java.lang.String)}
   * @return Generated descriptors from Javadocs
   * @throws JSONException if the input is not of the proper structure
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static ParameterDescriptor[] descriptors(
          final Class restControllerClass,
          final String filter)
          throws ParseProblemException, JSONException
  {
    return descriptors(null, restControllerClass, jsonToFilterMap(filter));
  }

  /**
   * Returns {@link ParameterDescriptor}[] from a given java class source by using method's Javadocs.
   *
   * @param sourceRoot sourceRoot
   * @param restControllerClass class of the rest controller
   * @param filter filter for matching annotation attributes
   * @return Generated descriptors from Javadocs.
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static ParameterDescriptor[] descriptors(
          final String sourceRoot,
          final Class restControllerClass, final RequestMappingFilter filter)
          throws ParseProblemException
  {
    return descriptors(sourceRoot, restControllerClass, filterToMap(filter));
  }

  /**
   * Returns {@link ParameterDescriptor}[] from a given java class source by using method's Javadocs.
   *
   * @param sourceRoot base path of the sources folder (if null
   * <a href="https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html">
   * "./src/main/java/"</a> is used)
   * @param controllerClass class of the rest controller mapping the paths
   * @param annotationFilterValues value map to filter for matching annotation attributes
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  public static ParameterDescriptor[] descriptors(final String sourceRoot,
          final Class controllerClass, final Map<String, String[]> annotationFilterValues)
          throws ParseProblemException
  {
    return descriptors(sourceRoot, getFilePathFromClass(controllerClass),
            annotationFilterValues);
  }

  /**
   * Returns {@link ParameterDescriptor}[] from a given java class source by using method's Javadocs.
   *
   * @param sourceRoot base path of the sources folder
   * @param sourceFile path to the .java file
   * @param annotationFilterValues value map to filter for matching annotation attributes
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  private static ParameterDescriptor[] descriptors(final String sourceRoot,
          final String sourceFile, final Map<String, String[]> annotationFilterValues)
          throws ParseProblemException
  {
    final String sourcesBasePath = sourceRoot == null ? JAVA_SOURCE_MAIN_PATH : sourceRoot;
    return descriptors(getResolvingSourceRoot(sourcesBasePath), sourceFile, annotationFilterValues);
  }

  /**
   * Returns {@link ParameterDescriptor}[] from a given java class source by using method's Javadocs.
   *
   * @param sourceRoot base path of the sources folder
   * @param sourceFile path to the .java file
   * @param annotationFilterValues value map to filter for matching annotation attributes
   * @return Generated descriptors from Javadocs
   * @throws com.github.javaparser.ParseProblemException {@link ParseProblemException} is thrown if the source could not
   * be parsed
   */
  private static ParameterDescriptor[] descriptors(final SourceRoot sourceRoot,
          final String sourceFile, final Map<String, String[]> annotationFilterValues)
          throws ParseProblemException
  {
    final List<ParameterDescriptor> descriptors = new ArrayList<>();
    final SpringWebParameterDescriptorMethodVisitor visitor =
            new SpringWebParameterDescriptorMethodVisitor(annotationFilterValues == null ?
                    new HashMap<>() : annotationFilterValues);
    final CompilationUnit compilationUnit = sourceRoot.parse("", sourceFile);
    visitor.visit(compilationUnit, null);
    descriptors.addAll(visitor.getParameterDescriptors());
    return descriptors.toArray(ParameterDescriptor[]::new);
  }

  /**
   * Converts a JSON representation of the {@link RequestMappingFilter} provided as a {@link String} into an instance of
   * {@link HashMap} which may be used to filter request mapping queries.<br>
   * <br>
   * JSON structure must conform to the returned Map's structure like so and be nested no further:<br>
   * <ul>
   * <li>Key = (string)
   * <li>Value = (string_array|string)
   * </ul>
   *
   * @param input JSON string
   * @return {@link Map} of type <b>Map&lt;String, String[]&gt;</b>
   * @throws JSONException if the input is not proper JSON
   */
  private static Map<String, String[]> jsonToFilterMap(final String input) throws JSONException
  {
    if (input == null) {
      return new HashMap<>();
    }
    final Map<String, String[]> map = new HashMap<>();
    final JSONObject json = new JSONObject(input);
    final Iterator<?> keys = json.keys();
    while (keys.hasNext()) {
      final String key = (String) keys.next();
      final JSONArray arr = json.optJSONArray(key);
      if (arr != null) {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
          list.add(arr.getString(i));
        }
        map.put(key, list.toArray(String[]::new));
      } else {
        map.put(key, new String[]{json.getString(key)});
      }
    }
    return map;
  }

}
