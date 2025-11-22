import java.sql.*;
import java.util.Scanner;

public class Main{
     private static final String url = "jdbc:mysql://127.0.0.1:3306/mydb";
    private static final String username = "AP_Assignment";
    private static final String password = "1991";
    public static void main(String[] args){
       try{
           Class.forName(("com.mysql.cj.jdbc.Driver"));
       }
       catch (ClassNotFoundException e){
           System.out.println(e.getMessage());
       }

       try(Connection connection = DriverManager.getConnection(url, username,password)){
           Scanner scanner = new Scanner(System.in);
           String query = "insert into students (name , age , marks) values (?, ?, ?)";
           PreparedStatement preparedStatement = connection.prepareStatement(query);

           while (true){
               System.out.print("Enter name: ");
               String name = scanner.nextLine();
               System.out.print("Enter age: ");
               int age = scanner.nextInt();
               scanner.nextLine();
               System.out.print("Enter marks: ");
               double marks = scanner.nextDouble();
               scanner.nextLine();
               System.out.print("Enter more data(Y/N): ");
               String choice = scanner.nextLine();
               preparedStatement.setString(1,name);
               preparedStatement.setInt(2,age);
               preparedStatement.setDouble(3,marks);
               preparedStatement.addBatch();
               if(choice.toUpperCase().equals("N")){
                   break;
               }
           }
           int[] arr = preparedStatement.executeBatch();
           int n = arr.length;
           for(int i = 0 ; i < n ; i++){
               if(arr[i] == 0){
                   System.out.printf("Error in query Execution for query %d\n", i +1);
               }
               else{
                   System.out.printf("query %d ran successfully\n",i+1);
               }
           }


       }
       catch (SQLException e){
           System.out.println(e.getMessage());
       }
    }
}