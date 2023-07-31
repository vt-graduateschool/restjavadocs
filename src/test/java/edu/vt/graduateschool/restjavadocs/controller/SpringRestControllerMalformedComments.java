/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Graduate School
 */
public class SpringRestControllerMalformedComments
{

  /**
   * Sample text response
   */
  private static final String RESPONSE = "Hello";

  /**
   * Mapping that should be filtered out
   *
   * @param requiredOne requiredOne
   * @return {@link #RESPONSE}
   */
  public String notincluded(@RequestParam final String requiredOne)
  {
    return RESPONSE;
  }

  /**
   * Mapping that should be filtered out
   *
   * @param requiredOne requiredOne
   * @return {@link #RESPONSE}
   */
  @GetMapping
  public String notincludedgetmapping(final String requiredOne)
  {
    return RESPONSE;
  }

  /**
   * Mapping that should be filtered out
   *
   * @param requiredOne requiredOne
   * @return {@link #RESPONSE}
   */
  @PatchMapping
  public String notincludedpatchmapping(@RequestParam final String requiredOne)
  {
    return RESPONSE;
  }

  /**
   * Mapping that should be filtered out
   *
   * @param requiredOne requiredOne
   * @return {@link #RESPONSE}
   */
  @PostMapping
  public String notincludedpostmapping(@RequestParam final String requiredOne)
  {
    return RESPONSE;
  }

  /**
   * Mapping that should be filtered out
   *
   * @param requiredOne requiredOne
   * @return {@link #RESPONSE}
   */
  @DeleteMapping
  public String notincludeddeletemapping(@RequestParam final String requiredOne)
  {
    return RESPONSE;
  }

  /**
   * Mapping that should be filtered out
   *
   * @param requiredOne requiredOne
   * @return {@link #RESPONSE}
   */
  @PutMapping
  public String notincludedputmapping(@RequestParam final String requiredOne)
  {
    return RESPONSE;
  }

//CheckStyle:JavadocMethod OFF
  /**
   * Method with param description. null null null null null null   {@inheritDoc {@link
   *
   * @param requiredOne requiredOne
   * @return {@link #RESPONSE}
   */
  @RequestMapping(name = "badcomments")
  public String endpointWithBadComments(@RequestParam final String requiredOne)
  {
    return RESPONSE;
  }
  //CheckStyle:JavadocMethod ON

}
