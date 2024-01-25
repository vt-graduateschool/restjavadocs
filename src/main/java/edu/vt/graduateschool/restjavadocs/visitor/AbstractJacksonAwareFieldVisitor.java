/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.visitor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.findGetter;
import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getAnnotationValue;
import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getAnnotationValues;
import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.isAnnotationNotRequired;
import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.isResolvedTypeJava8Optional;

/**
 * Parses source files using for fields using the business logic that applies with Jackson annotations.
 *
 * @author Graduate School
 */
public abstract class AbstractJacksonAwareFieldVisitor extends AbstractFieldVisitor
{

  /**
   * Logger instance
   */
  private static final Logger LOGGER =
          Logger.getLogger(AbstractJacksonAwareFieldVisitor.class.getName());

  /**
   * Contains names of fields that are ignored at the type level by {@link JsonIgnoreProperties}.
   */
  private final List<String> ignoredFieldsTypeLevel = new ArrayList<>();

  @Override
  public void visit(final ClassOrInterfaceDeclaration foundClass, final Class<? extends Annotation> requireAnnotated)
  {
    final Optional<AnnotationExpr> ignoreProperties = foundClass.getAnnotationByClass(JsonIgnoreProperties.class);
    if (ignoreProperties.isPresent()) {
      final String[] values = getAnnotationValues(
              foundClass.getAnnotationByClass(JsonIgnoreProperties.class).get());
      ignoredFieldsTypeLevel.clear();
      if (values != null) {
        ignoredFieldsTypeLevel.addAll(Arrays.asList(values));
        LOGGER.log(Level.FINEST, "found ignored properties while visiting class");
      }
    }
    super.visit(foundClass, requireAnnotated);
  }

  @Override
  protected boolean filterVariableDeclarators(final ClassOrInterfaceDeclaration rootClass,
          final FieldDeclaration field, final VariableDeclarator variable)
  {
    return !ignoredFieldsTypeLevel.contains(getFieldName(rootClass, field, variable)) &&
            !(field.getVariables().size() > 1 && getJsonPropertyName(field) != null);
  }

  @Override
  protected boolean filterFields(final ClassOrInterfaceDeclaration rootClass, final FieldDeclaration field,
          final Class<? extends Annotation> annotation)
  {
    return !field.isStatic() && !field.isPhantom() &&
            !field.isAnnotationPresent(JsonIgnore.class) && super.filterFields(rootClass, field, annotation);
  }

  /**
   * Returns the name defined under the {@link JsonProperty} annotation's value if such a definition exists.
   *
   * @param node {@link NodeWithAnnotations} to check
   * @return found name in value, or null if none exists
   */
  protected static String getJsonPropertyName(final NodeWithAnnotations<?> node)
  {
    final Optional<AnnotationExpr> expression = node.getAnnotationByClass(JsonProperty.class);
    return expression.isPresent() ? getAnnotationValue(expression.get()) : null;
  }

  /**
   * Returns the name defined under the {@link JsonGetter} annotation's value if such a definition exists.
   *
   * @param node {@link NodeWithAnnotations} to check
   * @return found name in value, or null if none exists
   */
  protected static String getJsonGetterName(final NodeWithAnnotations<?> node)
  {
    final Optional<AnnotationExpr> expression = node.getAnnotationByClass(JsonGetter.class);
    return expression.isPresent() ? getAnnotationValue(expression.get()) : null;
  }

  /**
   * Returns the field name or the {@link JsonProperty} value specified. Latter takes precedence. If the field is not
   * annotated with {@link JsonProperty} and the getter is annotated as such with a value defined than the getter's
   * property name is used.
   *
   * @param rootClass Class the fields belong to
   * @param field field
   * @param variableDeclarator variableDeclarator
   * @return Name of the response field
   */
  protected static String getFieldName(final ClassOrInterfaceDeclaration rootClass,
          final FieldDeclaration field,
          final VariableDeclarator variableDeclarator)
  {
    if (!field.isAnnotationPresent(JsonProperty.class)) {
      final MethodDeclaration fieldGetter = findGetter(rootClass, variableDeclarator);
      if (fieldGetter != null) {
        if (fieldGetter.getAnnotationByClass(JsonIgnore.class).isEmpty()) {
          final String foundName = getJsonPropertyName(fieldGetter) != null ? getJsonPropertyName(fieldGetter) :
                  getJsonGetterName(fieldGetter);
          if (foundName != null) {
            return foundName;
          }
        }
      }
      return getSimpleFieldName(variableDeclarator);
    } else {
      final String jsonPropertyName = getJsonPropertyName(field);
      return jsonPropertyName == null ? getSimpleFieldName(variableDeclarator) : jsonPropertyName;
    }
  }

  /**
   * Returns whether or not the field indicates being optional or not required on either its getter or the field itself.
   * The conditions checked are true if the field has {@link JsonProperty}(required=true) or if the getter does while
   * not having {@link JsonIgnore} on either or if the declared field type is of Java 8 {@link Optional}.
   *
   * @param rootClass Class the fields belong to
   * @param field field
   * @param variable variableDeclarator
   * @return {@link Comment} optional
   */
  protected static boolean isFieldOptional(final ClassOrInterfaceDeclaration rootClass,
          final FieldDeclaration field,
          final VariableDeclarator variable)
  {
    if (isResolvedTypeJava8Optional(variable.getType())) {
      return true;
    }
    boolean isOptional = false;
    final Optional<AnnotationExpr> jsonPropertyOnField = field.getAnnotationByClass(JsonProperty.class);
    if (jsonPropertyOnField.isEmpty()) {
      final MethodDeclaration fieldGetter = findGetter(rootClass, variable);
      if (fieldGetter != null) {
        if (fieldGetter.getAnnotationByClass(JsonIgnore.class).isEmpty() &&
                fieldGetter.isAnnotationPresent(JsonProperty.class)) {
          isOptional = isAnnotationNotRequired(fieldGetter.getAnnotationByClass(JsonProperty.class).get());
        }
      }
    } else {
      isOptional = isAnnotationNotRequired(jsonPropertyOnField.get());
    }
    return isOptional;
  }

  /**
   * Returns either the field or getter comments depending on which one takes precedence
   *
   * @param rootClass Class the fields belong to
   * @param field field
   * @param variableDeclarator variableDeclarator
   * @return {@link Comment} optional
   */
  protected static Optional<Comment> getFieldComment(final ClassOrInterfaceDeclaration rootClass,
          final FieldDeclaration field,
          final VariableDeclarator variableDeclarator)
  {
    if (!field.isAnnotationPresent(JsonProperty.class)) {
      final MethodDeclaration fieldGetter = findGetter(rootClass, variableDeclarator);
      if (fieldGetter != null) {
        if (fieldGetter.getAnnotationByClass(JsonIgnore.class).isEmpty() &&
                (fieldGetter.getAnnotationByClass(JsonProperty.class).isPresent() ||
                fieldGetter.getAnnotationByClass(JsonGetter.class).isPresent() ||
                fieldGetter.getAnnotationByClass(JsonAnyGetter.class).isPresent())) {
          return fieldGetter.getComment();
        }
      }
    }
    return field.getComment();
  }

}
