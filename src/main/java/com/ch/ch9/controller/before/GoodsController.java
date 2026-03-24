package com.ch.ch9.controller.before;

import com.ch.ch9.service.before.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("beforeGoodsController")
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品列表页面（同时支持搜索）
     */
    @GetMapping("/list")
    public String listGoods(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "12") Integer size,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            Model model) {
        return goodsService.listGoods(page, size, keyword, model);
    }

    /**
     * 商品详情页面
     */
    @GetMapping("/detail/{id}")
    public String goodsDetail(@PathVariable("id") Integer id, Model model) {
        return goodsService.goodsDetail(id, model);
    }
}