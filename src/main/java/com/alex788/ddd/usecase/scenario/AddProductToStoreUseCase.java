package com.alex788.ddd.usecase.scenario;

import com.alex788.ddd.domain.Product;
import com.alex788.ddd.domain.error.AddProductToStoreError;
import com.alex788.ddd.domain.invariant.ProductNameIsUnique;
import com.alex788.ddd.domain.value_object.ProductId;
import com.alex788.ddd.usecase.AddProductToStore;
import com.alex788.ddd.usecase.access.ProductPersister;
import com.alex788.ddd.usecase.error.AddProductToStoreUseCaseError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddProductToStoreUseCase implements AddProductToStore {

    private final ProductId.Generator idGenerator;
    private final ProductPersister persister;
    private final ProductNameIsUnique productNameIsUnique;
    private final AddProductToStoreError.ErrorMapper errorMapper;

    @Override
    public Either<AddProductToStoreUseCaseError, ProductId> execute(AddProductToStoreRequest request) {
        Either<AddProductToStoreError, Product> productEth =
                Product.addToStore(idGenerator, productNameIsUnique, request.getName());

        if (productEth.isLeft()) {
            AddProductToStoreError domainError = productEth.getLeft();
            return Either.left(mapToUseCaseError(domainError));
        }

        Product product = productEth.get();
        persister.persist(product);
        return Either.right(product.getId());
    }


    private AddProductToStoreUseCaseError mapToUseCaseError(AddProductToStoreError domainError) {
        return (AddProductToStoreUseCaseError) domainError.accept(errorMapper);
    }

    protected static class ErrorMapper implements AddProductToStoreError.ErrorMapper {

        @Override
        public AddProductToStoreUseCaseError visit(AddProductToStoreError.NameIsNotUniqueError error) {
            return new AddProductToStoreUseCaseError.NameIsNotUniqueError();
        }
    }
}
