package com.ch.ch9.controller.admin;

import com.ch.ch9.entity.Goods;
import com.ch.ch9.entity.GoodsType;
import com.ch.ch9.service.admin.GoodsService;
import com.ch.ch9.service.admin.GoodsTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller("adminGoodsController")
@RequestMapping("/admin/goods")
public class GoodsController extends AdminBaseController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsTypeService goodsTypeService;

    // 从配置文件中读取上传路径，如果没有配置则使用默认值
    @Value("${file.upload-dir:src/main/resources/static/images}")
    private String uploadDir;
    
    @RequestMapping("/list")
    public String list(@RequestParam(value = "gname", required = false) String gname, Model model, HttpSession session) {
        logger.info("进入商品列表页面，查询条件: gname = {}", gname);
        try {
            List<Goods> goodsList;
            if (gname != null && !gname.trim().isEmpty()) {
                goodsList = goodsService.findByGname(gname.trim());
                logger.info("根据商品名称查询完成，共 {} 条记录", goodsList.size());
            } else {
                goodsList = goodsService.findAll();
                logger.info("商品列表加载完成，共 {} 条记录", goodsList.size());
            }
            model.addAttribute("goodsList", goodsList);
            model.addAttribute("gname", gname);
            // 输出每个商品的gpicture值
            for (Goods goods : goodsList) {
                logger.info("商品ID: {}, 图片路径: {}", goods.getId(), goods.getGpicture());
            }
        } catch (Exception e) {
            logger.error("加载商品列表失败: {}", e.getMessage(), e);
            throw e;
        }
        return "admin/goodsList";
    }

    @RequestMapping("/toAdd")
    public String toAdd(@ModelAttribute("goods") Goods goods, Model model) {
        logger.info("进入添加商品页面");
        try {
            List<GoodsType> goodsTypeList = goodsTypeService.findAll();
            model.addAttribute("goodsTypeList", goodsTypeList);
            logger.info("商品类型列表加载完成，共 {} 条记录", goodsTypeList.size());
        } catch (Exception e) {
            logger.error("加载商品类型列表失败: {}", e.getMessage(), e);
            throw e;
        }
        return "admin/goodsAdd";
    }

    @RequestMapping("/add")
    public String add(@ModelAttribute("goods") Goods goods, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        logger.info("执行添加商品操作: {}", goods);
        try {
            // 处理文件上传
            if (file != null && !file.isEmpty()) {
                // 使用原始文件名
                String originalFilename = file.getOriginalFilename();
                
                // 获取项目根目录的绝对路径
                String projectPath = System.getProperty("user.dir");
                
                // 保存文件到src目录，确保项目重新编译时文件不会丢失
                String srcPath = "D:/QYQ/in-class/JavaEE/project/ch9/admin/ch9/src/main/resources/static/images/";
                File srcDir = new File(srcPath);
                if (!srcDir.exists()) {
                    srcDir.mkdirs();
                }
                File srcFile = new File(srcPath + originalFilename);
                file.transferTo(srcFile);
                
                // 保存文件到target目录，确保Spring Boot能立即识别
                String targetPath = projectPath + "/target/classes/static/images/";
                File targetDir = new File(targetPath);
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }
                File targetFile = new File(targetPath + originalFilename);
                // 使用Files.copy确保文件被正确复制
                java.nio.file.Files.copy(srcFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                // 设置商品图片路径
                goods.setGpicture("/images/" + originalFilename);
            }

            goodsService.add(goods);
            logger.info("商品添加成功: {}", goods);
        } catch (Exception e) {
            logger.error("商品添加失败: {}", e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/goods/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit(@RequestParam Integer id, Model model) {
        logger.info("进入编辑商品页面，ID: {}", id);
        try {
            Goods goods = goodsService.findById(id);
            List<GoodsType> goodsTypeList = goodsTypeService.findAll();
            model.addAttribute("goods", goods);
            model.addAttribute("goodsTypeList", goodsTypeList);
            logger.info("加载待编辑商品信息和商品类型列表完成");
        } catch (Exception e) {
            logger.error("加载待编辑商品信息失败，ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
        return "admin/goodsEdit";
    }

    @RequestMapping("/edit")
    public String edit(@ModelAttribute("goods") Goods goods, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        logger.info("执行编辑商品操作: {}", goods);
        try {
            // 处理文件上传
            if (file != null && !file.isEmpty()) {
                // 使用原始文件名
                String originalFilename = file.getOriginalFilename();
                
                // 获取项目根目录的绝对路径
                String projectPath = System.getProperty("user.dir");
                
                // 1. 保存文件到src目录，确保项目重新编译时文件不会丢失
                String srcPath = "D:/QYQ/in-class/JavaEE/project/ch9/admin/ch9/src/main/resources/static/images/";
                File srcDir = new File(srcPath);
                if (!srcDir.exists()) {
                    srcDir.mkdirs();
                }
                File srcFile = new File(srcPath + originalFilename);
                file.transferTo(srcFile);
                
                // 2. 保存文件到target目录，确保Spring Boot能立即识别
                String targetPath = projectPath + "/target/classes/static/images/";
                File targetDir = new File(targetPath);
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }
                File targetFile = new File(targetPath + originalFilename);
                // 使用Files.copy确保文件被正确复制
                java.nio.file.Files.copy(srcFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                // 设置商品图片路径
                goods.setGpicture("/images/" + originalFilename);
            }

            goodsService.update(goods);
            logger.info("商品编辑成功: {}", goods);
        } catch (Exception e) {
            logger.error("商品编辑失败: {}", e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/goods/list";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam Integer id) throws Exception {
        logger.info("执行删除商品操作，ID: {}", id);
        try {
            goodsService.delete(id);
            logger.info("商品删除成功，ID: {}", id);
        } catch (Exception e) {
            logger.error("商品删除失败，ID: {}: {}", id, e.getMessage(), e);
            throw e;
        }
        return "redirect:/admin/goods/list";
    }
}