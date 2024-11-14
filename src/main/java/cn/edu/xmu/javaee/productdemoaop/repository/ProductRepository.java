package cn.edu.xmu.javaee.productdemoaop.repository;

import cn.edu.xmu.javaee.productdemoaop.mapper.jpa.entity.ProductEntity;
import jakarta.persistence.QueryHint;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long>{

    // 根据 name 查找商品
    @Query("SELECT p FROM ProductEntity p WHERE p.name = :name")
    List<ProductEntity> findByName(@Param("name") String name);

    // 根据 goodsId 和 id 查找其他同类商品
    @Query("SELECT p from ProductEntity p where p.goodsId=:goodsId AND p.id <> :id")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly",value = "true"))
    List<ProductEntity> findOtherProducts(@Param("goodsId") Long goodsId, @Param("id") Long id);

}
