package com.basilandember.controller;

import com.basilandember.service.FoodService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    public record Item(@NotBlank String foodId, @Min(1) @Max(99) int quantity) {}
    public record Request(@NotEmpty @Size(max=50) List<@Valid Item> items) {}
    public record Line(String foodId, String name, int quantity, BigDecimal unitPrice, BigDecimal subtotal) {}
    public record Quote(List<Line> items, BigDecimal total, String currency) {}
    private final FoodService service;
    public CartController(FoodService service) { this.service=service; }
    @PostMapping("/quote") public Quote quote(@Valid @RequestBody Request request) {
        Set<String> ids=new HashSet<>(); List<Line> lines=new ArrayList<>();
        for (Item item:request.items()) {
            if (!ids.add(item.foodId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Duplicate cart item.");
            var food=service.get(item.foodId());
            if (!food.isAvailable()) throw new ResponseStatusException(HttpStatus.CONFLICT,food.getName()+" is currently unavailable. Remove it from your cart.");
            lines.add(new Line(food.getId(),food.getName(),item.quantity(),food.getPrice(),food.getPrice().multiply(BigDecimal.valueOf(item.quantity()))));
        }
        return new Quote(lines,lines.stream().map(Line::subtotal).reduce(BigDecimal.ZERO,BigDecimal::add),"USD");
    }
}
