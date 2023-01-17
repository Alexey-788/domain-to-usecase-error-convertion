package com.alex788.ddd.usecase.access;

import com.alex788.ddd.domain.Product;

public interface ProductPersister {

    void persist(Product product);
}
