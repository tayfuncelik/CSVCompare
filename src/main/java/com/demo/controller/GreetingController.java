package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {

  @RequestMapping("/")
  public String greeting() {
    return "index";
  }

}
