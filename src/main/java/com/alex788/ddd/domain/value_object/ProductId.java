package com.alex788.ddd.domain.value_object;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ProductId {

    private final long value;

    public interface Generator {

        ProductId generate();
    }
}
