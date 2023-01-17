package com.alex788.ddd.domain.error;

import com.alex788.ddd.common.error.DomainError;
import com.alex788.ddd.common.error.UseCaseError;

public interface AddProductToStoreError extends DomainError {

    UseCaseError accept(ErrorMapper errorMapper);

    class NameIsNotUniqueError implements AddProductToStoreError {

        @Override
        public UseCaseError accept(ErrorMapper errorMapper) {
            return errorMapper.visit(this);
        }
    }

    interface ErrorMapper {

        UseCaseError visit(NameIsNotUniqueError error);
    }
}
