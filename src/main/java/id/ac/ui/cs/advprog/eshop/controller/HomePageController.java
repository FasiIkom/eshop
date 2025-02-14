package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.ac.ui.cs.advprog.eshop.service.ProductService;

@Controller
@RequestMapping("/")
public class HomePageController {

    @GetMapping("/")
    public String Homepage() {
        return "homepage";
    }
}
