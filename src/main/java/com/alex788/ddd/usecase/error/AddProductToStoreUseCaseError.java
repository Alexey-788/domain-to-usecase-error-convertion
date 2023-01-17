package com.alex788.ddd.usecase.error;

import com.alex788.ddd.common.error.UseCaseError;

public interface AddProductToStoreUseCaseError extends UseCaseError {

    class NameIsNotUniqueError implements AddProductToStoreUseCaseError {
    }
}
