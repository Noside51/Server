import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.sql.*;

import java.net.InetSocketAddress;
import java.util.Map;

//For compiling on the shell on repl: Same on mac
//javac -cp sqlite-jdbc-3.23.1.jar: Main.java
//java -cp sqlite-jdbc-3.23.1.jar: Main

//Use for windows
//javac -cp sqlite-jdbc-3.23.1.jar; Main.java
class Main {

 public static void main(String[] args)throws IOException{
    (new Main()).init();
  }


  void print(Object o){ System.out.println(o);}
  void printt(Object o){ System.out.print(o);}

  void init() throws IOException{
   

    // create a port - our Gateway
    int port = 8500;
      
    //create the HTTPserver object
    HttpServer server = HttpServer.create(new InetSocketAddress(port),0);

    // create the database object
    Database db = new Database("jdbc:sqlite:NutritionValue.db");
    // Database db = new Database("jdbc:sqlite:NutritionValue_Demo.db");
    // Database db = new Database("jdbc:sqlite:test.db");
    
    

   // Add your  code here
    server.createContext("/", new RouteHandler("You are connected, but route not given or incorrect....") );


    String sql = "";

    // sql = "Select * from Test;";
    // server.createContext("/test", new RouteHandler(db,sql) );
    
    sql = "Select * from Food;";
    server.createContext("/foods", new RouteHandler(db,sql) );
    
    sql = "Select Food.Name, NutritionFacts.ServingSize, NutritionFacts.ServingsPerContainer, NutritionFacts.Calories, NutritionFacts.TotalFat, NutritionFacts.SaturatedFat, NutritionFacts.Cholesterol, NutritionFacts.Sodium, NutritionFacts.TotalCarbohydrate, NutritionFacts.DietaryFiber, NutritionFacts.Sugar, NutritionFacts.Protein, NutritionFacts.VitaminD, NutritionFacts.Calcium, NutritionFacts.Iron, NutritionFacts.Potassium From Food Inner Join NutritionFacts On Food.Name = NutritionFacts.Name;";
    server.createContext("/facts", new RouteHandler(db,sql) );

    sql = "Select Food.Name, NutritionFacts.ServingSize, NutritionFacts.ServingsPerContainer, NutritionFacts.Calories, PercentDailyValue.PerTotalFat, PercentDailyValue.PerSaturatedFat, PercentDailyValue.PerCholesterol, PercentDailyValue.PerSodium, PercentDailyValue.PerTotalCarbohydrate, PercentDailyValue.PerDietaryFiber, PercentDailyValue.PerProtein, PercentDailyValue.PerVitaminD, PercentDailyValue.PerCalcium, PercentDailyValue.PerIron, PercentDailyValue.PerPotassium From Food Inner Join NutritionFacts On Food.Name = NutritionFacts.Name Inner Join PercentDailyValue On NutritionFacts.Name = PercentDailyValue.Name;";
    server.createContext("/percents", new RouteHandler(db,sql) );

    sql = "Select Food.Name, Link.Links from Food Inner Join Link On Food.Name = Link.Name;";
    server.createContext("/links", new RouteHandler(db,sql) );


    //Start the server
    server.start();

    System.out.println("Server is listening on port "+port);
       
      
    }    
}


//BREAK//


// import java.io.IOException;
// import java.sql.*;
// //For compiling on the shell on repl: Same on mac
// //javac -cp sqlite-jdbc-3.23.1.jar: Main.java

// //Use for windows
// //javac -cp sqlite-jdbc-3.23.1.jar; ServerExample.java
// class Main {

//  public static void main(String[] args)throws IOException{
//     (new Main()).init();
//   }

//   void print(Object o){ System.out.println(o);}
//   void printt(Object o){ System.out.print(o);}

//   void init() {


//     String queryResult = "";

//     String sql = "Select * From NutritionValue_Demo;";

//     Database db = new Database("jdbc:sqlite:NutritionValue_Demo.db");

//     queryResult = db.runSQL(sql, "table-auto");


//     print(queryResult);

//   }    
// }