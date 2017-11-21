package kaizone.songmaya.qingtian.controller;

import kaizone.songmaya.qingtian.bean.GoodInfoBean;
import kaizone.songmaya.qingtian.bean.GoodTypeBean;
import kaizone.songmaya.qingtian.dto.GoodInfoDTO;
import kaizone.songmaya.qingtian.jpa.GoodInfoJPA;
import kaizone.songmaya.qingtian.jpa.GoodTypeJPA;
import kaizone.songmaya.qingtian.mapper.GoodInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodInfoController {
    /**
     * 注入商品基本信息jpa
     */
    @Autowired
    private GoodInfoJPA goodInfoJPA;
    /**
     * 注入商品类型jpa
     */
    @Autowired
    private GoodTypeJPA goodTypeJPA;
    /**
     * 注入mapStruct转换Mapper
     */
    @Autowired
    private GoodInfoMapper goodInfoMapper;

    @RequestMapping(value = "/detail/{id}")
    public GoodInfoDTO detail(@PathVariable("id") Long id) {
        //查询商品基本信息
        GoodInfoBean goodInfoBean = goodInfoJPA.findOne(id);
        //查询商品类型基本信息
        GoodTypeBean typeBean = goodTypeJPA.findOne(goodInfoBean.getTypeId());
        //返回转换dto
        return goodInfoMapper.from(goodInfoBean, typeBean);
    }
}
