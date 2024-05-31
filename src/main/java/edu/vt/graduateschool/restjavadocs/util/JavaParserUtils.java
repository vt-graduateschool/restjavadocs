/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.util;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ClassLoaderTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.utils.SourceRoot;
import org.walkmod.javalang.ast.body.JavadocTag;
import org.walkmod.javalang.tags.ParseException;
import org.walkmod.javalang.tags.TagsParser;

/**
 * @author Graduate School
 */
public final class JavaParserUtils
{

  /**
   * Annotation expression name which maps to Jackson and Spring annotations' <b>required</b> expression
   */
  public static final String ANNOTATION_EXPRESSION_REQUIRED = "required";

  /**
   * Private constructor.
   */
  private JavaParserUtils()
  {
  }

  /**
   * Returns a configured resolving compilation unit source root
   *
   * @param sourceRoot path to source
   * @return Configured {@link SourceRoot}
   */
  public static SourceRoot getResolvingSourceRoot(final String sourceRoot)
  {
    return getResolvingSourceRoot(sourceRoot, JavaParserUtils.class.getClassLoader());
  }

  /**
   * Returns a configured resolving compilation unit source root
   *
   * @param sourceRoot path to source
   * @param classLoader class loader to solve from
   * @return Configured {@link SourceRoot}
   */
  public static SourceRoot getResolvingSourceRoot(final String sourceRoot,
          final ClassLoader classLoader)
  {
    if (sourceRoot == null) {
      throw new IllegalArgumentException("sourceRoot cannot be null");
    }
    final CombinedTypeSolver typeSolver = new CombinedTypeSolver(
            new ClassLoaderTypeSolver(classLoader),
            new ReflectionTypeSolver(),
            new JavaParserTypeSolver(sourceRoot));
    final JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
    final ParserConfiguration config = new ParserConfiguration()
            .setStoreTokens(true)
            .setSymbolResolver(symbolSolver);
    return new SourceRoot(Paths.get(sourceRoot), config);
  }

  /**
   * Returns an annotation on a given type, null if not found.
   *
   * @param type type
   * @param annotationClass annotation class
   * @return {@link AnnotationExpr}
   */
  public static AnnotationExpr findAnnotation(final Type type, final Class annotationClass)
  {
    for (final AnnotationExpr annotation : type.getAnnotations()) {
      if (annotation.getName().asString().equals(annotationClass.getName())) {
        return annotation;
      }
    }
    return null;
  }

  /**
   * Returns a variable's getter from the provided class, null if none found.
   *
   * @param foundClass class the method would belong to
   * @param variable variable
   * @return found {@link MethodDeclaration} or null
   */
  public static MethodDeclaration findGetter(final ClassOrInterfaceDeclaration foundClass,
          final VariableDeclarator variable)
  {
    final String methodName = getGetterMethodName(variable);
    final Type variableType = variable.getType();
    final List<MethodDeclaration> getters = foundClass.getMethodsByName(methodName);
    if (!getters.isEmpty()) {
      for (final MethodDeclaration method : getters) {
        if (method.getParameters().isEmpty() && method.getType().equals(variableType)) {
          return method;
        }
      }
    }
    return null;
  }

  /**
   * Returns a variable's setter from the provided class, null if none found.
   *
   * @param foundClass class the method would belong to
   * @param variable variable
   * @return found {@link MethodDeclaration} or null
   */
  public static MethodDeclaration findSetter(final ClassOrInterfaceDeclaration foundClass,
          final VariableDeclarator variable)
  {
    final String methodName = getSetterMethodName(variable);
    final Type variableType = variable.getType();
    final List<MethodDeclaration> setters = foundClass.getMethodsByName(methodName);
    if (!setters.isEmpty()) {
      for (final MethodDeclaration method : setters) {
        if (method.getParameters().size() == 1 && method.getParameters().get(0).getType().equals(variableType)) {
          return method;
        }
      }
    }
    return null;
  }

  /**
   * Returns the text only content for a comment.
   *
   * @param comment Comment to parse
   * @return text of comment
   */
  public static String getCommentText(final Comment comment)
  {
    final String commentText;
    if (comment.isBlockComment()) {
      commentText = comment.asBlockComment().getContent();
    } else if (comment.isJavadocComment()) {
      commentText = comment.asJavadocComment().getContent().replaceAll("^[\\s]*\\*?[\\s]*", "");
    } else if (comment.isLineComment()) {
      commentText = comment.asLineComment().getContent();
    } else {
      commentText = comment.getContent();
    }
    return commentText.trim();
  }

