package com.alex788.ddd.usecase.scenario;

import com.alex788.ddd.domain.Product;
import com.alex788.ddd.domain.error.AddProductToStoreError;
import com.alex788.ddd.domain.invariant.ProductNameIsUnique;
import com.alex788.ddd.domain.value_object.ProductId;
import com.alex788.ddd.domain.value_object.ProductName;
import com.alex788.ddd.usecase.AddProductToStore;
import com.alex788.ddd.usecase.access.ProductPersister;
import com.alex788.ddd.usecase.error.AddProductToStoreUseCaseError;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddProductToStoreUseCaseTest {

    TestProductPersister persister = new TestProductPersister();
    TestProductIdGenerator idGenerator = new TestProductIdGenerator();
    AddProductToStoreError.ErrorMapper errorMapper = new AddProductToStoreUseCase.ErrorMapper();

    @Test
    void execute_InInvariant_StoresProduct() {
        AddProductToStoreUseCase useCase = new AddProductToStoreUseCase(idGenerator, persister, productNameIsUnique(), errorMapper);
        AddProductToStore.AddProductToStoreRequest request = new AddProductToStore.AddProductToStoreRequest(
                ProductName.from("Product Name").get()
        );

        Either<AddProductToStoreUseCaseError, ProductId> productIdEth = useCase.execute(request);

        assertTrue(productIdEth.isRight());
        ProductId productId = productIdEth.get();
        assertEquals(1, persister.store.size());
        Product product = persister.store.get(0);
        assertEquals(idGenerator.id, product.getId());
        assertEquals(productId, product.getId());
        assertEquals(request.getName(), product.getName());
    }

    @Test
    void execute_WithNotUniqueName_ReturnsError() {
        AddProductToStoreUseCase useCase = new AddProductToStoreUseCase(idGenerator, persister, productNameNotUnique(), errorMapper);
        AddProductToStore.AddProductToStoreRequest request = new AddProductToStore.AddProductToStoreRequest(
                ProductName.from("Product Name").get()
        );

        Either<AddProductToStoreUseCaseError, ProductId> productIdEth = useCase.execute(request);

        assertTrue(productIdEth.isLeft());
        assertInstanceOf(AddProductToStoreUseCaseError.NameIsNotUniqueError.class, productIdEth.getLeft());
    }

    ProductNameIsUnique productNameIsUnique() {
        return name -> true;
    }

    ProductNameIsUnique productNameNotUnique() {
        return name -> false;
    }

    static class TestProductIdGenerator implements ProductId.Generator {

        public ProductId id = new ProductId(5);

        @Override
        public ProductId generate() {
            return id;
        }
    }

    static class TestProductPersister implements ProductPersister {

        public List<Product> store = new ArrayList<>();

        @Override
        public void persist(Product product) {
            store.add(product);
        }
    }
}