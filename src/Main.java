import java.sql.*;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/myjoinsdb?useSSL=false";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "Ruslan2703"; // "root";
    // Присваиваем запрос константе GET_ALL
    private static final String GET_TASK_01 = "SELECT phone, address FROM myjoinsdb.Employee JOIN myjoinsdb.workingStatus ON id = EmployeeID";
    private static final String GET_TASK_02 = "SELECT dateBirth, phone FROM myjoinsdb.Employee JOIN myjoinsdb.workingStatus ON statusF = 'не женат' and id = EmployeeID";
    private static final String GET_TASK_03 = "SELECT phone , dateBirth  FROM myjoinsdb.Employee JOIN myjoinsdb.workingPosition ON jobTitle = 'менеджер' and id = EmployeeIDPosition JOIN myjoinsdb.workingStatus ON id = EmployeeID";

    public static void main(String[] args) {
        registerDriver();

        Connection connection = null;
        PreparedStatement statement_01 = null;
        PreparedStatement statement_02 = null;
        PreparedStatement statement_03 = null;


        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            // В statement передаем константу
            statement_01 = connection.prepareStatement(GET_TASK_01);
            statement_02 = connection.prepareStatement(GET_TASK_02);
            statement_03 = connection.prepareStatement(GET_TASK_03);

            // Загоняем в ResultSet полученные данные
            // executeQuery уже без параметров, тк. в синтаксисе выше мы передали содержание запроса
            ResultSet resultSet_1 = statement_01.executeQuery();
            ResultSet resultSet_2 = statement_02.executeQuery();
            ResultSet resultSet_3 = statement_03.executeQuery();

            System.out.println("1) Получите контактные данные сотрудников (номера телефонов, место жительства):");
            while (resultSet_1.next()) {
                String address  = resultSet_1.getString("address");
                String phone  = resultSet_1.getString("phone");

                System.out.println( phone + " " + address );
            }

            System.out.println("2) Получите информацию о дате рождения всех холостых сотрудников и их номера:");
            while (resultSet_2.next()) {
                String phone  = resultSet_2.getString("phone");
                Date dateBirth = resultSet_2.getDate("dateBirth");

                System.out.println(phone + " " + dateBirth);
            }

            System.out.println("3) Получите информацию обо всех менеджерах компании: дату рождения и номер телефона:");
            while (resultSet_3.next()) {
                Date dateBirth = resultSet_3.getDate("dateBirth");
                String phone  = resultSet_3.getString("phone");


                System.out.println(dateBirth + " " + phone );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement_01.close();
                statement_02.close();
                statement_03.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Class.forName("com.mysql.jdbc.Driver"); // deprecated
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
