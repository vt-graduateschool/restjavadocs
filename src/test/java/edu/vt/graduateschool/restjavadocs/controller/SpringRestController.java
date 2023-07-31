/* See LICENSE for licensing and NOTICE for copyright. */
package edu.vt.graduateschool.restjavadocs.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Graduate School
 */
public class SpringRestController
{

  /**
   * Sample text response
   */
  private static final String RESPONSE = "Hello";

  /**
   * stringEndpointNoParams
   *
   * @return {@link #RESPONSE}
   */
  @RequestMapping(path = "/stringEndpointNoParams", method = RequestMethod.GET, produces = "text/plain")
  public String stringEndpointNoParams()
  {
    return RESPONSE;
  }

  /**
   * stringEndpointNonRequiredParams
   *
   * @param nonRequiredOne A non-required parameter of String
   * @param nonRequiredTwo A non-required parameter of String by Java 8 Optional
   * @return {@link #RESPONSE}
   */
  @RequestMapping(path = "/stringEndpointNonRequiredParams", method = RequestMethod.GET, produces = "text/plain")
  public String stringEndpointNonRequiredParams(@RequestParam(required = false) final String nonRequiredOne,
          @RequestParam final Optional<String> nonRequiredTwo)
  {
    return RESPONSE;
  }

  /**
   * stringEndpointRequiredParams
   *
   * @param requiredOne requiredOne
   * @param requiredTwo requiredTwo
   * @return {@link #RESPONSE}
   */
  @RequestMapping(path = "/stringEndpointRequiredParams", method = RequestMethod.GET, produces = "text/plain")
  public String stringEndpointRequiredParams(@RequestParam final String requiredOne,
          @RequestParam final List<String> requiredTwo)
  {
    return RESPONSE;
  }

  /**
   * patchMappingWithRequiredParams
   *
   * @param requiredOne requiredOne
   * @param requiredTwo requiredTwo
   * @return {@link #RESPONSE}
   */
  @InitBinder
  @PatchMapping(path = "/patchMapping", produces = "text/plain", consumes = "patchyness")
  public String patchMappingWithRequiredParams(@RequestParam final String requiredOne,
          @RequestParam final List<String> requiredTwo)
  {
    return RESPONSE;
  }

  /**
   * patchMappingWithRequiredParams (should not match similar method with params)
   *
   * @param requiredOne requiredOne
   * @return {@link #RESPONSE}
   */
  @PatchMapping(path = "/patchMappingThatDoesntMatch", produces = "text/plain", consumes = "patchyness")
  public String patchMappingWithRequiredParams(@RequestParam final String requiredOne)
  {
    return RESPONSE;
  }

  /**
   * stringEndpointWithParams
   *
   * @param requiredOne description for requiredOne
   * @param nonRequiredTwo description for nonRequiredTwo
   * @param nonRequiredThree description for nonRequiredThree
   * @return {@link #RESPONSE}
   */
  @RequestMapping(name = "all",
          path = "/stringEndpointWithParams", method = RequestMethod.GET, produces = {"text/plain", "application/*"},
          consumes = {"text/plain", "application/*"}, headers = "content-type=text/*",
          params = {"match=two", "match=one"}, value = "/stringEndpointWithParams")
  public String stringEndpointWithParams(@RequestParam final String requiredOne,
          @RequestParam(required = false, name = "notrequiredtwo") final String nonRequiredTwo,
          @RequestParam final Optional<String> nonRequiredThree)
  {
    return RESPONSE;
  }

  //CheckStyle:JavadocMethod OFF
  /**
   * endpointWithOptionalTypeNoGeneric
   *
   * @param notevenaParam
   * @param optional just optional
   * @return {@link #RESPONSE}
   */
  @RequestMapping(name = "strangeoptional")
  public String endpointWithOptionalTypeNoGeneric(@RequestParam final Optional optional)
  {
    return RESPONSE;
  }

  /**
   * endpointWithNoParameterComments
   *
   * @return {@link #RESPONSE}
   */
  @RequestMapping(name = "noparamcomments")
  public String endpointWithNoParameterComments(@RequestParam final String requiredOne)
  {
    return RESPONSE;
  }

  /**
   * endpointWithNoParameterComments
   *
   * @param notevenaParam described
   * @param requiredOne
   * @return {@link #RESPONSE}
   */
  @RequestMapping(name = "noparamcommentsdescribed")
  public String endpointWithNoParameterCommentsDescribed(@RequestParam final String requiredOne)
  {
    return RESPONSE;
  }

  @RequestMapping(name = "nocomments")
  public String endpointWithNoMethodComments(@RequestParam final String requiredOne)
  {
    return RESPONSE;
  }
  //CheckStyle:JavadocMethod ON
}
