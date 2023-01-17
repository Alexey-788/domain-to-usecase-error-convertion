package com.alex788.ddd.usecase;

import com.alex788.ddd.domain.value_object.ProductId;
import com.alex788.ddd.domain.value_object.ProductName;
import com.alex788.ddd.usecase.error.AddProductToStoreUseCaseError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AddProductToStore {

    Either<AddProductToStoreUseCaseError, ProductId> execute(AddProductToStoreRequest request);

    @Getter
    @AllArgsConstructor
    class AddProductToStoreRequest {

        private final ProductName name;
    }
}
