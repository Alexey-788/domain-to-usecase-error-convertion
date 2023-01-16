package com.alex788.ddd.domain;

import com.alex788.ddd.domain.error.AddProductToStoreError;
import com.alex788.ddd.domain.invariant.ProductNameIsUnique;
import com.alex788.ddd.domain.value_object.ProductId;
import com.alex788.ddd.domain.value_object.ProductName;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    TestProductIdGenerator idGenerator = new TestProductIdGenerator();

    @Test
    void addToStore_InInvariant_ReturnsProduct() {
        ProductName productName = productName();
        Either<AddProductToStoreError, Product> productEth = Product.addToStore(idGenerator,
                productNameIsUnique(),
                productName);

        assertTrue(productEth.isRight());
        Product product = productEth.get();
        assertEquals(idGenerator.id, product.getId());
        assertEquals(productName, product.getName());
    }

    @Test
    void addToStore_WithNotUniqueName_ReturnsError() {
        ProductName productName = productName();
        Either<AddProductToStoreError, Product> productEth = Product.addToStore(idGenerator,
                productNameNotUnique(),
                productName);

        assertTrue(productEth.isLeft());
        assertInstanceOf(AddProductToStoreError.NameIsNotUniqueError.class, productEth.getLeft());
    }

    ProductName productName() {
        return ProductName.from("Product name").get();
    }

    ProductNameIsUnique productNameIsUnique() {
        return name -> true;
    }

    ProductNameIsUnique productNameNotUnique() {
        return name -> false;
    }

    static class TestProductIdGenerator implements ProductId.Generator {

        public final ProductId id = new ProductId(
                new Random(System.currentTimeMillis()).nextLong()
        );

        @Override
        public ProductId generate() {
            return id;
        }
    }
}