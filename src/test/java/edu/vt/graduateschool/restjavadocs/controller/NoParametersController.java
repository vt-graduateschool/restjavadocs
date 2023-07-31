/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Graduate School
 */
@RequestMapping("/stringEndpoint")
public class NoParametersController
{

  /**
   * Sample text response
   */
  private static final String RESPONSE = "Hello";

  /**
   * Method with no params
   *
   * @return {@link #RESPONSE}
   */
  @GetMapping
  public String stringEndpoint()
  {
    return RESPONSE;
  }

}
