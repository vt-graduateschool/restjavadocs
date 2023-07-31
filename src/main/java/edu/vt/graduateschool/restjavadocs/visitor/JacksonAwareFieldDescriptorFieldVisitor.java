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
import org.springframework.restdocs.payload.FieldDescriptor;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getCommentText;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

/**
 * Parses {@link FieldDescriptor}(s) from source files using Jackson annotations.
 *
 * @author Graduate School
 */
public final class JacksonAwareFieldDescriptorFieldVisitor extends AbstractJacksonAwareFieldVisitor
{

  /**
   * Logger instance
   */
  private static final Logger LOGGER =
          Logger.getLogger(JacksonAwareFieldDescriptorFieldVisitor.class.getName());

  /**
   * List of parsed field descriptors (null means unvisited).
   */
  protected List<FieldDescriptor> fieldDescriptors;

  /**
   * Getter for fieldDescriptors.
   *
   * @return fieldDescriptors
   */
  public List<FieldDescriptor> getFieldDescriptors()
  {
    if (fieldDescriptors == null) {
      throw new IllegalStateException("You must visit the class to parse descriptors");
    }
    return fieldDescriptors;
  }

  @Override
  public void visit(final ClassOrInterfaceDeclaration foundClass, final Class<? extends Annotation> requireAnnotated)
  {
    this.fieldDescriptors = new ArrayList<>();
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
      fieldDescriptors.add(optionalField ? subsectionWithPath(getFieldName(rootClass, field, variable))
              .description(commentText).optional() : subsectionWithPath(getFieldName(rootClass, field, variable))
              .description(commentText));
      LOGGER.log(Level.FINEST, "processed fieldDescriptor for variable {0}", variable.getNameAsString());
    }
  }

}
