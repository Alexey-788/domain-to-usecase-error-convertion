package com.alex788.ddd.domain.value_object;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ProductNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"Product name", "Name"})
    void from_WithValidValue_CreatesSuccessfully(String value) {
        Either<ProductName.Error, ProductName> productNameEth = ProductName.from(value);

        assertTrue(productNameEth.isRight());
        ProductName productName = productNameEth.get();
        assertEquals(value, productName.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\n \t"})
    void from_WithBlankValue_ReturnsError(String value) {
        Either<ProductName.Error, ProductName> productNameEth = ProductName.from(value);

        assertTrue(productNameEth.isLeft());
        assertInstanceOf(ProductName.BlankError.class, productNameEth.getLeft());
    }
}