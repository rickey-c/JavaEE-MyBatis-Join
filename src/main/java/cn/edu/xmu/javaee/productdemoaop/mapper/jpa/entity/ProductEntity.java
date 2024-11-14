package cn.edu.xmu.javaee.productdemoaop.mapper.jpa.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "goods_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    /**
     *
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 使用数据库自增
    private Long id;

    /**
     *
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     *
     */
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     *
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     *
     */
    @Column(name = "template_id")
    private Long templateId;

    /**
     *
     */
    @Column(name = "sku_sn")
    private String skuSn;

    /**
     *
     */
    private String name;

    /**
     *
     */
    @Column(name = "original_price")
    private Long originalPrice;

    /**
     *
     */
    private Long weight;

    /**
     *
     */
    private String barcode;

    /**
     *
     */
    private String unit;

    /**
     * 产地
     */
    @Column(name = "origin_place")
    private String originPlace;

    /**
     *
     */
    @Column(name = "creator_id")
    private Long creatorId;

    /**
     *
     */
    @Column(name = "creator_name")
    private String creatorName;

    /**
     *
     */
    @Column(name = "modifier_id")
    private Long modifierId;

    /**
     *
     */
    @Column(name = "modifier_name")
    private String modifierName;

    /**
     *
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     *
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     *
     */
    private Integer status;

    /**
     *
     */
    @Column(name = "commission_ratio")
    private Integer commissionRatio;

    /**
     *
     */
    @Column(name = "shop_logistic_id")
    private Long shopLogisticId;

    /**
     * 免邮门槛
     */
    @Column(name = "free_threshold")
    private Long freeThreshold;

    private static final long serialVersionUID = 1L;

}
