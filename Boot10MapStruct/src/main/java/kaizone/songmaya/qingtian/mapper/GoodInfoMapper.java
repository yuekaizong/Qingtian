package kaizone.songmaya.qingtian.mapper;

import kaizone.songmaya.qingtian.bean.GoodInfoBean;
import kaizone.songmaya.qingtian.bean.GoodTypeBean;
import kaizone.songmaya.qingtian.dto.GoodInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


//@Mapper
@Mapper(componentModel = "spring")
public interface GoodInfoMapper {
//    public static GoodInfoMapper MAPPER = Mappers.getMapper(GoodInfoMapper.class);

    @Mappings({
            @Mapping(source = "type.name", target = "typeName"),
            @Mapping(source = "good.id", target = "goodId"),
            @Mapping(source = "good.title", target = "goodName"),
            @Mapping(source = "good.price", target = "goodPrice")
    })
    public GoodInfoDTO from(GoodInfoBean good, GoodTypeBean type);
}

