package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectDB {
	public static Connection getConnection() {
	String url = "jdbc:mysql://localhost:3306/"; 
        String dbName = "loan_system_db"; 
        String strTimeZone = "?serverTimezone=Asia/Ho_Chi_Minh";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root"; 
        String password = "";
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url+dbName+strTimeZone,userName,password);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
	}
        
        public static ResultSet executeQuery (String query)
        {
            ResultSet resultSet=null;
            PreparedStatement preparedStatement=null;
            try {
                preparedStatement = ConnectDB.getConnection().prepareStatement(
                        query);
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                 resultSet= preparedStatement.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return resultSet;
        }
        public static int insertNewItem (String tableName, String paramaters, Object[] obj) throws SQLException
        {
        	// e.g: query ="insert into [table-name]"
        	// e.g: params ="firstname, lastname, address,..."
 
        	String completeQueryStatement ="insert into "+tableName+" ("+paramaters+") values (";
        	int paramsLength = paramaters.split(",").length; 
        	for (int i= 0;i<paramsLength;i++)
        	{
        		if(i!=paramsLength-1) completeQueryStatement+="?,";
        		else completeQueryStatement+="?";
        	}
        	completeQueryStatement+=")";
        
        	PreparedStatement preparedStatement=null;
        	preparedStatement = ConnectDB.getConnection().prepareStatement(completeQueryStatement);
        	for (int i= 0;i<paramsLength;i++)
        	{
        		preparedStatement.setObject(i+1, obj[i]);
        	}
        	int result =preparedStatement.executeUpdate();
        	return result;
        	
        }
        public static int updateItem (String tableName, String paramaters, Object[] obj) throws SQLException
        {
        	// e.g: query ="insert into [table-name]"
        	// e.g: params ="firstname, lastname, address,..."
        	
        	String completeQueryStatement ="update "+tableName+" set ";
        	int paramsLength = paramaters.split(",").length; 
        	String[] temp =paramaters.split(",");
        	for (int i= 0;i<paramsLength;i++)
        	{
        		if(i!=paramsLength-1)
        			completeQueryStatement+=temp[i]+"=? , ";
        		else completeQueryStatement+=temp[i]+"=? where id=? ";
        	}
        	System.out.print(completeQueryStatement);
        	
        	PreparedStatement preparedStatement=null;
        	preparedStatement = ConnectDB.getConnection().prepareStatement(completeQueryStatement);
        	for (int i= 0;i<=paramsLength;i++)
        	{
        		preparedStatement.setObject(i+1, obj[i]);
        	}
        	int result =preparedStatement.executeUpdate();
        	return result;
        }

		public static int removeItem(String tableName, String paramaters, Object[] obj) throws SQLException {
			// e.g: query ="insert into [table-name]"
        	// e.g: params ="firstname, lastname, address,..."
        	
        	String completeQueryStatement ="delete from "+tableName+" where id=? ";
        	int paramsLength = paramaters.split(",").length; 
        	
        	PreparedStatement preparedStatement=null;
        	preparedStatement = ConnectDB.getConnection().prepareStatement(completeQueryStatement);
        	for (int i= 0;i<paramsLength;i++)
        	{
        		preparedStatement.setObject(i+1, obj[i]);
        	}
        	int result =preparedStatement.executeUpdate();
        	return result;
		}
//		private String hashPassword(String plainTextPassword){
//			return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
//		}
}