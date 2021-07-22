package com.mafei.customerorderservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
  @Author mafei
  @Created 7/22/2021 3:22 AM  
*/
@RestController
@RequestMapping("/customer-order")
public class IndexController {
    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("I'm from order service");
    }

}
