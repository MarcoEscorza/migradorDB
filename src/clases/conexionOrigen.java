/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author marqu
 */
public class conexionOrigen 
{
    private static String hostname;
    private static String username;
    private static String password;
    
    private String driver;
    private String puerto = "3306";
    private String url;
    
    
    
    static Connection conexion=null;
    
    private static Statement stm;
    private static ResultSet rs;
    
    
    public conexionOrigen(){}
    
    
    public conexionOrigen(String hostname,String username, String password)
    {
        this.hostname=hostname;
        this.username=username;
        this.password=password;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public String getHostname() {
        return hostname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    
    
    
    
    public void mysql(String host)
    {
        //this.url = "jdbc:mysql://" + this.hostname + ":" + port + "/" + this.database + "?useSSL=false";
        //this.url = "jdbc:mysql://" + this.hostname + ":" + puerto + "/"  + "?useSSL=false&allowPublicKeyRetrieval=true";
        this.url = "jdbc:mysql://" + this.hostname + ":" + puerto + "/"  +"?allowMultiQueries=true";
        this.driver="com.mysql.jdbc.Driver";
        this.setConexion();
    }
    
    public void sql(String host)
    {
        //this.url="jdbc:sqlserver://" + this.hostname + ";databaseName= " + this.database;
        this.url="jdbc:sqlserver://" + this.hostname ;
        this.driver= "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        this.setConexion();
    }
    
    private void setConexion()
    {
        try 
        {
            Class.forName(driver);
            //recibe a los parametros y obtiene la conexion
            conexion=DriverManager.getConnection(url,username,password);
            stm = conexion.createStatement();
            
            
        }
        catch (ClassNotFoundException ex) 
        {
            JOptionPane.showMessageDialog(null, ex, "Error en la Conexión con la BD " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        }
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex, "Error en la Conexión con la BD " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, ex, "Error en la Conexión con la BD " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        }
        finally 
        {
            
        }
    }        
        
    public static Connection getConexion() 
    {
        return conexion;
    }
       
    
    public ResultSet executeQ(String query){
        
        Connection connection  = getConexion() ;
        Statement statement = null;
        ResultSet set = null;
        
        try {
            statement = connection.createStatement();
            set = statement.executeQuery(query);
            return set;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally { // Close in order: ResultSet, Statement, Connection.
            
            
        }
        
        return null;
    
    
    }
    
    public void executeUpdate(String update) {
       
        Connection connection  = getConexion() ;
        
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(update);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
        }
    }
    
}