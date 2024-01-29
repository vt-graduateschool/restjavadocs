# REST Javadocs Library

Provides extensions to Spring REST Docs for automatic snippet generation from Javadocs.

**TODO:**

* @RequestBody support
* Resolve javadoc comment tag links
* Enclosed class support

v0.1 Features:

* Generate response fields from Javadocs for non-enclosed classes
* Generate request descriptors from a controller class' Javadocs where methods are annotated by Spring MVC annotations.

![Coverage](.github/badges/jacoco.svg)

Maven
```
<dependency>
  <groupId>edu.vt.graduateschool</groupId>
  <artifactId>restjavadocs</artifactId>
  <version>0.1</version>
</dependency>
```
  
Usage Example:


**Given the following sample controller StudentInfoController.java:**

```
  /**
   * REST endpoint which returns public directory information with fields released
   * according to the user's account preferences.
   * 
   * @param virginiaTechId 9 digit VT-ID of the person
   * @return {@link ResponseEntity}
   */
  @RequestMapping(value = "/directory-information", method = RequestMethod.GET,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('gs/students/directory-information?get')")
  public ResponseEntity<EdPerson> directoryInformation(
          @NotEmpty @Size(min = 9) @RequestParam(name = "virginiaTechId") final String virginiaTechId)
  {
    return new ResponseEntity<>(restClient.getPersonByVirginiaTechId(virginiaTechId,
            EdPersonType.DATA_TYPE_VIRGINIA_TECH, "all"), HttpStatus.OK);
  }

```

**Given the following sample response EdPerson.java:**

```
/**
 * @author Graduate School
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EdPerson implements Serializable
{

  /**
   * Unique identifier
   */
  @JsonProperty
  private long uid;

  /**
   * VT-PID of the person (uupid)
   */
  @JsonProperty
  private String pid;

  /**
   * Preferred name of the person
   */
  @JsonProperty(required = false)
  private String displayName;

// ... Getters ... Setters

}
```

**The following integration test will automatically generate required fields from Javadocs:**

```
    this.mockMvc.perform(get("/v1/students/directory-information").secure(true)
            .header(HttpHeaders.AUTHORIZATION, AuthorizedRestClient.RFC6750_SECTION_2_1_BEARER_HEADER_FIELD +
                    validToken)
            .param("virginiaTechId", "000000000"))
            .andExpect(status().isOk())
            .andDo(document("get/students/directory-information",
                    relaxedRequestParameters(descriptors(StudentInfoController.class,
                                    "{value: '/directory-information', method: 'GET'}")
                    ),
                    relaxedResponseFields(fields("../core/src/main/java/", EdPerson.class))));
```

**Above example produces the following tables:**

*Table 1. Request Parameters*
| Parameter      | Required | Description                 |
| -------------- | -------- | --------------------------- |
| virginiaTechId | Yes      | 9 digit VT-ID of the person |

*Table 2. Response Fields Path*
| Type           |  Description                 |
| -------------- | ---------------------------- |
| uid            | Unique identifier            |
| pid            | VT-PID of the person (uupid) |
| displayName    | Preferred name of the person |
