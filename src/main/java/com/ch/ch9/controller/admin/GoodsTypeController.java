package com.ch.ch9.controller.admin;

import com.ch.ch9.entity.GoodsType;
import com.ch.ch9.service.admin.GoodsTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller("adminGoodsTypeController")
@RequestMapping("/admin/type")
public class GoodsTypeController extends AdminBaseController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsTypeController.class);
    
    @Autowired
    private GoodsTypeService goodsTypeService;

    @RequestMapping("/list")
    public String list(@RequestParam(value = "tname", required = false) String tname, Model model, HttpSession session) {
        logger.info("进入商品类型列表页面，查询条件: tname = {}", tname);
        try {
            List<GoodsType> goodsTypeList;
            if (tname != null && !tname.trim().isEmpty()) {
                goodsTypeList = goodsTypeService.findByTname(tname.trim());
                logger.info("根据商品类型名称查询完成，共 {} 条记录", goodsTypeList.size());
            } else {
                goodsTypeList = goodsTypeService.findAll();
                logger.info("商品类型列表加载完成，共 {} 条记录", goodsTypeList.size());
            }
            model.addAttribute("goodsTypeList", goodsTypeList);
            model.addAttribute("tname", tname);
        } catch (Exception e) {
            logger.error("加载商品类型列表失败: {}", e.getMessage(), e);
            throw e;
        }
        return "admin/goodsTypeList";
    }

    @RequestMapping("/toAdd")
    public String toAdd(Model model) {
        logger.info("进入添加商品类型页面");
        model.addAttribute("goodsType",new GoodsType());
        return "admin/goodsTypeAdd";
    }

    @RequestMapping("/add")
    public String add(@ModelAttribute GoodsType goodsType) throws Exception {
        logger.info("执行添加商品类型操作: {}", goodsType);
        try {
            goodsTypeService.add(goodsType);
            logger.info("商品类型添加成功: {}", goodsType);
        } catch (Exception e) {
            logger.error("商品类型添加失败: {}", e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/type/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit(@RequestParam Integer id, Model model) {
        logger.info("进入编辑商品类型页面，ID: {}", id);
        try {
            GoodsType goodsType = goodsTypeService.findById(id);
            model.addAttribute("goodsType", goodsType);
            logger.info("加载待编辑商品类型信息: {}", goodsType);
        } catch (Exception e) {
            logger.error("加载待编辑商品类型失败，ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
        return "admin/goodsTypeEdit";
    }

    @RequestMapping("/edit")
    public String edit(@ModelAttribute("goodsType") GoodsType goodsType) throws Exception {
        logger.info("执行编辑商品类型操作: {}", goodsType);
        try {
            goodsTypeService.update(goodsType);
            logger.info("商品类型编辑成功: {}", goodsType);
        } catch (Exception e) {
            logger.error("商品类型编辑失败: {}", e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/type/list";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam Integer id) throws Exception {
        logger.info("执行删除商品类型操作，ID: {}", id);
        try {
            goodsTypeService.delete(id);
            logger.info("商品类型删除成功，ID: {}", id);
        } catch (Exception e) {
            logger.error("商品类型删除失败，ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/type/list";
    }
}