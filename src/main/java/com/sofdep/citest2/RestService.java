package com.sofdep.citest2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestService {

  @GetMapping("/hello/{aName}")
  public String helloWorld(@PathVariable String aName) {
    return "Hello " + aName;
  }
}
