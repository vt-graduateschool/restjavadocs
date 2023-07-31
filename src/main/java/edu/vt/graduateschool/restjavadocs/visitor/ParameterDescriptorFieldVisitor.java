/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.visitor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import org.springframework.restdocs.request.ParameterDescriptor;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getCommentText;
import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.isResolvedTypeJava8Optional;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

/**
 * Parses {@link ParameterDescriptor}(s) from source files. If a field has the Java 8 optional type it is then marked
 * optional.
 *
 * @author Graduate School
 */
public class ParameterDescriptorFieldVisitor extends AbstractFieldVisitor
{

  /**
   * Logger instance
   */
  private static final Logger LOGGER =
          Logger.getLogger(ParameterDescriptorFieldVisitor.class.getName());

  /**
   * List of parsed parameter descriptors (null means unvisited).
   */
  private List<ParameterDescriptor> parameterDescriptors;

  /**
   * Getter for parameterDescriptors.
   *
   * @return parameterDescriptors
   */
  public List<ParameterDescriptor> getParameterDescriptors()
  {
    if (parameterDescriptors == null) {
      throw new IllegalStateException("You must visit the class to parse descriptors");
    }
    return parameterDescriptors;
  }

  @Override
  public void visit(final ClassOrInterfaceDeclaration foundClass, final Class<? extends Annotation> requireAnnotated)
  {
    this.parameterDescriptors = new ArrayList<>();
    super.visit(foundClass, requireAnnotated);
  }

  @Override
  protected void processField(final ClassOrInterfaceDeclaration rootClass, final FieldDeclaration field,
          final VariableDeclarator variable)
  {
    super.processField(rootClass, field, variable);
    final boolean optionalField = isResolvedTypeJava8Optional(variable.getType());
    if (field.getComment().isPresent()) {
      final String commentText = getCommentText(field.getComment().get());
      final String fieldName = getSimpleFieldName(variable);
      final ParameterDescriptor parameterDescriptor = parameterWithName(fieldName).description(commentText);
      parameterDescriptors.add(optionalField ? parameterDescriptor.optional() : parameterDescriptor);
    }
    LOGGER.log(Level.FINEST, "processed parameterDescriptor for variable {0}", variable.getNameAsString());
  }

}