  /**
   * Collects expression values into a {@link List} as {@link String} literals. Intended to only work with annotation
   * interfaces as their values can only be an enum, {@link String} or a {@link String}[] array.
   *
   * @param expression expression to returns values for
   * @return {@link List}&lt;{@link String}&gt;
   */
  public static List<String> collectExpressionValues(final Expression expression)
  {
    final List<String> values = new ArrayList<>();
    if (expression.isArrayInitializerExpr()) {
      for (final Expression arrayExpression : expression.asArrayInitializerExpr().getValues()) {
        values.add(getStringLiteral(arrayExpression));
      }
    } else {
      values.add(getStringLiteral(expression));
    }
    return values;
  }

  /**
   * Returns the {@link String} literal representation of annotation {@link MemberValuePair}. Null values are returned
   * as nulls, if an expression type cannot be determined empty string is returned. Constant expressions are not
   * evaluated but rather converted to string literals as defined (i.e. Long.MAX_VALUE == 'Long.MAX_VALUE').
   *
   * @param value value expression
   * @return literal string value, an empty string is returned if no type match is found.
   */
  public static String getStringLiteral(final Expression value)
  {
    if (value == null || value.isNullLiteralExpr()) {
      return null;
    }
    final String stringValue;
    if (value.isFieldAccessExpr()) {
      final StringBuilder sb = new StringBuilder();
      final FieldAccessExpr fieldAccessExpr = value.asFieldAccessExpr();
      if (fieldAccessExpr.getScope() != null) {
        sb.append(getStringLiteral(fieldAccessExpr.getScope()));
        sb.append('.');
      }
      stringValue = sb.append(fieldAccessExpr.getNameAsString()).toString();
    } else if (value.isStringLiteralExpr()) {
      stringValue = value.asStringLiteralExpr().getValue();
    } else if (value.isBooleanLiteralExpr()) {
      stringValue = value.asBooleanLiteralExpr().toString();
    } else if (value.isCharLiteralExpr()) {
      stringValue = value.asCharLiteralExpr().getValue();
    } else if (value.isLiteralExpr()) {
      stringValue = value.asLiteralExpr().toString();
    } else if (value.isBinaryExpr()) {
      stringValue = evaluateBinaryExpr(value.asBinaryExpr());
    } else {
      stringValue = value.toString(new DefaultPrinterConfiguration());
    }
    return stringValue;
  }

  /**
   * Returns the value expression of an annotation as a {@link String} literal, or null if none found. Array expressions
   * are delimited by a comma (',')
   *
   * @param annotationExpression {@link AnnotationExpr} to query for the value
   * @return {@link String} or null
   */
  public static String getAnnotationValue(final AnnotationExpr annotationExpression)
  {
    return getAnnotationValue(annotationExpression, null, null);
  }

  /**
   * Returns the value expression of an annotation as a {@link String} literal, or null if none found. Array expressions
   * are delimited by a comma (',')
   *
   * @param annotationExpression {@link AnnotationExpr} to query for the value
   * @param expressionName Name of the expression to query for, if this value is null
   * {@link LangUtils#ANNOTATION_EXPRESSION_VALUE} is used
   * @return {@link String} or null
   */
  public static String getAnnotationValue(final AnnotationExpr annotationExpression,
          final String expressionName)
  {
    return getAnnotationValue(annotationExpression, expressionName, null);
  }

  /**
   * Returns the value expression of an annotation as a {@link String} literal, or null if none found.
   *
   * @param annotationExpression {@link AnnotationExpr} to query for the value
   * @param expressionName Name of the expression to query for, if this value is null
   * {@link LangUtils#ANNOTATION_EXPRESSION_VALUE} is used
   * @param delimiter delimiter if there are multiple values, if this value is null a comma (,) is used.
   * @return {@link String} or null
   */
  public static String getAnnotationValue(final AnnotationExpr annotationExpression,
          final String expressionName, final CharSequence delimiter)
  {
    final String[] values = getAnnotationValues(annotationExpression, expressionName);
    if (values != null) {
      return String.join(delimiter == null ? "," : delimiter, values);
    }
    return null;
  }

  /**
   * Returns the list of {@link MemberValuePair} entries for a given annotation. Never null.
   *
   * @param annotationExpression {@link AnnotationExpr} to query for the value of
   * @return {@link List}
   */
  public static List<MemberValuePair> getAnnotationValuePairs(final AnnotationExpr annotationExpression)
  {
    final List<MemberValuePair> memberValues = new ArrayList<>();
    if ((annotationExpression.isNormalAnnotationExpr() || annotationExpression.isSingleMemberAnnotationExpr()) &&
            !annotationExpression.isMarkerAnnotationExpr()) {
      if (annotationExpression.isSingleMemberAnnotationExpr()) {
        memberValues.add(new MemberValuePair(LangUtils.ANNOTATION_EXPRESSION_VALUE,
                annotationExpression.asSingleMemberAnnotationExpr().getMemberValue()));
      } else {
        if (annotationExpression.isNormalAnnotationExpr()) {
          memberValues.addAll(annotationExpression.asNormalAnnotationExpr().getPairs());
        }
      }
    }
    return memberValues;
  }

