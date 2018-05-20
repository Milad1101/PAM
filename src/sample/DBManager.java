package sample;

import java.sql.*;

public class DBManager {
    Connection con;
    public static String[] projectTypes = {"اتمتة","ذكاء","قواعد بيانات","ويب","خوارزميات","دراسة نظام","ألعاب","اختبار نظام","أمن معلومات","اندرويد","مقارنة نظم","وسائط متعددة","تصميم نظام","محاكاة"};


    public DBManager(){

       try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        con = makeConnection();


    }


    private Connection makeConnection(){
      try {
            Connection res = DriverManager.getConnection("jdbc:mysql://localhost/semProject?user=root&password=5314");
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addProject(Project project){

      try {
            String quiry = "insert into dataset values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement statement = con.prepareStatement(quiry);

            for(int i = 1 ; i <= 3 ;i++ ){
                if(project.getDept()[i-1])
                    statement.setInt(i,1);
                else
                    statement.setInt(i,0);
            }

            for(int i = 4 ; i <= 17 ;i++ ){
                if(project.getTypes()[i-4])
                    statement.setInt(i,1);
                else
                    statement.setInt(i,0);
            }

            statement.setString(18,project.getTitle());
            statement.setString(19,project.getProf());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
