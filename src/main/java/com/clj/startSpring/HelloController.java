package com.clj.startSpring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class HelloController {

    /* @RequestMapping("/hello")
     public String index(){
         return "Hello,World!";
     }*/
    @RequestMapping("/")
    public String index(ModelMap map) {
        //加入一个属性用在模板库中使用
        map.addAttribute("host", "http://google.com");
        //return模板名称，对应src/main/res/resources/templates/index.html
        return "index";
    }
}
