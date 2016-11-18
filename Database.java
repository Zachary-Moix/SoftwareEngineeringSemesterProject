package main;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class Database 
{
	private Connection conn;
	private String url;
	private String user;
	private String pass;
	private Statement stmt;
	private  ResultSet rs;
    private ResultSetMetaData rmd;
    
    public Database()
	{
		
	    Properties prop = new Properties();
	    FileInputStream fis = null;
		try 
		{
			fis = new FileInputStream("main/db.properties");
		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try 
	    {
			prop.load(fis);	
		} 
	    catch (IOException e)
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    url = prop.getProperty("url");
	    user = prop.getProperty("user");
	    pass = prop.getProperty("password");
  }
    
    public ArrayList<String> query(String query)
	  {
	    //Add your code here
		 ArrayList<String> res = new ArrayList<String>();
		 
		 try
		    {
		      
		      //Perform the Connection
		      conn = DriverManager.getConnection(url,user,pass);
		      
		    //Create a statement
		      stmt=conn.createStatement();
		      
		    //Execute a query
		      rs=stmt.executeQuery(query);  
		      
		      //Get metadata about the query
		      rmd = rs.getMetaData();
		      int i=1;
		      while(rs.next()) //true if more rows exist, false if not
		      {
		    	  res.add(rs.getString(i));
		    	  i++;
		      }
		      
		    } 
		    catch (SQLException e)
		    {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		 
		 
		return res; 
		 
	  }

	public boolean executeDML(String dml)
	  {  
		  try
		{
			//Perform the Connection
		      conn = DriverManager.getConnection(url,user,pass);
		      
		    //Create a statement
		      stmt=conn.createStatement();
		      
		      
		      //Execute a DML statement
		      stmt.execute(dml);
		      
		      
		      return true;
		  }
		  catch (SQLException e)
		    {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		  return false;
	  }
	
}
