package com.basilandember.controller;

import com.basilandember.dto.FoodRequest;
import com.basilandember.model.Food;
import com.basilandember.service.FoodService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    private final FoodService service;
    public FoodController(FoodService service) { this.service=service; }
    @GetMapping public List<Food> list(@RequestParam(required=false) String search, @RequestParam(required=false) String category,
        @RequestParam(required=false) Boolean available, @RequestParam(defaultValue="featured") String sort) { return service.list(search,category,available,sort); }
    @GetMapping("/{id}") public Food get(@PathVariable String id) { return service.get(id); }
    @PostMapping public ResponseEntity<Food> create(@Valid @RequestBody FoodRequest request) {
        Food food=service.create(request); return ResponseEntity.created(URI.create("/api/foods/"+food.getId())).body(food);
    }
    @PutMapping("/{id}") public Food update(@PathVariable String id, @Valid @RequestBody FoodRequest request) { return service.update(id,request); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable String id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
