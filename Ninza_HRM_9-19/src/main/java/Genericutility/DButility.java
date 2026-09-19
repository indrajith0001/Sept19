package Genericutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class DButility {
	Connection conn;
	public Connection getDBconnection(String url, String un, String pwd) throws Exception{
		Driver d=new Driver();
		DriverManager.registerDriver(d);
		return DriverManager.getConnection(url, un, pwd);
	}	
	public void close() throws Exception {
		conn.close();
		System.out.println("Connection closed");
	}
}
