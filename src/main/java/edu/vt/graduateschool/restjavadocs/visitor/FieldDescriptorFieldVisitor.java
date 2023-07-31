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
import org.springframework.restdocs.payload.FieldDescriptor;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getCommentText;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

/**
 * Parses {@link FieldDescriptor}(s) from source files.
 *
 * @author Graduate School
 */
public class FieldDescriptorFieldVisitor extends AbstractFieldVisitor
{

  /**
   * Logger instance
   */
  private static final Logger LOGGER =
          Logger.getLogger(FieldDescriptorFieldVisitor.class.getName());

  /**
   * List of parsed field descriptors (null means unvisited).
   */
  protected List<FieldDescriptor> fieldDescriptors;

  /**
   * Getter for fieldDescriptor.
   *
   * @return fieldDescriptor
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
  protected void processField(final ClassOrInterfaceDeclaration rootClass,
          final FieldDeclaration field, final VariableDeclarator variable)
  {
    if (field.getComment().isPresent()) {
      final String fieldName = getSimpleFieldName(variable);
      final String commentText = getCommentText(field.getComment().get());
      fieldDescriptors.add(subsectionWithPath(fieldName)
              .description(commentText));
    }
    LOGGER.log(Level.FINEST, "processed fieldDescriptor for variable {0}", variable.getNameAsString());
  }

}
