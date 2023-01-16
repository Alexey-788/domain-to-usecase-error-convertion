package com.alex788.ddd.domain;

import com.alex788.ddd.domain.error.AddProductToStoreError;
import com.alex788.ddd.domain.invariant.ProductNameIsUnique;
import com.alex788.ddd.domain.value_object.ProductId;
import com.alex788.ddd.domain.value_object.ProductName;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {

    private ProductId id;
    private ProductName name;

    public static Either<AddProductToStoreError, Product> addToStore(
            ProductId.Generator idGenerator,
            ProductNameIsUnique nameIsUnique,
            ProductName name
    ) {
        if (!nameIsUnique.check(name)) {
            return Either.left(new AddProductToStoreError.NameIsNotUniqueError());
        }

        return Either.right(new Product(
                idGenerator.generate(),
                name
        ));
    }
}
