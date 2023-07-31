/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.beans;
//CheckStyle:JavadocVariable OFF

import java.io.Serializable;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonPointer;

/**
 * @author Graduate School
 */
@JsonIgnoreProperties({"id", "differentName", "ignoreMeToo"})
public class JacksonPOJOIgnoreProperties implements Serializable
{

  /* id of the entry */
  @JsonProperty
  protected long id;

  /**
   * different name than the field
   */
  @JsonProperty("differentName")
  protected String uupid;

  /**
   * an annotatedOnGetterWithDifferentName property
   */
  protected JsonPointer annotatedOnGetterWithDifferentName;

  // not annotated on field but getter
  protected String annotatedOnGetter;

  /**
   * an optional property by Java 8 {@link Optional}
   */
  private Optional<String> notAnnotatedOptional;

  /**
   * an optional property by getter property
   */
  private String optionalOnGetter;

  /**
   * an optional property by Java 8 {@link Optional}
   */
  @JsonIgnore
  private Optional<String> notAnnotatedOptionalIgnored;

  /**
   * Default constructor
   */
  public JacksonPOJOIgnoreProperties()
  {
  }

  /**
   * Constructor with params.
   *
   * @param uupidParam uupid
   */
  public JacksonPOJOIgnoreProperties(final String uupidParam)
  {
    this.uupid = uupidParam;
  }

  /**
   * Id constructor
   *
   * @param idParam id
   */
  public JacksonPOJOIgnoreProperties(final long idParam)
  {
    id = idParam;
  }

  /**
   * Getter for id
   *
   * @return id
   */
  public long getId()
  {
    return id;
  }

  /**
   * Setter for id
   *
   * @param idParam id
   */
  public void setId(final long idParam)
  {
    this.id = idParam;
  }

  /**
   * Getter for uupid
   *
   * @return uupid
   */
  public String getUupid()
  {
    return uupid;
  }

  /**
   * Setter for uupid
   *
   * @param uupidParam uupid
   */
  public void setUupid(final String uupidParam)
  {
    this.uupid = uupidParam;
  }

  /**
   * Getter for annotatedOnGetterWithDifferentName
   *
   * @return annotatedOnGetterWithDifferentName
   */
  @JsonProperty("ignoreMeToo")
  public JsonPointer getAnnotatedOnGetterWithDifferentName()
  {
    return annotatedOnGetterWithDifferentName;
  }

  /**
   * Setter for annotatedOnGetterWithDifferentName
   *
   * @param ignoredParam annotatedOnGetterWithDifferentName
   */
  public void setAnnotatedOnGetterWithDifferentName(final JsonPointer ignoredParam)
  {
    this.annotatedOnGetterWithDifferentName = ignoredParam;
  }

  /**
   * Getter for annotatedOnGetter
   *
   * @return annotatedOnGetter
   */
  @JsonGetter(value = "nameFromGetter")
  public String getAnnotatedOnGetter()
  {
    return annotatedOnGetter;
  }

  /**
   * Setter for annotatedOnGetter
   *
   * @param notAnnotatedParam annotatedOnGetter
   */
  public void setAnnotatedOnGetter(final String notAnnotatedParam)
  {
    this.annotatedOnGetter = notAnnotatedParam;
  }

  /**
   * Getter for optionalOnGetter
   *
   * @return optionalOnGetter
   */
  @JsonProperty(required = false)
  public String getOptionalOnGetter()
  {
    return optionalOnGetter;
  }

  /**
   * Setter for optionalOnGetter
   *
   * @param optionalOnGetterParam optionalOnGetter
   */
  public void setOptionalOnGetter(final String optionalOnGetterParam)
  {
    this.optionalOnGetter = optionalOnGetterParam;
  }

  //CheckStyle:ON
}
