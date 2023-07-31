/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.visitor;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import edu.vt.graduateschool.restjavadocs.request.RequestMappingFilter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static edu.vt.graduateschool.restjavadocs.util.JavaParserUtils.filterAnnotationExpression;

/**
 * Parses source files of controllers with methods annotated by Spring MVC annotations.
 *
 * @author Graduate School
 */
public abstract class AbstractSpringWebMethodVisitor extends AbstractMethodVisitor
{

  /**
   * Logger instance
   */
  private static final Logger LOGGER =
          Logger.getLogger(AbstractSpringWebMethodVisitor.class.getName());

  /**
   * Map provided to query for filtering specific annotation expression elements. (null means all)
   */
  protected Map<String, String[]> filterMap;

  /**
   * Getter for filterMap.
   *
   * @return filterMap
   */
  public Map<String, String[]> getFilterMap()
  {
    return filterMap;
  }

  /**
   * Setter for filterMap.
   *
   * @param filterMapParam filterMap
   */
  public void setFilterMap(final Map<String, String[]> filterMapParam)
  {
    this.filterMap = filterMapParam;
  }

  @Override
  protected boolean filterMethods(final ClassOrInterfaceDeclaration rootClass, final MethodDeclaration method,
          final Class<? extends Annotation> annotationArg
  )
  {
    Map<String, String[]> filter = filterMap == null ? new HashMap<>() : filterMap;
    final AnnotationExpr annotation;
    if (method.isAnnotationPresent(RequestMapping.class)) {
      annotation = method.getAnnotationByClass(RequestMapping.class).get();
    } else {
      filter = filterMap.entrySet().stream()
              .filter(x -> !x.getKey().equals(RequestMappingFilter.REQUEST_MAPPING_EXPRESSION_METHOD))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      if (method.isAnnotationPresent(GetMapping.class)) {
        annotation = method.getAnnotationByClass(GetMapping.class).get();
      } else if (method.isAnnotationPresent(PostMapping.class)) {
        annotation = method.getAnnotationByClass(PostMapping.class).get();
      } else if (method.isAnnotationPresent(DeleteMapping.class)) {
        annotation = method.getAnnotationByClass(DeleteMapping.class).get();
      } else if (method.isAnnotationPresent(PatchMapping.class)) {
        annotation = method.getAnnotationByClass(PatchMapping.class).get();
      } else if (method.isAnnotationPresent(PutMapping.class)) {
        annotation = method.getAnnotationByClass(PutMapping.class).get();
      } else {
        annotation = null;
      }
    }
    if (annotation != null) {
      return filterAnnotationExpression(annotation, filter) &&
              super.filterMethods(rootClass, method, annotationArg);
    }
    return false;
  }

}
