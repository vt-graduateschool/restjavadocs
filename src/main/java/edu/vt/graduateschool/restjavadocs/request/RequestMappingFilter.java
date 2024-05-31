/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.request;

/**
 * @author Graduate School
 */
public final class RequestMappingFilter
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
   * path attribute.
   */
  private String[] path;

  /**
   * method attribute.
   */
  private String[] method;

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
    this.path = pathParam;
  }

  /**
   * Constructor with values.
   *
   * @param pathParam path
   * @param methodParam method
   */
  public RequestMappingFilter(final String[] pathParam, final String[] methodParam)
  {
    this.path = pathParam;
    this.method = methodParam;
  }

  /**
   * Constructor with values.
   *
   * @param pathParam path
   * @param methodParam method
   * @param consumesParam consumes
   */
  public RequestMappingFilter(final String[] pathParam, final String[] methodParam,
          final String[] consumesParam)
  {
    this.path = pathParam;
    this.method = methodParam;
    this.consumes = consumesParam;
  }

  /**
   * Constructor with values.
   *
   * @param pathParam path
   * @param methodParam method
   * @param consumesParam consumes
   * @param producesParam produces
   */
  public RequestMappingFilter(final String[] pathParam, final String[] methodParam,
          final String[] consumesParam, final String[] producesParam)
  {
    this.path = pathParam;
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
  public RequestMappingFilter(final String[] pathParam, final String[] methodParam,
          final String[] headersParam, final String[] consumesParam, final String[] producesParam)
  {
    this.path = pathParam;
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
  public RequestMappingFilter(final String[] pathParam, final String[] methodParam, final String[] paramsParam,
          final String[] headersParam, final String[] consumesParam, final String[] producesParam)
  {
    this.path = pathParam;
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
  public RequestMappingFilter(final String nameParam, final String[] pathParam, final String[] methodParam,
          final String[] paramsParam, final String[] headersParam, final String[] consumesParam,
          final String[] producesParam)
  {
    this.name = nameParam;
    this.path = pathParam;
    this.method = methodParam;
    this.params = paramsParam;
    this.headers = headersParam;
    this.consumes = consumesParam;
    this.produces = producesParam;
  }

  /**
   * Getter for name
   *
   * @return name
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Getter for value
   *
   * @return value
   */
  public String[] getValue()
  {
    return this.path;
  }

  /**
   * Getter for path
   *
   * @return path
   */
  public String[] getPath()
  {
    return this.path;
  }

  /**
   * Getter for method
   *
   * @return method
   */
  public String[] getMethod()
  {
    return this.method;
  }

  /**
   * Getter for params
   *
   * @return params
   */
  public String[] getParams()
  {
    return this.params;
  }

  /**
   * Getter for headers
   *
   * @return headers
   */
  public String[] getHeaders()
  {
    return this.headers;
  }

  /**
   * Getter for consumes
   *
   * @return consumes
   */
  public String[] getConsumes()
  {
    return this.consumes;
  }

  /**
   * Getter for produces
   *
   * @return produces
   */
  public String[] getProduces()
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
  }

  /**
   * Setter for method.
   *
   * @param methodParam method
   */
  public void setMethod(final String[] methodParam)
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

}
