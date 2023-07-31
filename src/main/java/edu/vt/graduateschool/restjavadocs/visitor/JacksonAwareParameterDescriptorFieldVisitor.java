/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.visitor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import org.springframework.restdocs.request.ParameterDescriptor;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getCommentText;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

/**
 * Parses {@link ParameterDescriptor}(s) from source files using Jackson annotations.
 *
 * @author Graduate School
 */
public final class JacksonAwareParameterDescriptorFieldVisitor extends AbstractJacksonAwareFieldVisitor
{

  /**
   * Logger instance
   */
  private static final Logger LOGGER =
          Logger.getLogger(JacksonAwareParameterDescriptorFieldVisitor.class.getName());

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
    final boolean optionalField = isFieldOptional(rootClass, field, variable);
    final Optional<Comment> comments = getFieldComment(rootClass, field, variable);
    if (comments.isPresent()) {
      final String commentText = getCommentText(comments.get());
      final String fieldName = getFieldName(rootClass, field, variable);
      final ParameterDescriptor parameterDescriptor = parameterWithName(fieldName).description(commentText);
      parameterDescriptors.add(optionalField ? parameterDescriptor.optional() : parameterDescriptor);
      LOGGER.log(Level.FINEST, "processed parameterDescriptor for variable {0}", variable.getNameAsString());
    }
  }

}
