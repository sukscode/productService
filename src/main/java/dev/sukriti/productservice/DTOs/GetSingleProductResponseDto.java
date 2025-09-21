package dev.sukriti.productservice.DTOs;

import dev.sukriti.productservice.Models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSingleProductResponseDto {
    private Product product;
}
