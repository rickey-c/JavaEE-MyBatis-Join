package cn.edu.xmu.javaee.productdemoaop.mapper.join;

import cn.edu.xmu.javaee.productdemoaop.mapper.join.po.ProductJoinPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJoinMapper {

    @Select("""
        SELECT 
            gp.id AS product_id,
            gp.shop_id,
            gp.goods_id,
            gp.category_id,
            gp.template_id,
            gp.sku_sn,
            gp.name,
            gp.original_price,
            gp.weight,
            gp.barcode,
            gp.unit,
            gp.origin_place,
            gp.creator_id AS product_creator_id,
            gp.creator_name AS product_creator_name,
            gp.modifier_id AS product_modifier_id,
            gp.modifier_name AS product_modifier_name,
            gp.gmt_create AS product_gmt_create,
            gp.gmt_modified AS product_gmt_modified,
            gp.status,
            gp.commission_ratio,
            gp.shop_logistic_id,
            gp.free_threshold,
            go.id AS onsale_id,
            go.shop_id AS onsale_shop_id,
            go.product_id AS onsale_product_id,
            go.price,
            go.begin_time,
            go.end_time,
            go.quantity,
            go.type,
            go.creator_id AS onsale_creator_id,
            go.creator_name AS onsale_creator_name,
            go.modifier_id AS onsale_modifier_id,
            go.modifier_name AS onsale_modifier_name,
            go.gmt_create AS onsale_gmt_create,
            go.gmt_modified AS onsale_gmt_modified,
            go.max_quantity,
            go.invalid
        FROM 
            goods_product gp
        JOIN 
            goods_onsale go ON gp.id = go.product_id
        WHERE 
            gp.goods_id = (
                SELECT goods_id 
                FROM goods_product 
                WHERE id = #{productId}
            )
            AND go.begin_time < NOW() 
            AND go.end_time > NOW()
        ORDER BY 
            CASE WHEN gp.id = #{productId} THEN 0 ELSE 1 END, -- 输入的 ID 排到最上面
            gp.id; -- 其他记录按 ID 排序
    """)
    List<ProductJoinPo> getProductsByProductIdWithJoin(@Param("productId") Long productId);
}
