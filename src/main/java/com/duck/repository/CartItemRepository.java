package com.duck.repository;

import com.duck.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart_Id(Long cartId);

    List<CartItem> findByIdIn(List<Long> itemIds);

    Optional<CartItem> findByCart_IdAndProduct_ProductId(Long cartId, Long productId);

    Optional<CartItem> findByCart_IdAndProduct_ProductIdAndSku_SkuId(Long cartId, Long productId, Long skuId);

    void deleteAllByCart_Id(Long cartId);

    @Modifying
    @Query(value = "DELETE FROM cart_items WHERE id IN (:ids)", nativeQuery = true)
    int deleteByIdIn(@Param("ids") List<Long> ids);

    Optional<CartItem> findByProduct_ProductIdAndSku_SkuId(Long productId, Long skuId);

    @Modifying
    @Query(value = "DELETE FROM cart_items WHERE product_id = :productId", nativeQuery = true)
    void deleteByProductId(@Param("productId") Long productId);

    @Modifying
    @Query(value = "UPDATE cart_items SET price = :price, subtotal = :price * quantity WHERE product_id = :productId", nativeQuery = true)
    void updatePriceAndSubtotal(@Param("price") BigDecimal price, @Param("productId") Long productId);
}
