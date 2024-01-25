/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.visitor;

import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Parses method(s) from source files.
 *
 * @author Graduate School
 */
public abstract class AbstractMethodVisitor extends VoidVisitorAdapter<Class<? extends Annotation>>
{

  /**
   * Logger instance
   */
  private static final Logger LOGGER =
          Logger.getLogger(AbstractMethodVisitor.class.getName());

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
    foundClass.getMethods().stream().filter(method -> filterMethods(foundClass, method, requireAnnotated))
            .forEach(method -> processMethod(foundClass, method));
    LOGGER.log(Level.FINEST, "visited class {0}", foundClass.getNameAsString());
  }

  /**
   * Filters each {@link MethodDeclaration} by the resulting condition of this method.
   *
   * @param rootClass Class the fields belong to
   * @param method {@link MethodDeclaration} instance
   * @param annotation Annotation if any provided as an argument to the visitor
   * @return true or false
   */
  protected boolean filterMethods(final ClassOrInterfaceDeclaration rootClass, final MethodDeclaration method,
          final Class<? extends Annotation> annotation)
  {
    return annotation == null || method.isAnnotationPresent(annotation);
  }

  /**
   * Does work on each {@link MethodDeclaration} instance on a class.
   *
   * @param rootClass Class the fields belong to
   * @param method {@link MethodDeclaration} instance
   */
  protected void processMethod(final ClassOrInterfaceDeclaration rootClass, final MethodDeclaration method)
  {
    LOGGER.log(Level.FINEST, "process called for method declaration {0}", getSimpleMethodName(method));
  }

  /**
   * Returns the method name from the {@link MethodDeclaration}.
   *
   * @param method {@link MethodDeclaration} instance
   * @return name of the method
   */
  protected static String getSimpleMethodName(final MethodDeclaration method)
  {
    return method.getNameAsString();
  }

}
