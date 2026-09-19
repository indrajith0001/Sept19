package ddt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class CURD_db {
	public static void main(String[] args) throws Exception {
		Driver d=new Driver();
		DriverManager.registerDriver(d);
		Connection con=DriverManager.getConnection("jdbc:mysql://49.249.29.4:3307/ninza_hrm", "root@%", "root");
		
		Statement st=con.createStatement();
		
//		st.execute("create table asdfghtt10 (name VARCHAR(20));");
//		st.execute("insert into asdfghtt10 values ('asdd');");
//		//Execute
//		boolean status=st.execute("select * from project;");
//		
//		if(status==true)
//			System.out.println("table_created");
//		else
//			System.out.println("table not created");
		
		ResultSet set=st.executeQuery("select * from project;");
		while(set.next()) {
			System.out.println("PID:" + set.getString(1)+"CreatedBy:"+set.getString(2));
		}
	}
}
