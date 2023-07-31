/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Graduate School
 */
@RequestMapping("/stringEndpoint")
public class VerySimpleRestController
{

  /**
   * Sample text response
   */
  private static final String RESPONSE = "Hello";

  /**
   * Method with param description.
   *
   * @param param param
   * @return {@link #RESPONSE}
   */
  @GetMapping
  public String stringEndpoint(@RequestParam final String param)
  {
    return RESPONSE;
  }

}
