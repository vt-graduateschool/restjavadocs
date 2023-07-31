/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.beans;
//CheckStyle:MultipleVariableDeclarations OFF

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Graduate School
 */
public class LessCommonPOJO implements Serializable
{

  /**
   * Three fields at once as marker annotation.
   */
  @JsonProperty(namespace = "whynot")
  private String one, two, three;

  /**
   * Four fields at once with a defined name.
   */
  @JsonProperty("oneName")
  private String shouldnt, find, any, ofthese;

  /**
   * Default constructor
   */
  public LessCommonPOJO()
  {
  }

  /**
   * Getter for one
   *
   * @return one
   */
  public String getOne()
  {
    return one;
  }

  /**
   * Setter for one
   *
   * @param oneParam one
   */
  public void setOne(final String oneParam)
  {
    this.one = oneParam;
  }

  /**
   * Getter for two
   *
   * @return two
   */
  public String getTwo()
  {
    return two;
  }

  /**
   * Setter for two
   *
   * @param twoParam two
   */
  public void setTwo(final String twoParam)
  {
    this.two = twoParam;
  }

  /**
   * Getter for three
   *
   * @return three
   */
  public String getThree()
  {
    return three;
  }

  /**
   * Setter for three
   *
   * @param threeParam three
   */
  public void setThree(final String threeParam)
  {
    this.three = threeParam;
  }
  //CheckStyle:MultipleVariableDeclarations ON
}
