package cn.edu.xmu.javaee.productdemoaop.mapper.jpa.entity;

import cn.edu.xmu.javaee.productdemoaop.controller.dto.ProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "goods_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 使用数据库自增
    private Long id;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 商品ID
     */
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * 类目ID
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 模板ID
     */
    @Column(name = "template_id")
    private Long templateId;

    /**
     * SKU编号
     */
    @Column(name = "sku_sn")
    private String skuSn;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 原始价格
     */
    @Column(name = "original_price")
    private Long originalPrice;

    /**
     * 商品重量
     */
    private Long weight;

    /**
     * 商品条形码
     */
    private String barcode;

    /**
     * 商品单位
     */
    private String unit;

    /**
     * 产地
     */
    @Column(name = "origin_place")
    private String originPlace;

    /**
     * 创建者ID
     */
    @Column(name = "creator_id")
    private Long creatorId;

    /**
     * 创建者名称
     */
    @Column(name = "creator_name")
    private String creatorName;

    /**
     * 修改者ID
     */
    @Column(name = "modifier_id")
    private Long modifierId;

    /**
     * 修改者名称
     */
    @Column(name = "modifier_name")
    private String modifierName;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 最后修改时间
     */
    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

    /**
     * 商品状态
     */
    private Integer status;

    /**
     * 佣金比率
     */
    @Column(name = "commission_ratio")
    private Integer commissionRatio;

    /**
     * 店铺物流ID
     */
    @Column(name = "shop_logistic_id")
    private Long shopLogisticId;

    /**
     * 免邮门槛
     */
    @Column(name = "free_threshold")
    private Long freeThreshold;

    /**
     * 其他商品信息
     */
    @Transient // 非数据库字段，表示这个字段不会映射到数据库
    private List<ProductDto> otherProduct;

    private static final long serialVersionUID = 1L;

}
