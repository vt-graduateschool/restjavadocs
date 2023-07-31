/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.visitor;

import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Parses field(s) from source files.
 *
 * @author Graduate School
 */
public abstract class AbstractFieldVisitor extends VoidVisitorAdapter<Class<? extends Annotation>>
{

  /**
   * Logger instance
   */
  private static final Logger LOGGER =
          Logger.getLogger(AbstractFieldVisitor.class.getName());

  /**
   * {@inheritDoc}
   *
   * @param foundClass class to visit
   * @param requireAnnotated an annotation to work with per field
   */
  @Override
  public void visit(final ClassOrInterfaceDeclaration foundClass, final Class<? extends Annotation> requireAnnotated)
  {
    super.visit(foundClass, requireAnnotated);
    foundClass.findAll(FieldDeclaration.class).stream()
            .filter(field -> filterFields(foundClass, field, requireAnnotated))
            .forEach(field -> {
              field.getVariables()
                      .stream()
                      .filter(variableDeclarator -> filterVariableDeclarators(foundClass, field, variableDeclarator))
                      .forEach(variableDeclarator -> processField(foundClass, field, variableDeclarator));
            });
    LOGGER.log(Level.FINEST, "visited class {0}", foundClass.getNameAsString());
  }

  /**
   * Filters each field by the resulting condition of this method. Has higher precedence than {@link
   * #filterVariableDeclarators(
   * com.github.javaparser.ast.body.ClassOrInterfaceDeclaration,
   * com.github.javaparser.ast.body.FieldDeclaration,
   * com.github.javaparser.ast.body.VariableDeclarator)}
   *
   * @param rootClass Class the fields belong to
   * @param field Field declaration
   * @param annotation Annotation if any provided as an argument to the visitor
   * @return true or false
   */
  protected boolean filterFields(final ClassOrInterfaceDeclaration rootClass, final FieldDeclaration field,
          final Class<? extends Annotation> annotation)
  {
    return annotation == null || field.isAnnotationPresent(annotation);
  }

  /**
   * Filters each field with the variable declarator(s) present under the field with the resulting condition of this
   * method. Has lower precedence than {@link
   * #filterFields(com.github.javaparser.ast.body.ClassOrInterfaceDeclaration,
   * com.github.javaparser.ast.body.FieldDeclaration,
   * java.lang.Class) }
   *
   * @param rootClass Class the fields belong to
   * @param field Field declaration
   * @param variable Variable declarator(s) present under this field declaration
   * @return true or false
   */
  protected boolean filterVariableDeclarators(final ClassOrInterfaceDeclaration rootClass,
          final FieldDeclaration field, final VariableDeclarator variable)
  {
    return true;
  }

  /**
   * Does work on each variable declarator on a field.
   *
   * @param rootClass Class the fields belong to
   * @param field Field declaration
   * @param variable Variable declarator(s) present under this field declaration
   */
  protected void processField(final ClassOrInterfaceDeclaration rootClass,
          final FieldDeclaration field, final VariableDeclarator variable)
  {
    LOGGER.log(Level.FINEST, "process called for variable declarator {0}", variable.getNameAsString());
  }

  /**
   * Returns the field name from the variable alone.
   *
   * @param variableDeclarator variableDeclarator
   * @return name of the field
   */
  protected static final String getSimpleFieldName(final VariableDeclarator variableDeclarator)
  {
    return variableDeclarator.getNameAsString();
  }

}
