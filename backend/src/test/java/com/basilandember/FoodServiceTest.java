package com.basilandember;

import com.basilandember.dto.FoodRequest;
import com.basilandember.model.Food;
import com.basilandember.repository.FoodRepository;
import com.basilandember.service.FoodService;
import com.basilandember.controller.CartController;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodServiceTest {
    FoodRepository repository; FoodService service; Food food;
    @BeforeEach void setup() {
        repository=mock(FoodRepository.class); service=new FoodService(repository);
        food=new Food("one","Burger","Grilled burger","Burgers",new BigDecimal("14.50"),"https://example.com/food.jpg",true);
        when(repository.findById("one")).thenReturn(Optional.of(food));
    }
    @Test void updatePreservesIdAndSavesAllFields() {
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));
        var updated=service.update("one",new FoodRequest("New burger","New description","Burgers",new BigDecimal("16.25"),"https://example.com/new.jpg",false));
        assertEquals("one",updated.getId()); assertEquals("New burger",updated.getName()); assertFalse(updated.isAvailable());
        assertEquals(new BigDecimal("16.25"),updated.getPrice()); verify(repository).save(food);
    }
    @Test void missingFoodReturnsNotFound() { assertEquals(404,assertThrows(ResponseStatusException.class,() -> service.get("absent")).getStatusCode().value()); }
    @Test void quoteUsesDatabasePricesAndExactDecimalMath() {
        var result=new CartController(service).quote(new CartController.Request(List.of(new CartController.Item("one",3))));
        assertEquals(new BigDecimal("43.50"),result.total()); assertEquals(3,result.items().getFirst().quantity());
    }
    @Test void unavailableItemsCannotBeQuoted() {
        food.setAvailable(false);
        assertEquals(409,assertThrows(ResponseStatusException.class,() -> new CartController(service).quote(new CartController.Request(List.of(new CartController.Item("one",1))))).getStatusCode().value());
    }
    @Test void duplicateCartLinesAreRejected() {
        assertEquals(400,assertThrows(ResponseStatusException.class,() -> new CartController(service).quote(new CartController.Request(List.of(new CartController.Item("one",1),new CartController.Item("one",1))))).getStatusCode().value());
    }
    @Test void searchCategoryAvailabilityAndSortWorkTogether() {
        var second=new Food("two","Small burger","Grilled","Burgers",new BigDecimal("10.00"),"https://example.com/a.jpg",true);
        when(repository.findAll()).thenReturn(List.of(food,second,new Food("three","Pizza","Basil","Pizza",new BigDecimal("18.00"),"https://example.com/b.jpg",false)));
        var result=service.list("BURGER","Burgers",true,"price-asc");
        assertEquals(List.of("two","one"),result.stream().map(Food::getId).toList());
    }
    @Test void deleteChecksExistence() { service.delete("one"); verify(repository).delete(food); }
}
