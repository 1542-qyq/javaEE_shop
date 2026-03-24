package com.ch.ch9.service.admin;

import com.ch.ch9.entity.Goods;
import com.ch.ch9.mapper.admin.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("goodsService")
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsmapper;

    @Override
    public List<Goods> findAll() {
        return goodsmapper.selectList(null);
    }

    @Override
    public Goods findById(Integer id) {
        return goodsmapper.selectById(id);
    }
    
    @Override
    public List<Goods> findByGname(String gname) {
        return goodsmapper.findByGname(gname);
    }

    @Override
    public void add(Goods goods) throws Exception {
        goodsmapper.insert(goods);
    }

    @Override
    public void update(Goods goods) throws Exception {
        goodsmapper.updateById(goods);
    }

    @Override
    public void delete(Integer id) throws Exception {
        // 1. 先获取商品信息，包括图片路径
        Goods goods = goodsmapper.selectById(id);
        if (goods != null && goods.getGpicture() != null) {
            // 2. 删除服务器上的图片文件
            String picturePath = goods.getGpicture();
            String fileName = picturePath.substring(picturePath.lastIndexOf('/') + 1);
            
            // 获取项目根目录的绝对路径
            String projectPath = System.getProperty("user.dir");
            
            // 删除target目录中的图片
            String targetFilePath = projectPath + "/target/classes/static/images/" + fileName;
            java.io.File targetFile = new java.io.File(targetFilePath);
            if (targetFile.exists()) {
                boolean targetDeleted = targetFile.delete();
                System.out.println("target目录图片删除" + (targetDeleted ? "成功" : "失败") + ": " + fileName);
            }
            
            // 删除src目录中的图片
            String srcFilePath = projectPath + "/src/main/resources/static/images/" + fileName;
            java.io.File srcFile = new java.io.File(srcFilePath);
            if (srcFile.exists()) {
                boolean srcDeleted = srcFile.delete();
                System.out.println("src目录图片删除" + (srcDeleted ? "成功" : "失败") + ": " + fileName);
            }
        }
        // 3. 删除数据库中的商品记录
        goodsmapper.deleteById(id);
        // 4. 重置自增ID，使其从1开始
        goodsmapper.resetAutoIncrement();
    }
}