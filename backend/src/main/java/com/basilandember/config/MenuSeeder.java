package com.basilandember.config;
import com.basilandember.repository.FoodRepository;
import org.slf4j.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
@Component @ConditionalOnProperty(name="app.seed-menu",havingValue="true")
public class MenuSeeder implements CommandLineRunner {
 private static final Logger log=LoggerFactory.getLogger(MenuSeeder.class); private final FoodRepository repository;
 public MenuSeeder(FoodRepository repository){this.repository=repository;}
 @Override public void run(String... args){try{if(repository.count()==0)repository.saveAll(MenuCatalog.starterMenu());}catch(DataAccessException e){log.warn("Atlas is temporarily unavailable. Menu requests will return an error until it reconnects.");}}
}
