package dev.sukriti.productservice.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProductsRequestDto {
    private int numberOfProducts;
    private int offset;
}
