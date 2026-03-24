package com.ch.ch9.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // 直接跳转到商品列表页
        return "redirect:/goods/list";
    }
}