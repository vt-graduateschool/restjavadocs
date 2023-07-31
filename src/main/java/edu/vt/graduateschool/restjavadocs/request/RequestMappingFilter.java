/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.request;

import java.lang.annotation.Annotation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Graduate School
 */
public final class RequestMappingFilter implements RequestMapping
{

  /**
   * Annotation expression name which maps to {@link org.springframework.web.bind.annotation.RequestMapping#method()}
   */
  public static final String REQUEST_MAPPING_EXPRESSION_METHOD = "method";

  /**
   * Annotation expression name which maps to {@link org.springframework.web.bind.annotation.RequestMapping#consumes()}
   */
  public static final String REQUEST_MAPPING_EXPRESSION_CONSUMES = "consumes";

  /**
   * Annotation expression name which maps to {@link org.springframework.web.bind.annotation.RequestMapping#headers()}
   */
  public static final String REQUEST_MAPPING_EXPRESSION_HEADERS = "headers";

  /**
   * Annotation expression name which maps to {@link org.springframework.web.bind.annotation.RequestMapping#name()}
   */
  public static final String REQUEST_MAPPING_EXPRESSION_NAME = "name";

  /**
   * Annotation expression name which maps to {@link org.springframework.web.bind.annotation.RequestMapping#params()}
   */
  public static final String REQUEST_MAPPING_EXPRESSION_PARAMS = "params";

  /**
   * Annotation expression name which maps to {@link org.springframework.web.bind.annotation.RequestMapping#path()}
   */
  public static final String REQUEST_MAPPING_EXPRESSION_PATH = "path";

  /**
   * Annotation expression name which maps to {@link org.springframework.web.bind.annotation.RequestMapping#produces()}
   */
  public static final String REQUEST_MAPPING_EXPRESSION_PRODUCES = "produces";

  /**
   * name attribute.
   */
  private String name;

  /**
   * value attribute.
   */
  private String[] value;

  /**
   * path attribute.
   */
  private String[] path;

  /**
   * method attribute.
   */
  private RequestMethod[] method;

  /**
   * params attribute.
   */
  private String[] params;

  /**
   * headers attribute.
   */
  private String[] headers;

  /**
   * consumes attribute.
   */
  private String[] consumes;

  /**
   * produces attribute.
   */
  private String[] produces;

  /**
   * Empty constructor.
   */
  public RequestMappingFilter()
  {
  }

  /**
   * Constructor with values.
   *
   * @param nameParam name
   */
  public RequestMappingFilter(final String nameParam)
  {
    this.name = nameParam;
  }

  /**
   * Constructor with values.
   *
   * @param pathParam path
   */
  public RequestMappingFilter(final String[] pathParam)
  {
    this.value = pathParam;
    this.path = pathParam;
  }

  /**
   * Constructor with values.
   *
   * @param nameParam name
   * @param pathParam path
   */
  public RequestMappingFilter(final String nameParam, final String[] pathParam)
  {
    this.name = nameParam;
    this.value = pathParam;
    this.path = pathParam;
  }

  /**
   * Constructor with values.
   *
   * @param methodParam method
   */
  public RequestMappingFilter(final RequestMethod[] methodParam)
  {
    this.method = methodParam;
  }

  /**
   * Constructor with values.
   *
   * @param pathParam path
   * @param methodParam method
   */
  public RequestMappingFilter(final String[] pathParam, final RequestMethod[] methodParam)
  {
    this.path = pathParam;
    this.value = pathParam;
    this.method = methodParam;
  }

  /**
   * Constructor with values.
   *
   * @param pathParam path
   * @param methodParam method
   * @param consumesParam consumes
   */
  public RequestMappingFilter(final String[] pathParam, final RequestMethod[] methodParam,
          final String[] consumesParam)
  {
    this.path = pathParam;
    this.value = pathParam;
    this.method = methodParam;
    this.consumes = consumesParam;
  }

  /**
   * Constructor with values.
   *
   * @param producesParam produces
   * @param pathParam path
   * @param methodParam method
   */
  public RequestMappingFilter(final String[] producesParam, final String[] pathParam,
          final RequestMethod[] methodParam)
  {
    this.path = pathParam;
    this.value = pathParam;
    this.method = methodParam;
    this.produces = producesParam;
  }

  /**
   * Constructor with values.
   *
   * @param pathParam path
   * @param methodParam method
   * @param consumesParam consumes
   * @param producesParam produces
   */
  public RequestMappingFilter(final String[] pathParam, final RequestMethod[] methodParam,
          final String[] consumesParam, final String[] producesParam)
  {
    this.path = pathParam;
    this.value = pathParam;
    this.method = methodParam;
    this.consumes = consumesParam;
    this.produces = producesParam;
  }

