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

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "sku_sn")
    private String skuSn;

    @Column(name = "name")
    private String name;

    @Column(name = "original_price")
    private Long originalPrice;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "unit")
    private String unit;

    @Column(name = "origin_place")
    private String originPlace;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "modifier_id")
    private Long modifierId;

    @Column(name = "modifier_name")
    private String modifierName;

    @Column(name = "gmt_create")
    private LocalDateTime gmtCreate;

    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;

    @Column(name = "status")
    private Integer status;

    @Column(name = "commission_ratio")
    private Integer commissionRatio;

    @Column(name = "shop_logistic_id")
    private Long shopLogisticId;

    @Column(name = "free_threshold")
    private Long freeThreshold;

    @Transient
    private List<ProductDto> otherProduct;

    private static final long serialVersionUID = 1L;
}
