package com.ch.ch9.service.before;

import com.ch.ch9.entity.Goods;
import com.ch.ch9.repository.before.GoodsRepository;
import com.ch.ch9.service.before.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    // 默认每页显示数量
    private static final int DEFAULT_PAGE_SIZE = 12;

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public String listGoods(Integer currentPage, Integer pageSize, String keyword, Model model) {
        // 参数处理
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        List<Goods> goodsList;
        int totalCount;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 执行搜索
            goodsList = goodsRepository.searchByName(keyword.trim());
            totalCount = goodsList.size(); // 搜索结果是全部数据

            // 添加搜索关键词到模型
            model.addAttribute("keyword", keyword);
            model.addAttribute("isSearch", true);
        } else {
            // 正常分页查询
            int start = (currentPage - 1) * pageSize;
            goodsList = goodsRepository.findAll(start, pageSize);
            totalCount = goodsRepository.countAll();
            model.addAttribute("isSearch", false);
        }

        // 计算总页数（如果是搜索，默认全部显示不分页）
        int totalPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            // 搜索不分页，显示全部结果
            totalPage = 1;
        } else {
            // 正常分页
            totalPage = (int) Math.ceil((double) totalCount / pageSize);
        }

        // 添加到模型
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("resultCount", goodsList.size());

        return "user/goodsList";
    }

    @Override
    public String goodsDetail(Integer id, Model model) {
        if (id == null || id < 1) {
            model.addAttribute("error", "商品ID无效");
            return "error";
        }

        Goods goods = goodsRepository.findById(id);
        if (goods == null) {
            model.addAttribute("error", "商品不存在");
            return "error";
        }

        model.addAttribute("goods", goods);

        return "user/goodsDetail";
    }

    @Override
    public String searchGoods(String keyword, Model model) {
        return "";
    }
}