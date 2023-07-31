/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.visitor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.web.bind.annotation.RequestParam;
import org.walkmod.javalang.tags.TagsParser;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.getJavadocTags;
import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.isAnnotationNotRequired;
import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.isResolvedTypeJava8Optional;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

/**
 * Parses source files of controllers with methods annotated by Spring MVC annotations and generates
 * {@link ParameterDescriptor}s
 *
 * @author Graduate School
 */
public final class SpringWebParameterDescriptorMethodVisitor extends AbstractSpringWebMethodVisitor
{

  /**
   * Logger instance
   */
  private static final Logger LOGGER =
          Logger.getLogger(SpringWebParameterDescriptorMethodVisitor.class.getName());

  /**
   * List of parsed parameter descriptors (null means unvisited).
   */
  private List<ParameterDescriptor> parameterDescriptors;

  /**
   * Default constructor.
   */
  public SpringWebParameterDescriptorMethodVisitor()
  {
  }

  /**
   * Constructor with a filterMap provided.
   *
   * @param filterMapParam Map used to filter for Spring MVC annotation expression values
   */
  public SpringWebParameterDescriptorMethodVisitor(final Map<String, String[]> filterMapParam)
  {
    this.filterMap = filterMapParam;
  }

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
  protected void processMethod(final ClassOrInterfaceDeclaration rootClass, final MethodDeclaration method)
  {
    super.processMethod(rootClass, method);
    if (method.getParameters().isNonEmpty()) {
      method.getParameters().forEach(parameter -> {
        if (parameter.isAnnotationPresent(RequestParam.class)) {
          final AnnotationExpr requestParam = parameter.getAnnotationByClass(RequestParam.class).get();
          final boolean isOptional = isResolvedTypeJava8Optional(parameter.getType()) ||
                  isAnnotationNotRequired(requestParam);
          final String paramName = parameter.getNameAsString();
          final ParameterDescriptor descriptor =
                  getParameterDescriptor(method, paramName, isOptional);
          if (descriptor != null) {
            parameterDescriptors.add(descriptor);
          } else {
            LOGGER.log(Level.FINE, "could not find descriptor for paramName {0}", paramName);
          }
        }
      });
    }
  }

  /**
   * Traverses through a matching parameter tag and returns a {@link ParameterDescriptor} with the parameter value
   * described in method's Javadocs.
   *
   * @param methodDeclaration method
   * @param parameterName parameter name to filter
   * @param optional whether or not descriptor is optional
   * @return {@link ParameterDescriptor}, null if none could be found
   * @throws IllegalArgumentException if the javadoc comments could not be parsed
   */
  private static ParameterDescriptor getParameterDescriptor(
          final MethodDeclaration methodDeclaration,
          final String parameterName,
          final boolean optional) throws IllegalArgumentException
  {
    if (methodDeclaration.getComment().isPresent()) {
      final Map<String, String[]> tagValues =
              getJavadocTags(TagsParser.tokenImage[TagsParser.PARAM], methodDeclaration.getComment().toString());
      for (final String tagName : tagValues.keySet()) {
        if (tagName.equals(parameterName)) {
          final ParameterDescriptor desc = parameterWithName(parameterName)
                  .description(tagValues.get(tagName)[0]);
          return optional ? desc.optional() : desc;
        }
      }
    }
    return null;
  }

}
