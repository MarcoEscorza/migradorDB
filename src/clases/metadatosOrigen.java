/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marqu
 */
public class metadatosOrigen {
    
    conexionOrigen con;
    Connection conect;
    DatabaseMetaData  metadatosBD;
    
    static String baseDatos;
    static String tabla;
    static String tablaVista;
    static String procedimiento;
    
    public metadatosOrigen() throws SQLException
    {
        con = new conexionOrigen(); // instancia de la clase conexion
        conect = con.getConexion();
        metadatosBD=conect.getMetaData();  
    }
    
    public void setBase(String baseDatos)
    {
        this.baseDatos=baseDatos;
    }
    
    public String getBaseDatos() 
    {
        return baseDatos;
    }
    
    
    
    public void setTabla(String tabla){
        
        this.tabla=tabla;
    }
    
     
    public String getTabla1(){
    
        return tabla;
    }
    
     
    
    public void setTablaVista(String tablaVista)
    {
        this.tablaVista=tablaVista;
    }
    
    
    public String getTablaVista()
    {
        return tablaVista;
    }
    
    
    
    public void setProcedimiento(String procedimiento)
    {
        this.procedimiento=procedimiento;
    }
    
    
    public String getProcedimiento()
    {
        return procedimiento;
    }
    
    
    public DefaultTableModel listarBases() throws SQLException
    {
        DefaultTableModel salida=new DefaultTableModel();
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
    
    public DefaultTableModel listarTablas() throws SQLException
    {
        DefaultTableModel salida=new DefaultTableModel();
        String[] TABLE_TYPES = {"TABLE"}; // creo que es table
        ResultSet metadatos=this.metadatosBD.getTables(baseDatos, null,null,TABLE_TYPES);
        
        salida.addColumn("tableName");
        int iCuantos=0;
        while (metadatos.next()) 
        {
                iCuantos++;
                Object dato[] = new Object[1];
                dato[0]=metadatos.getObject("TABLE_NAME").toString();
                salida.addRow(dato);
        }
        salida.setColumnCount(1);
        return salida;
        
    }

    // metodo 1 
    //metodo de tablas 
    public DefaultTableModel listarLlavesPrimarias() throws SQLException{
        DefaultTableModel salida=new DefaultTableModel();
        ResultSet metadatos=this.metadatosBD.getPrimaryKeys(baseDatos,  null, tabla);
        
        salida.addColumn("columName");
        int iCuantos=0;
        while (metadatos.next()) {
                iCuantos++;
                @SuppressWarnings("MismatchedReadAndWriteOfArray")
                 Object dato[] = new Object[5];
                dato[0]=metadatos.getObject("COLUMN_NAME").toString();
                salida.addRow(dato);
        }
        salida.setColumnCount(1);
        return salida;
    }
    
    
    //metodo de tablas-Vistas
    public DefaultTableModel listarLlavesPrimariasV() throws SQLException{
        DefaultTableModel salida=new DefaultTableModel();
        ResultSet metadatos=this.metadatosBD.getPrimaryKeys(baseDatos,  null, tablaVista);
        
        salida.addColumn("columName");
        int iCuantos=0;
        while (metadatos.next()) {
                iCuantos++;
                @SuppressWarnings("MismatchedReadAndWriteOfArray")
                 Object dato[] = new Object[5];
                dato[0]=metadatos.getObject("COLUMN_NAME").toString();
                salida.addRow(dato);
        }
        salida.setColumnCount(1);
        return salida;
    }
    
    //metodo2
    //metodo de tablas 
    private boolean esLlavePrimaria(String atributo) throws SQLException {
        DefaultTableModel llavePrimaria=listarLlavesPrimarias();
        for(int i=0;i<llavePrimaria.getRowCount();i++){
            if(atributo.equals(llavePrimaria.getValueAt(i, 0).toString())){
                return true;
            }
        }
        
        return false;
    }
    
    //metodo de tablas -vistas
    private boolean esLlavePrimariaV(String atributo) throws SQLException {
        DefaultTableModel llavePrimaria=listarLlavesPrimariasV();
        for(int i=0;i<llavePrimaria.getRowCount();i++){
            if(atributo.equals(llavePrimaria.getValueAt(i, 0).toString())){
                return true;
            }
        }
        
        return false;
    }
    
    
    
    //metodo 3
    //Metodo de Tablas 
    public DefaultTableModel sel() throws SQLException
    {
        DefaultTableModel salida = new DefaultTableModel();
        ResultSet metadatos=this.metadatosBD.getColumns(baseDatos, null, tabla, null);
        
        //salida.addColumn("columName");
        salida.addColumn("columName");
        salida.addColumn("columnType");
        salida.addColumn("columnSize");
        salida.addColumn("columnDecimals");
        salida.addColumn("primaryKey");
        salida.addColumn("Nullable");
        
        int iCuantos=0;
        
        while (metadatos.next()) {
                iCuantos++;
                Object dato[] = new Object[6];
                dato[0]=metadatos.getObject("COLUMN_NAME").toString();
                dato[1]=metadatos.getObject("TYPE_NAME").toString();
                dato[2]=0;
                dato[3]=0;
                dato[4]=0;
                dato[5]=0;
                        
                
            if(metadatos.getObject("COLUMN_SIZE")!=null){
            
               dato[2]=metadatos.getObject("COLUMN_SIZE").toString(); 
                
            }
            
            
            if(metadatos.getObject("DECIMAL_DIGITS")!=null){
               
                dato[3]=metadatos.getObject("DECIMAL_DIGITS").toString();
            }
            
            
            
            if(metadatos.getObject("NULLABLE")!=null)
            {
                dato[5]=metadatos.getObject("NULLABLE").toString();
            }
            
            dato[4]=esLlavePrimaria(dato[0].toString());
            salida.addRow(dato);
        }
        salida.setColumnCount(6);
        

        return salida;
        
    }
    
    
    
    
    
    
    
    

     //metodo de tablas-vistas
    public DefaultTableModel listarVistas() throws SQLException{
        DefaultTableModel salida=new DefaultTableModel();
        String[] TABLE_TYPES = {"VIEW"};
        ResultSet metadatos=this.metadatosBD.getTables(baseDatos, null,null,TABLE_TYPES);

        salida.addColumn("tableName");
        int iCuantos=0;
        while (metadatos.next()) 
        {
                iCuantos++;
                Object dato[] = new Object[1];
                dato[0]=metadatos.getObject("TABLE_NAME").toString();
                salida.addRow(dato);
        }
        salida.setColumnCount(1);
        return salida;
    }
    
    
    
    //metodo de tablas-vistas
    
    public DefaultTableModel sel2() throws SQLException
    {
        DefaultTableModel salida = new DefaultTableModel();
        ResultSet metadatos=this.metadatosBD.getColumns(baseDatos, null, tablaVista, null);
        
        salida.addColumn("columName");
        salida.addColumn("columnType");
        salida.addColumn("columnSize");
        salida.addColumn("columnDecimals");
        salida.addColumn("primaryKey");
        salida.addColumn("Nullable");
        
        int iCuantos=0;
        
        while (metadatos.next()) {
                iCuantos++;
                Object dato[] = new Object[6];
                dato[0]=metadatos.getObject("COLUMN_NAME").toString();
                dato[1]=metadatos.getObject("TYPE_NAME").toString();
                dato[2]=0;
                dato[3]=0;
                dato[4]=0;
                dato[5]=0;
                        
                
            if(metadatos.getObject("COLUMN_SIZE")!=null){
            
               dato[2]=metadatos.getObject("COLUMN_SIZE").toString(); 
                
            }
            
            if(metadatos.getObject("DECIMAL_DIGITS")!=null){
               
                dato[3]=metadatos.getObject("DECIMAL_DIGITS").toString();
            }
            
            if(metadatos.getObject("NULLABLE")!=null)
            {
                dato[5]=metadatos.getObject("NULLABLE").toString();
            }
            
            dato[4]=esLlavePrimaria(dato[0].toString());
            salida.addRow(dato);
        }
        salida.setColumnCount(6);
        

        return salida;
        
    }
    
    
    
            
  
    
    
    
    
    
    public DefaultTableModel listarProcedimientos() throws SQLException{
        DefaultTableModel salida=new DefaultTableModel();
        ResultSet metadatos=this.metadatosBD.getProcedures(baseDatos,"dbo",null);
        
        salida.addColumn("procedureName");
        int iCuantos=0;
        while (metadatos.next()) {
                iCuantos++;
                Object dato[] = new Object[1];
                dato[0]=metadatos.getObject("PROCEDURE_NAME").toString();
                salida.addRow(dato);
        }
        salida.setColumnCount(1);
        return salida;
    }
    
    public DefaultTableModel listarAtributosProcedimiento() throws SQLException{
        DefaultTableModel salida=new DefaultTableModel();
        ResultSet metadatos=this.metadatosBD.getProcedureColumns(baseDatos,null,procedimiento,null);
        
        salida.addColumn("parameterName");
        salida.addColumn("parameterType");
        salida.addColumn("parameterDataType");
        salida.addColumn("parameterLength");
        salida.addColumn("parameterPrecision");
        
        int iCuantos=0;
        while (metadatos.next()) {
                iCuantos++;
                int tipoParametro=Integer.parseInt(metadatos.getObject("COLUMN_TYPE").toString());
                if(tipoParametro!=4 && tipoParametro!=5){
                    Object dato[] = new Object[5];
                    dato[0]=metadatos.getObject("COLUMN_NAME").toString();

                    switch(Integer.parseInt(metadatos.getObject("COLUMN_TYPE").toString())){
                        case 1:
                            dato[1]="E";
                        case 2:
                        case 3:
                            dato[1]="S";
                        default:
                            dato[1]="X";
                    }


                    dato[1]=metadatos.getObject("TYPE_NAME").toString();
                    dato[2]=metadatos.getObject("LENGTH").toString();
                    dato[3]=metadatos.getObject("PRECISION").toString();

                    salida.addRow(dato);
                }
                if(iCuantos==23){    
                    continue;
                }
        }
        salida.setColumnCount(5);
        return salida;
    }   
}
