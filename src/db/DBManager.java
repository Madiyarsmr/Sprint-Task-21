package db;

import model.Items;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/SprintTask02Database",
                    "postgres",
                    "postgres");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Items> getItems(){
        List<Items> itemsList = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM items ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Long id = resultSet.getLong("id"); // Long id = 1;
                String name = resultSet.getString("name");// String name = "Macbook Pro 14"
                String description = resultSet.getString("description"); // String description = "8 GB RAM 512 GB SSD M1 Pro";
                Double price = resultSet.getDouble("price"); // double price = 800000;

                Items item = new Items();
                item.setId(id);
                item.setName(name);
                item.setDescription(description);
                item.setPrice(price);
                itemsList.add(item);
            }
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return itemsList;
    }

    public static void addItem(Items item){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO items(name,description, price)" +
                    "values (?,?,?)");
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setDouble(3, item.getPrice());

            int rows = statement.executeUpdate();
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Items getItemById(Long id){
        Items item = null;
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM items WHERE id = ?");
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Double price = resultSet.getDouble("price");
                item = new Items();
                item.setName(name);
                item.setDescription(description);
                item.setPrice(price);
            }
            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }
}
