package com.ch.ch9.service.before;

import com.ch.ch9.entity.Goods;
import org.springframework.ui.Model;

import java.util.List;

public interface GoodsService {

    /**
     * 获取商品列表（分页）
     * @param currentPage 当前页码
     * @param pageSize 每页大小
     * @param model 模型
     * @return 视图名称
     */
    String listGoods(Integer currentPage, Integer pageSize,String keyword, Model model);

    /**
     * 获取商品详情
     * @param id 商品ID
     * @param model 模型
     * @return 视图名称
     */
    String goodsDetail(Integer id, Model model);

    /**
     * 根据商品名搜索
     * @param keyword 关键词
     * @param model 模型
     * @return 视图名称
     */
    String searchGoods(String keyword, Model model);

}