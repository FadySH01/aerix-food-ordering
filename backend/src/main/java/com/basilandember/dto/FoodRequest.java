package com.basilandember.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public record FoodRequest(
    @NotBlank @Size(max=80) String name,
    @NotBlank @Size(max=500) String description,
    @NotBlank @Pattern(regexp="Pizza|Burgers|Bowls|Sides|Desserts|Drinks") String category,
    @NotNull @DecimalMin("0.01") @DecimalMax("9999.99") @Digits(integer=4, fraction=2) BigDecimal price,
    @NotBlank @Size(max=2000) @Pattern(regexp="https://[^\\s]+", message="must be an HTTPS image URL") String imageUrl,
    @NotNull Boolean available
) {}
