package com.basilandember.controller;
import java.util.Map;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
@RestController
public class SystemController {
    private final MongoTemplate mongo;
    public SystemController(MongoTemplate mongo) { this.mongo=mongo; }
    @GetMapping("/api/health") public Map<String,String> health() { mongo.executeCommand(new Document("ping",1)); return Map.of("status","UP"); }
    @GetMapping("/api/admin/session") public Map<String,String> session() { return Map.of("role","ADMIN"); }
}
