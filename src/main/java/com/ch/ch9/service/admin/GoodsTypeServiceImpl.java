package com.ch.ch9.service.admin;

import com.ch.ch9.entity.GoodsType;
import com.ch.ch9.mapper.admin.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("goodsTypeService")
@Transactional
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    private TypeMapper goodsTypeMapper;

    @Override
    public List<GoodsType> findAll() {
        return goodsTypeMapper.selectList(null);
    }

    @Override
    public GoodsType findById(Integer id) {
        GoodsType goodsType= goodsTypeMapper.selectById(id);
        if(goodsType==null){
            throw new RuntimeException("未找到ID为"+id+"的商品类型");
        }
        return goodsType;
    }
    
    @Override
    public List<GoodsType> findByTname(String tname) {
        return goodsTypeMapper.findByTname(tname);
    }

    @Override
    public void add(GoodsType goodsType) throws Exception {
        int result= goodsTypeMapper.insert(goodsType);
        if(result<=0){
            throw new Exception("添加商品类型失败");
        }
    }

    @Override
    public void update(GoodsType goodsType) throws Exception {
        int result= goodsTypeMapper.updateById(goodsType);
        if(result<=0){
            throw new Exception("更新商品类型失败");
        }
    }

    @Override
    public void delete(Integer id) throws Exception {
        int result= goodsTypeMapper.deleteById(id);
        if(result<=0){
            throw new Exception("删除商品类型失败");
        }
    }
}