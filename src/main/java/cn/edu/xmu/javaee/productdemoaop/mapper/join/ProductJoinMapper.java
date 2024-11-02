package cn.edu.xmu.javaee.productdemoaop.mapper.join;

import cn.edu.xmu.javaee.productdemoaop.mapper.join.po.ProductJoinPoSqlProvider;
import cn.edu.xmu.javaee.productdemoaop.mapper.join.po.ProductJoinPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductJoinMapper {

    @SelectProvider(type = ProductJoinPoSqlProvider.class, method = "getProductByNameWithJoin")
    @Results({
            @Result(column = "product_id", property = "id"),
            @Result(column = "sku_sn", property = "skuSn"),
            @Result(column = "name", property = "name"),
            @Result(column = "original_price", property = "originalPrice"),
            @Result(column = "weight", property = "weight"),
            @Result(column = "barcode", property = "barcode"),
            @Result(column = "unit", property = "unit"),
            @Result(column = "origin_place", property = "originPlace"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "creator_name", property = "creatorName"),
            @Result(column = "modifier_id", property = "modifierId"),
            @Result(column = "modifier_name", property = "modifierName"),
            @Result(column = "gmt_create", property = "gmtCreate"),
            @Result(column = "gmt_modified", property = "gmtModified"),
            @Result(column = "on_sale_price", property = "onSaleList.price"),
            @Result(column = "on_sale_quantity", property = "onSaleList.quantity"),
            @Result(column = "on_sale_max_quantity", property = "onSaleList.maxQuantity"),
            @Result(column = "other_product_id", property = "otherProduct.id"),
            @Result(column = "other_product_name", property = "otherProduct.name"),
            @Result(column = "other_product_original_price", property = "otherProduct.originalPrice"),
            @Result(column = "other_product_weight", property = "otherProduct.weight"),
            @Result(column = "other_product_barcode", property = "otherProduct.barcode"),
            @Result(column = "other_product_unit", property = "otherProduct.unit"),
            @Result(column = "other_product_origin_place", property = "otherProduct.originPlace")
    })
    List<ProductJoinPo> getProductByNameWithJoin(@Param("name") String name);
}

