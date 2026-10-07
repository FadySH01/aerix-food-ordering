package com.basilandember.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document("foods")
public class Food {
    @Id private String id;
    private String name;
    private String description;
    private String category;
    @Field(targetType = FieldType.DECIMAL128) private BigDecimal price;
    private String imageUrl;
    private boolean available;

    public Food() {}
    public Food(String id, String name, String description, String category, BigDecimal price, String imageUrl, boolean available) {
        this.id=id; this.name=name; this.description=description; this.category=category;
        this.price=price; this.imageUrl=imageUrl; this.available=available;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id=id; }
    public String getName() { return name; }
    public void setName(String name) { this.name=name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description=description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category=category; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price=price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl=imageUrl; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available=available; }
}
