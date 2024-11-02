package cn.edu.xmu.javaee.productdemoaop.mapper.join.po;

import org.apache.ibatis.jdbc.SQL;

public class ProductJoinPoSqlProvider {
    public String getProductByNameWithJoin(String name) {
        return new SQL() {{
            SELECT("p.id AS product_id");
            SELECT("p.sku_sn");
            SELECT("p.name");
            SELECT("p.original_price");
            SELECT("p.weight");
            SELECT("p.barcode");
            SELECT("p.unit");
            SELECT("p.origin_place");
            SELECT("p.creator_id");
            SELECT("p.creator_name");
            SELECT("p.modifier_id");
            SELECT("p.modifier_name");
            SELECT("p.gmt_create");
            SELECT("p.gmt_modified");
            SELECT("os.price AS on_sale_price");
            SELECT("os.quantity AS on_sale_quantity");
            SELECT("os.max_quantity AS on_sale_max_quantity");
            SELECT("op.id AS other_product_id");
            SELECT("op.name AS other_product_name");
            SELECT("op.original_price AS other_product_original_price");
            SELECT("op.weight AS other_product_weight");
            SELECT("op.barcode AS other_product_barcode");
            SELECT("op.unit AS other_product_unit");
            SELECT("op.origin_place AS other_product_origin_place");

            FROM("goods_product p");
            INNER_JOIN("goods_onsale os ON p.id = os.product_id AND os.begin_time <= NOW() AND os.end_time > NOW()");
            INNER_JOIN("goods_product op ON op.goods_id = p.goods_id");

            WHERE("p.name = #{name}");
        }}.toString();
    }

}
