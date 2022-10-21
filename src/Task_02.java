import com.sun.jdi.connect.spi.Connection;

import java.io.*;
import java.sql.*;

public class Task_02 {

    private static final String URL = "jdbc:mysql://localhost:3306/myjoinsdb2?useSSL=false";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "Ruslan2703"; // "root";

    public static void main(String[] args) throws IOException {

        registerDriver();

        FileWriter writerSimple = new FileWriter("task_02.txt");

        writerSimple.write("INSERT INTO MainTask_06(surnamesN, nameN, phone, rating, age )  VALUES(?,?,?,?,?)\n" +
                "SELECT * FROM MainTask_06");

        writerSimple.close();
        // Создали объект файла по ссылке
        File f1 = new File("task_02.txt");
        // Считываем текстовый файл в пакете
        BufferedReader br = new BufferedReader(new FileReader(f1));

        String temp;
        int count = 0;
        while ((temp = br.readLine()) != null) {
            count += 1 ;
            if (count == 1){
                setAllTable (temp,"Фамилия", "Имя","0917612343", 7, 35);
            } else {
                getAllTable(temp);
            }

        }
        br.close();
    }


    private static void setAllTable (String InsertNew, String surnamesN, String nameN, String phone, double rating, int age ) {
        Connection connection = null;
        //Теперь используем PreparedStatement (из пакета java.sql)
        PreparedStatement statement = null;

        try {
            connection = (Connection) DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = ((java.sql.Connection) connection).prepareStatement(InsertNew);

            //Вызываем у statement методы для вставки в таблицу данных соответствующего типа
            // Указываем индекс столбца и передаем данные для этой строки
            statement.setString(1, surnamesN);
            statement.setString(2, nameN);
            statement.setString(3, phone);
            statement.setDouble(4, rating);
            statement.setInt(5, age);

            // запускаем на выполнение данный стейтмент
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
                statement.close();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void getAllTable(String GET_ALL) {
        Connection connection = null;
        PreparedStatement statement_01 = null;
        try {
            connection= (Connection) DriverManager.getConnection(URL, LOGIN, PASSWORD);
            // В statement передаем константу
            statement_01 = ((java.sql.Connection) connection).prepareStatement(GET_ALL);

            // Загоняем в ResultSet полученные данные
            // executeQuery уже без параметров, тк. в синтаксисе выше мы передали содержание запроса
            ResultSet resultSet_1 = statement_01.executeQuery();

            while (resultSet_1.next()) {
                String surnamesN  = resultSet_1.getString("surnamesN");
                String nameN  = resultSet_1.getString("nameN");
                String phone  = resultSet_1.getString("phone");
                double rating  = resultSet_1.getDouble("rating");
                int age = resultSet_1.getInt("age");

                System.out.println( surnamesN + " " + nameN + " " + phone + " " + rating + " " + age );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement_01.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void registerDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Class.forName("com.mysql.jdbc.Driver"); // deprecated
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
