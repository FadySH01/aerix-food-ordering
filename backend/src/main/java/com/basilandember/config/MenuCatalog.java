package com.basilandember.config;
import com.basilandember.model.Food;
import java.math.BigDecimal;
import java.util.List;
public final class MenuCatalog {
 private MenuCatalog() {}
 private static Food food(String id,String name,String description,String category,String price,String image,boolean available){return new Food(id,name,description,category,new BigDecimal(price),"https://images.unsplash.com/"+image+"?auto=format&fit=crop&w=1000&q=85",available);}
 public static List<Food> starterMenu(){return List.of(
  food("menu-01","Aerix Signature Burger","Flame-grilled beef, cheddar, crisp lettuce, tomato and Aerix smoky sauce in a toasted brioche bun.","Burgers","14.50","photo-1568901346375-23c9450c58cd",true),
  food("menu-02","Aerix Burrata Pizza","Slow-fermented crust, tomato sauce, creamy burrata and freshly torn basil. Simple ingredients, big flavor.","Pizza","18.00","photo-1579751626657-72bc17010498",true),
  food("menu-03","The Green Goddess","A colorful bowl of crisp greens, avocado, seasonal vegetables and grains, finished with a bright herb dressing.","Bowls","12.50","photo-1512621776951-a57141f2eefd",true),
  food("menu-04","Crispy Golden Fries","Golden-cut potatoes tossed with sea salt and herbs. A little crispy, a little fluffy, and made for sharing.","Sides","5.50","photo-1573080496219-bb080dd4f877",true),
  food("menu-05","Classic Margherita","Our signature tomato sauce, melted mozzarella and aromatic basil on a blistered, hand-stretched crust.","Pizza","15.00","photo-1513104890138-7c749659a591",true),
  food("menu-06","Harvest Bowl","Roasted seasonal vegetables, tender grains and fresh greens with a lemon-tahini dressing on the side.","Bowls","13.50","photo-1540420773420-3366772f4999",true),
  food("menu-07","Double Smash","Two seared beef patties, double cheddar, pickles and mustard mayo. For the days when one just isn't enough.","Burgers","17.00","photo-1550547660-d9450f859349",true),
  food("menu-08","Chocolate Ice Cream","Creamy chocolate ice cream finished with chocolate curls and a rich cocoa drizzle.","Desserts","6.50","photo-1563805042-7684c019e1cb",true),
  food("menu-09","Strawberry Ice Cream Sundae","Vanilla ice cream, fresh strawberries and berry sauce, finished with a crisp wafer.","Desserts","7.00","photo-1488900128323-21503983a07e",true),
  food("menu-10","Fresh Citrus Cooler","Fresh citrus, sparkling water and a little mint. Bright, refreshing and made to pair with your favorites.","Drinks","4.50","photo-1544145945-f90425340c7e",true),
  food("menu-11","Aerix Morning Coffee","Freshly brewed coffee for an easy morning start. Smooth, warming and served with milk on the side.","Drinks","4.00","photo-1495474472287-4d71bcdd2085",true),
  food("menu-12","Garden Side Salad","A light mix of crisp seasonal leaves and vegetables, served with our house vinaigrette.","Sides","6.00","photo-1512621776951-a57141f2eefd",false));}
}
