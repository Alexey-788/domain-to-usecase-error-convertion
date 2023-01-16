package com.alex788.ddd.domain.value_object;

import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductName {

    private final String value;

    public static Either<Error, ProductName> from(String name) {
        if (name.isBlank()) {
            return Either.left(new BlankError());
        }

        return Either.right(new ProductName(name));
    }

    public interface Error {
    }

    public static class BlankError implements Error {
    }
}
