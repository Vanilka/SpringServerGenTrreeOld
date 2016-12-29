package com.genealogytree.webapplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@RestController
@ComponentScan("com.genealogytree")
@RequestMapping("/")
public class MainRequestDispatcher {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<String> getStartPage() {

        return new ResponseEntity<String>("Hallo World", HttpStatus.OK);
    }

}
