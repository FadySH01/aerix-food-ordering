package com.basilandember.service;

import com.basilandember.dto.FoodRequest;
import com.basilandember.model.Food;
import com.basilandember.repository.FoodRepository;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FoodService {
    private final FoodRepository repository;
    public FoodService(FoodRepository repository) {
        this.repository=repository;
    }
    public List<Food> list(String search,String category,Boolean available,String sort) {
        String query=search==null?"":search.strip().toLowerCase(Locale.ROOT);
        Comparator<Food> comparator=switch(sort) {
            case "price-asc" -> Comparator.comparing(Food::getPrice);
            case "price-desc" -> Comparator.comparing(Food::getPrice).reversed();
            case "name" -> Comparator.comparing(Food::getName);
            default -> Comparator.comparing(Food::getId);
        };
        return repository.findAll().stream()
            .filter(f -> query.isEmpty()||(f.getName()+" "+f.getDescription()).toLowerCase(Locale.ROOT).contains(query))
            .filter(f -> category==null||category.isBlank()||f.getCategory().equalsIgnoreCase(category))
            .filter(f -> available==null||f.isAvailable()==available)
            .sorted(comparator.thenComparing(Food::getName)).toList();
    }
    public Food get(String id) {
        return repository.findById(id).orElseThrow(() -> notFound());
    }
    public Food create(FoodRequest request) {
        Food food=copy(new Food(),request); food.setId(UUID.randomUUID().toString());
        return repository.save(food);
    }
    public Food update(String id,FoodRequest request) {
        Food food=copy(get(id),request);
        return repository.save(food);
    }
    public void delete(String id) {
        repository.delete(get(id));
    }
    private ResponseStatusException notFound(){return new ResponseStatusException(HttpStatus.NOT_FOUND,"Food item not found.");}
    private Food copy(Food food,FoodRequest request) {
        food.setName(request.name().strip()); food.setDescription(request.description().strip());
        food.setCategory(request.category()); food.setPrice(request.price());
        food.setImageUrl(request.imageUrl().strip()); food.setAvailable(request.available()); return food;
    }
}
