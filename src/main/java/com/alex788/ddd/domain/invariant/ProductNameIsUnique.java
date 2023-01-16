package com.alex788.ddd.domain.invariant;

import com.alex788.ddd.domain.value_object.ProductName;

public interface ProductNameIsUnique {

    boolean check(ProductName name);
}