  /**
   * Constructor with values.
   *
   * @param pathParam path
   * @param methodParam method
   * @param headersParam headers
   * @param consumesParam consumes
   * @param producesParam produces
   */
  public RequestMappingFilter(final String[] pathParam, final RequestMethod[] methodParam,
          final String[] headersParam, final String[] consumesParam, final String[] producesParam)
  {
    this.path = pathParam;
    this.value = pathParam;
    this.method = methodParam;
    this.headers = headersParam;
    this.consumes = consumesParam;
    this.produces = producesParam;
  }

  /**
   * Constructor with values.
   *
   * @param pathParam path
   * @param methodParam method
   * @param paramsParam params
   * @param headersParam headers
   * @param consumesParam consumes
   * @param producesParam produces
   */
  public RequestMappingFilter(final String[] pathParam, final RequestMethod[] methodParam, final String[] paramsParam,
          final String[] headersParam, final String[] consumesParam, final String[] producesParam)
  {
    this.path = pathParam;
    this.value = pathParam;
    this.method = methodParam;
    this.params = paramsParam;
    this.headers = headersParam;
    this.consumes = consumesParam;
    this.produces = producesParam;
  }

  /**
   * Constructor with all values.
   *
   * @param nameParam path
   * @param pathParam path
   * @param methodParam method
   * @param paramsParam params
   * @param headersParam headers
   * @param consumesParam consumes
   * @param producesParam produces
   */
  public RequestMappingFilter(final String nameParam, final String[] pathParam, final RequestMethod[] methodParam,
          final String[] paramsParam, final String[] headersParam, final String[] consumesParam,
          final String[] producesParam)
  {
    this.name = nameParam;
    this.path = pathParam;
    this.value = pathParam;
    this.method = methodParam;
    this.params = paramsParam;
    this.headers = headersParam;
    this.consumes = consumesParam;
    this.produces = producesParam;
  }

  @Override
  public String name()
  {
    return this.name;
  }

  @Override
  public String[] value()
  {
    return this.value;
  }

  @Override
  public String[] path()
  {
    return this.path;
  }

  @Override
  public RequestMethod[] method()
  {
    return this.method;
  }

  /**
   * Return method enum's names as an array
   *
   * @return String[] equivalent of {@link #method()}
   */
  public String[] methodNames()
  {
    if (this.method == null) {
      return null;
    }
    final String[] methodNames = new String[this.method.length];
    for (int i = 0; i < methodNames.length; i++) {
      methodNames[i] = this.method[i].name();
    }
    return methodNames;
  }

  @Override
  public String[] params()
  {
    return this.params;
  }

  @Override
  public String[] headers()
  {
    return this.headers;
  }

  @Override
  public String[] consumes()
  {
    return this.consumes;
  }

  @Override
  public String[] produces()
  {
    return this.produces;
  }

  /**
   * Setter for name.
   *
   * @param nameParam name
   */
  public void setName(final String nameParam)
  {
    this.name = nameParam;
  }

  /**
   * Setter for value.
   *
   * @param valueParam value
   */
  public void setValue(final String[] valueParam)
  {
    this.value = valueParam;
    this.path = valueParam;
  }

  /**
   * Setter for path.
   *
   * @param pathParam path
   */
  public void setPath(final String[] pathParam)
  {
    this.path = pathParam;
    this.value = pathParam;
  }

  /**
   * Setter for method.
   *
   * @param methodParam method
   */
  public void setMethod(final RequestMethod[] methodParam)
  {
    this.method = methodParam;
  }

  /**
   * Setter for params.
   *
   * @param paramsParam params
   */
  public void setParams(final String[] paramsParam)
  {
    this.params = paramsParam;
  }

  /**
   * Setter for headers.
   *
   * @param headersParam headers
   */
  public void setHeaders(final String[] headersParam)
  {
    this.headers = headersParam;
  }

  /**
   * Setter for consumes.
   *
   * @param consumesParam consumes
   */
  public void setConsumes(final String[] consumesParam)
  {
    this.consumes = consumesParam;
  }

  /**
   * Setter for produces.
   *
   * @param producesParam produces
   */
  public void setProduces(final String[] producesParam)
  {
    this.produces = producesParam;
  }

  @Override
  public Class<? extends Annotation> annotationType()
  {
    return RequestMapping.class;
  }

}
