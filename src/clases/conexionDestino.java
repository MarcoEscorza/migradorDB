/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marqu
 */
public class conexionDestino {
    
    private static String hostname;
    private static String username;
    private static String password;
    private static String database;
    private String driver;
    public String port = "3306";
    private String url;
    
    DatabaseMetaData  metadatosBD;
    
    static Connection conexion=null;

    public String getHostname() {
        return hostname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
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

    public void setDatabase(String database) {
        this.database = database;
    }

    
    
    public conexionDestino() throws SQLException{
        
        
        this.hostname=hostname;
        this.username=username;
        this.password=password;
        this.database=database;

        
    }
    
    
    public void mysql()
    {
        //this.url = "jdbc:mysql://" + this.hostname + ":" + port + "/" + this.database + "?useSSL=false";
        this.url = "jdbc:mysql://" + this.hostname + ":" + port + "/"  +"?allowMultiQueries=true";
        this.driver="com.mysql.jdbc.Driver";
        this.setConexion();
    }
    
    public void sql()
    {
        //this.url="jdbc:sqlserver://" + this.hostname + ";databaseName= " + this.database;
        this.url="jdbc:sqlserver://" +"\\"+ this.hostname ;
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
    
    
    public DefaultTableModel listarBases() throws SQLException
    {
        DefaultTableModel salida=new DefaultTableModel();
        this.metadatosBD = conexion.getMetaData();
        
        ResultSet bases=this.metadatosBD.getCatalogs();
        int iCuantos=0;
        salida.addColumn("dbName");
        while(bases.next())
        {
            iCuantos++;
            Object dato[] = new Object[1];
            dato[0]=bases.getObject(1).toString();
            salida.addRow(dato);
        }
        salida.setColumnCount(1);
        return salida;
    }
    
    
    
    public ResultSet executeQ(String query){
        
        Connection connection  = getConexion() ;
        Statement statement = null;
        ResultSet set = null;
        
        try {
            statement = connection.createStatement();
            set = statement.executeQuery(query);
            JOptionPane.showMessageDialog(null, "Tablas generadas correctamente");
            return set;
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al ejecutar el script", "Error", 
                JOptionPane.ERROR_MESSAGE);   
        }
        
        return null;
    
    
    }
    
    public void executeUpdate(String update) {
       
        Connection connection  = getConexion() ;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeLargeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

}