package cn.edu.xmu.javaee.productdemoaop.util;

import cn.edu.xmu.javaee.productdemoaop.controller.dto.AdminProductDto;
import cn.edu.xmu.javaee.productdemoaop.controller.dto.OnSaleDto;
import cn.edu.xmu.javaee.productdemoaop.controller.dto.ProductDto;
import cn.edu.xmu.javaee.productdemoaop.controller.vo.ProductVo;
import cn.edu.xmu.javaee.productdemoaop.dao.bo.OnSale;
import cn.edu.xmu.javaee.productdemoaop.dao.bo.Product;
import cn.edu.xmu.javaee.productdemoaop.dao.bo.User;
import cn.edu.xmu.javaee.productdemoaop.mapper.generator.po.OnSalePo;
import cn.edu.xmu.javaee.productdemoaop.mapper.generator.po.ProductPo;
import cn.edu.xmu.javaee.productdemoaop.mapper.jpa.entity.ProductEntity;
import cn.edu.xmu.javaee.productdemoaop.mapper.manual.po.ProductAllPo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CloneFactory {

    public static OnSale copy(OnSale target, OnSalePo source) {
        target = OnSale.builder().id(source.getId()).beginTime(source.getBeginTime()).endTime(source.getEndTime()).price(source.getPrice()).quantity(source.getQuantity())
                .maxQuantity(source.getMaxQuantity()).creator(User.builder().id(source.getCreatorId()).name(source.getCreatorName()).build()).modifier(User.builder().id(source.getModifierId()).name(source.getModifierName()).build())
                .gmtCreate(source.getGmtCreate()).gmtModified(source.getGmtModified()).build();
        return target;
    }

    public static Product copy(Product target, ProductPo source){
        target = Product.builder().id(source.getId()).name(source.getName()).barcode(source.getBarcode()).originalPrice(source.getOriginalPrice()).originPlace(source.getOriginPlace())
                .skuSn(source.getSkuSn()).weight(source.getWeight()).unit(source.getUnit()).commissionRatio(source.getCommissionRatio()).freeThreshold(source.getFreeThreshold())
                .creator(User.builder().id(source.getCreatorId()).name(source.getCreatorName()).build()).
                modifier(User.builder().id(source.getModifierId()).name(source.getModifierName()).build()).gmtCreate(source.getGmtCreate()).gmtModified(source.getGmtModified()).build();
        return target;
    }

    public static Product copy(Product target, ProductAllPo source){
        target = Product.builder().id(source.getId()).name(source.getName()).barcode(source.getBarcode()).originalPrice(source.getOriginalPrice()).originPlace(source.getOriginPlace())
                .skuSn(source.getSkuSn()).weight(source.getWeight()).unit(source.getUnit()).commissionRatio(source.getCommissionRatio()).freeThreshold(source.getFreeThreshold())
                .creator(User.builder().id(source.getCreatorId()).name(source.getCreatorName()).build()).
                modifier(User.builder().id(source.getModifierId()).name(source.getModifierName()).build()).gmtCreate(source.getGmtCreate()).gmtModified(source.getGmtModified()).build();
        Long id = target.getId();
        target.setOnSaleList(source.getOnSaleList().stream().map(o->CloneFactory.copy(new OnSale(), o)).collect(Collectors.toList()));
        target.setOtherProduct(source.getOtherProduct().stream().filter(o->!(o.getId().equals(id))).map(o -> CloneFactory.copy(new Product(), o)).collect(Collectors.toList()));
        return target;
    }

    public static ProductPo copy(ProductPo target, Product source){
        target.setId(source.getId());
        target.setName(source.getName());
        target.setBarcode(source.getBarcode());
        target.setOriginalPrice(source.getOriginalPrice());
        target.setUnit(source.getUnit());
        target.setCommissionRatio(source.getCommissionRatio());
        target.setWeight(source.getWeight());
        target.setFreeThreshold(source.getFreeThreshold());
        target.setSkuSn(source.getSkuSn());
        target.setStatus(source.getStatus());
        target.setOriginPlace(source.getOriginPlace());
        if(null != source.getCreator()) {
            target.setCreatorId(source.getCreator().getId());
            target.setCreatorName(source.getCreator().getName());
        }
        if(null != source.getModifier()) {
            target.setModifierId(source.getModifier().getId());
            target.setModifierName(source.getModifier().getName());
        }
        target.setGmtModified(source.getGmtModified());
        target.setGmtCreate(source.getGmtCreate());
        return  target;
    }

    public static ProductDto copy(ProductDto target, Product source){
            target = ProductDto.builder().id(source.getId()).skuSn(source.getSkuSn()).name(source.getName()).unit(source.getUnit()).originalPrice(source.getOriginalPrice()).barcode(source.getBarcode())
            .weight(source.getWeight()).originPlace(source.getOriginPlace()).build();
        if (source.getOnSaleList().size() == 1){
            OnSale onSale = source.getOnSaleList().get(0);
            target.setPrice(onSale.getPrice());
            target.setQuantity(onSale.getQuantity());
            target.setMaxQuantity(onSale.getMaxQuantity());
        }

        target.setOtherProduct(source.getOtherProduct().stream().map(o->CloneFactory.copy(new ProductDto(), o)).collect(Collectors.toList()));
        return target;
    }

    public static AdminProductDto copy(AdminProductDto target, Product source){
        target = AdminProductDto.builder().id(source.getId()).skuSn(source.getSkuSn()).name(source.getName()).unit(source.getUnit()).originalPrice(source.getOriginalPrice()).barcode(source.getBarcode())
                .weight(source.getWeight()).originPlace(source.getOriginPlace()).creator(source.getCreator()).modifier(source.getModifier())
                .gmtCreate(source.getGmtCreate()).gmtModified(source.getGmtModified()).build();
        target.setOnsaleList(source.getOnSaleList().stream().map(o -> CloneFactory.copy(new OnSaleDto(), o)).collect(Collectors.toList()));
        target.setOtherProduct(source.getOtherProduct().stream().map(o->CloneFactory.copy(new ProductDto(), o)).collect(Collectors.toList()));
        return target;
    }

    public static OnSaleDto copy(OnSaleDto target, OnSale source){
        target = OnSaleDto.builder().id(source.getId()).beginTime(source.getBeginTime()).endTime(source.getEndTime()).price(source.getPrice()).quantity(source.getQuantity()).maxQuantity(source.getMaxQuantity())
                        .modifier(source.getModifier()).creator(source.getCreator()).gmtModified(source.getGmtModified()).gmtCreate(source.getGmtCreate()).build();
        return target;
    }

    public static Product copy(Product target, ProductVo source){
        target=Product.builder().name(source.getName()).barcode(source.getBarcode()).unit(source.getUnit()).originalPrice(source.getOriginalPrice()).originPlace(source.getOriginPlace()).weight(source.getWeight()).build();
        return target;
    }

    public static Product copy(Product target, ProductEntity source) {
        // 创建 target 对象并填充字段
        target = Product.builder()
                .id(source.getId()) // 商品 ID
                .skuSn(source.getSkuSn()) // SKU 编号
                .name(source.getName()) // 商品名称
                .originalPrice(source.getOriginalPrice()) // 原始价格
                .weight(source.getWeight()) // 商品重量
                .barcode(source.getBarcode()) // 商品条形码
                .unit(source.getUnit()) // 商品单位
                .originPlace(source.getOriginPlace()) // 产地
                .commissionRatio(source.getCommissionRatio()) // 佣金比率
                .freeThreshold(source.getFreeThreshold()) // 免邮门槛
                .status(source.getStatus().byteValue()) // 商品状态
                .creator(User.builder()
                        .id(source.getCreatorId()) // 创建者ID
                        .name(source.getCreatorName()) // 创建者姓名
                        .build())
                .modifier(User.builder()
                        .id(source.getModifierId()) // 修改者ID
                        .name(source.getModifierName()) // 修改者姓名
                        .build())
                .gmtCreate(source.getGmtCreate()) // 创建时间
                .gmtModified(source.getGmtModified()) // 最后修改时间
                .build();
        return target;
    }

}