  /**
   * Returns the value expression of an annotation as a {@link String} literal, or null if none found.
   *
   * @param annotationExpression {@link AnnotationExpr} to query for the value
   * @return {@link String}[] or null
   */
  public static String[] getAnnotationValues(final AnnotationExpr annotationExpression)
  {
    return getAnnotationValues(annotationExpression, null);
  }

  /**
   * Returns the value expression of an annotation as a {@link String} literal, or null if none found.
   *
   * @param annotationExpression {@link AnnotationExpr} to query for the value of
   * @param expressionName Name of the expression to query for, if this value is null
   * {@link LangUtils#ANNOTATION_EXPRESSION_VALUE} is used
   * @return {@link String}[] or null
   */
  public static String[] getAnnotationValues(final AnnotationExpr annotationExpression,
          final String expressionName)
  {
    final String queryForName = expressionName == null ? LangUtils.ANNOTATION_EXPRESSION_VALUE : expressionName;
    final List<MemberValuePair> memberValues = getAnnotationValuePairs(annotationExpression);
    for (MemberValuePair pair : memberValues) {
      final String name = pair.getName().asString();
      if (name.equals(queryForName)) {
        return collectExpressionValues(pair.getValue()).toArray(new String[0]);
      }
    }
    return null;
  }

  /**
   * Parses Javadoc tags and returns a map consisting of the values
   *
   * @param tagName tag name (i.e. "@param")
   * @param comments comments to parse tags from
   * @return map with values
   */
  public static Map<String, String[]> getJavadocTags(final String tagName, final String comments)
  {
    final Map<String, String[]> tagsMap = new HashMap<>();
    try {
      for (final JavadocTag tag : TagsParser.parse(new ByteArrayInputStream(comments.getBytes()))) {
        if (("\"" + tag.getName().trim() + "\"").equals(tagName) &&
                tag.getValues() != null && tag.getValues().size() > 1) {
          final String tagKey = tag.getValues().get(0);
          final String[] tagValues = tag.getValues().subList(1, tag.getValues().size()).toArray(new String[0]);
          tagsMap.put(tagKey, tagValues);
        }
      }
    } catch (final ParseException ex) {
      throw new IllegalArgumentException(ex);
    }
    return tagsMap;
  }

  /**
   * Whether or not resolvable type is assignable from Java 8 {@link Optional} type.
   *
   * @param resolvableType resolvableType
   * @return true if {@link Optional}
   */
  public static boolean isResolvedTypeJava8Optional(final Type resolvableType)
  {
    final String resolvedTypeDescription = getResolvableTypeName(resolvableType);
    return Optional.class.getCanonicalName()
            .equals(resolvedTypeDescription) || resolvedTypeDescription
            .startsWith(Optional.class.getCanonicalName() + "<");
  }

