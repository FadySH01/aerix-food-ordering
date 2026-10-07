package com.basilandember.repository;
import com.basilandember.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface FoodRepository extends MongoRepository<Food,String> {}
