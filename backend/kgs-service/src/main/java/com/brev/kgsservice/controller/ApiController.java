package com.brev.kgsservice.controller;

import com.brev.kgsservice.generator.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/kgs")
public class ApiController {

    private final Generator generator;

    @Autowired
    public ApiController(Generator generator) {
        this.generator = generator;
    }


    @RequestMapping("/next")
    public String nextKey() throws Exception {
        return generator.nextKey();
    }
}
