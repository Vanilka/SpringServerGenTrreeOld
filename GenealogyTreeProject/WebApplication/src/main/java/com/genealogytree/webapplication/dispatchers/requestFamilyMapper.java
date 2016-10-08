package com.genealogytree.webapplication.dispatchers;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("com.genealogytree")
@RequestMapping("/project")
public class requestFamilyMapper {

}
