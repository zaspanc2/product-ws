package com.fripop.product.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDto {

    private String name;

    private BigDecimal price;

    private Boolean active;
}
