/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Graduate School
 */
public final class LangUtils
{

  /**
   * The .java file extension
   */
  public static final String JAVA_FILE_EXTENSION = ".java";

  /**
   * The java source main path as per
   * <a href="https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html">
   * Standard Directory Layout</a>
   */
  public static final String JAVA_SOURCE_MAIN_PATH = "src/main/java";

  /**
   * The java source test path as per
   * <a href="https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html">
   * Standard Directory Layout</a>
   */
  public static final String JAVA_SOURCE_TEST_PATH = "src/test/java";

  /**
   * Annotation expression name which maps to {@link java.lang.annotation.Annotation}'s <b>value</b> expression
   */
  public static final String ANNOTATION_EXPRESSION_VALUE = "value";

  /**
   * Private constructor.
   */
  private LangUtils()
  {
  }

  /**
   * Alternate chaining method for descriptors
   *
   * @param <T> return type
   * @param clazz return type to collect into
   * @param descriptors descriptors
   * @return array of T
   */
  public static <T> T[] all(final Class<T> clazz, final Object... descriptors)
  {
    return spaghettify(10, clazz, descriptors);
  }

  /**
   * Returns a flattened array from inputs which may be an array or an object of same type. If arrays contain other
   * arrays, the function will also add those elements to the final one dimensional array.
   *
   * @param <T> class type
   * @param clazz class type of the final array
   * @param inputs inputs to consider
   * @return {@link T}[] one dimensional array containing all nested elements of provided type
   */
  public static <T> T[] spaghettify(final Class<T> clazz, final Object... inputs)
  {
    return spaghettify(0, clazz, inputs);
  }

  /**
   * Returns a flattened array from inputs which may be an array or an object of same type. If arrays contain other
   * arrays, the function will also add those elements to the final one dimensional array.
   *
   * @param <T> class type
   * @param level the depth to go down into collections/arrays (0 means no nested elements will be checked)
   * @param clazz class type of the final array
   * @param inputs inputs to consider
   * @return {@link T}[] one dimensional array containing all nested elements of provided type
   */
  public static <T> T[] spaghettify(final int level, final Class<T> clazz, final Object... inputs)
  {
    if (clazz == null || inputs == null) {
      throw new IllegalArgumentException("neither inputs nor class type arguments may be null");
    }
    return spaghettify(level, clazz, new ArrayList<>(inputs.length), inputs);
  }

  /**
   * Returns a flattened array from inputs which may be an array or an object of same type.If arrays contain other
   * arrays, the function will also add those elements to the final one dimensional array. This behaves similarly to
   * Kotlin's SpreadBuilder with additional recursion capability.
   *
   * @param <T> class type
   * @param level the depth to go down into collections/arrays (0 is the initial level)
   * @param clazz class type of the final array
   * @param initial initial list of elements to include with the final array
   * @param inputs inputs to consider
   * @return one dimensional array containing all nested elements of provided type
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] spaghettify(final int level, final Class<T> clazz,
          final List<T> initial, final Object... inputs)
  {
    if (clazz == null || inputs == null || initial == null) {
      throw new IllegalArgumentException("neither inputs, initial collection nor class type arguments may be null");
    }
    for (final Object input : inputs) {
      if (input.getClass().isArray() || Collection.class.isAssignableFrom(input.getClass())) {
        final Object[] inputArray;
        if (input.getClass().isArray()) {
          inputArray = (Object[]) input;
        } else {
          final Collection collection = (Collection) input;
          inputArray = collection.toArray();
        }
        for (final Object in : inputArray) {
          if (in.getClass().isArray() || Collection.class.isAssignableFrom(in.getClass())) {
            if (level > 0) {
              spaghettify(level - 1, clazz, initial, in);
            }
          } else if (clazz.isAssignableFrom(in.getClass())) {
            initial.add((T) in);
          }
        }
      } else if (clazz.isAssignableFrom(input.getClass())) {
        initial.add((T) input);
      }
    }
    return initial.toArray((T[]) Array.newInstance(clazz, initial.size()));
  }

}
