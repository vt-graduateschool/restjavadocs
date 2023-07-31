/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.beans;
//CheckStyle:JavadocVariable OFF

import java.io.Serializable;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Graduate School
 */
public class JacksonPOJO implements Serializable
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
   * an ignored property
   */
  @JsonIgnore
  protected String ignored;

  // not annotated but included
  protected String notAnnotated;

  protected String notCommented;

  /**
   * @deprecated Use something else.
   *
   * different name than the field using value expression explicitly.
   */
  @Deprecated
  @JsonProperty(value = "differentNameWithValue")
  protected String diffy;

  /**
   * an optional property by Java 8 {@link Optional}
   */
  @JsonIgnore
  private Optional<String> optionalString;

  /**
   * Default constructor
   */
  public JacksonPOJO()
  {
  }

  /**
   * Constructor with params.
   *
   * @param uupidParam uupid
   */
  public JacksonPOJO(final String uupidParam)
  {
    this.uupid = uupidParam;
  }

  /**
   * Id constructor
   *
   * @param idParam id
   */
  public JacksonPOJO(final long idParam)
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
   * Getter for ignored
   *
   * @return ignored
   */
  public String getIgnored()
  {
    return ignored;
  }

  /**
   * Setter for ignored
   *
   * @param ignoredParam ignored
   */
  public void setIgnored(final String ignoredParam)
  {
    this.ignored = ignoredParam;
  }

  /**
   * Getter for notAnnotated
   *
   * @return notAnnotated
   */
  public String getNotAnnotated()
  {
    return notAnnotated;
  }

  /**
   * Setter for notAnnotated
   *
   * @param notAnnotatedParam notAnnotated
   */
  public void setNotAnnotated(final String notAnnotatedParam)
  {
    this.notAnnotated = notAnnotatedParam;
  }

  /**
   * Getter for diffy
   *
   * @return diffy
   */
  public String getDiffy()
  {
    return diffy;
  }

  /**
   * Setter for diffy
   *
   * @param diffyParam diffy
   */
  public void setDiffy(final String diffyParam)
  {
    this.diffy = diffyParam;
  }

  /**
   * Getter for notCommented
   *
   * @return notCommented
   */
  public String getNotCommented()
  {
    return notCommented;
  }

  /**
   * Setter for notCommented
   *
   * @param notCommentedParam notCommented
   */
  public void setNotCommented(final String notCommentedParam)
  {
    this.notCommented = notCommentedParam;
  }
  //CheckStyle:ON
}