  /**
   * Whether or not a given {@link AnnotationExpr} contains values provided in the filterMap. The key for the map
   * represents an annotation expression and the values are compared by {@link String} values.
   *
   * @param annotationExpression annotation expression
   * @param filterMap values to filter by
   * @return true if a match is found
   */
  public static boolean filterAnnotationExpression(final AnnotationExpr annotationExpression,
          final Map<String, String[]> filterMap)
  {
    if (!(annotationExpression.isMarkerAnnotationExpr() || filterMap == null || filterMap.entrySet().isEmpty())) {
      final NodeList<MemberValuePair> annotationParameters =
              annotationExpression.asNormalAnnotationExpr().getPairs();
      final Map<String, String[]> annotationMap = convertRequestMappingAnnotationValues(annotationParameters);
      for (final String filterKey : filterMap.keySet()) {
        if (!annotationMap.keySet().contains(filterKey)) {
          return false;
        }
        final List<String> annotationValues = Arrays.asList(annotationMap.getOrDefault(filterKey, new String[0]));
        final List<String> filterValues = Arrays.asList(filterMap.getOrDefault(filterKey, new String[0]));
        if (filterValues.contains(null) || annotationValues.contains(null)) {
          throw new IllegalArgumentException("annotation expression values cannot be null (JSR-308 D.3.3)");
        }
        if (!annotationValues.containsAll(filterValues)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns a given string in the format required for a getter method name.
   *
   * @param variable Variable to parse method name for, if the argument is null then null is returned
   * @return getter method name as string or null
   */
  public static String getGetterMethodName(final VariableDeclarator variable)
  {
    return getMutatorMethodName(variable, true);
  }

  /**
   * Returns a given string in the format required for a setter method name.
   *
   * @param variable Variable to parse method name for, if the argument is null then null is returned
   * @return setter method name as string or null
   */
  public static String getSetterMethodName(final VariableDeclarator variable)
  {
    return getMutatorMethodName(variable, false);
  }

  /**
   * Whether or not a given {@link AnnotationExpr} contains an expression called required and whether or not the
   * expression's {@link String} value equals "false".
   *
   * @param annotationExpression annotation expression
   * @return true if a match is found
   */
  public static boolean isAnnotationNotRequired(final AnnotationExpr annotationExpression)
  {
    return Boolean.FALSE.toString().equals(getAnnotationValue(annotationExpression,
            ANNOTATION_EXPRESSION_REQUIRED));
  }

  /**
   * Returns a given string in the format required for a getter or setter method name.
   *
   * @param variable Variable to parse method name for, if the argument is null then null is returned
   * @param isGetter If true the getter is returned, otherwise setter name will be parsed
   * @return formatted string or null
   */
  private static String getMutatorMethodName(final VariableDeclarator variable,
          final boolean isGetter)
  {
    if (variable == null) {
      return null;
    }
    final String variableName = variable.getNameAsString();
    final String mutatorName = variableName.toUpperCase().substring(0, 1) +
            variableName.substring(1, variableName.length());
    final Type variableType = variable.getType();
    final String resolvedTypeName = getResolvableTypeName(variableType);
    if (isGetter) {
      final String getterPrefix;
      if (variableType.isPrimitiveType() && !variableType.isArrayType() && resolvedTypeName.equals(
              boolean.class.getCanonicalName())) {
        getterPrefix = "is";
      } else {
        getterPrefix = "get";
      }
      return getterPrefix + mutatorName;
    }
    return "set" + mutatorName;
  }

  /**
   * Returns the resolved type name. If the type could not be resolved the simple name is returned. See
   * {@link UnsolvedSymbolException#getName()}
   *
   * @param resolvableType resolvableType
   * @return resolved or simple name
   */
  public static String getResolvableTypeName(final Type resolvableType)
  {
    String resolvedTypeDescription;
    try {
      resolvedTypeDescription = resolvableType.resolve().describe();
    } catch (UnsolvedSymbolException e) {
      resolvedTypeDescription = e.getName();
    }
    return resolvedTypeDescription;
  }

  /**
   * Evaluates a constant expression that may reside as annotation expression values.
   *
   * @param expression {@link BinaryExpr}
   * @return Evaluated string literal
   */
  private static String evaluateBinaryExpr(final BinaryExpr expression)
  {
    final StringBuilder sb = new StringBuilder();
    if (expression.getOperator().equals(BinaryExpr.Operator.PLUS)) {
      sb.append(evaluateBinaryExpr(expression.getLeft()));
      sb.append(evaluateBinaryExpr(expression.getRight()));
    } else {
      throw new UnsupportedOperationException("Cannot evaluate BinaryExpr other than PLUS operation");
    }
    return sb.toString();
  }

  /**
   * Evaluates a constant expression that may reside as annotation expression values.
   *
   * @param expression {@link Expression}
   * @return Evaluated string literal
   */
  private static String evaluateBinaryExpr(final Expression expression)
  {
    if (expression.isBinaryExpr()) {
      return evaluateBinaryExpr(expression.asBinaryExpr());
    } else {
      return getStringLiteral(expression);
    }
  }

  /**
   * Converts a given {@link NodeList}&lt;{@link MemberValuePair}&gt; to a map.
   *
   * @param annotationParameters annotationParameters
   * @return Converted map, never null
   */
  private static Map<String, String[]> convertRequestMappingAnnotationValues(
          final NodeList<MemberValuePair> annotationParameters)
  {
    final Map<String, String[]> annotationMap = new HashMap<>();
    for (final MemberValuePair pair : annotationParameters) {
      final String name = pair.getName().asString();
      final Expression expression = pair.getValue();
      final List<String> values = JavaParserUtils
              .collectExpressionValues(expression);
      annotationMap.put(name, values.toArray(String[]::new));
      //@TODO better to parse Spring's @AliasFor annotation in the future
      //fortunately the following applies to all mapping annotations
      if ("path".equals(name)) {
        annotationMap.put("value", values.toArray(String[]::new));
      }
      if ("value".equals(name)) {
        annotationMap.put("path", values.toArray(String[]::new));
      }
    }
    return annotationMap;
  }

}
