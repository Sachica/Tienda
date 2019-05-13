/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kuroy
 */
public class Conexion {
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URLDATABASE = "jdbc:mysql://localhost/tienda";
    private final String USER = "root";
    private final String PASSWORD = "";
    private static Connection cnx = null;
    private static PreparedStatement ps  = null;

    public Conexion() throws SQLException, ClassNotFoundException{
        cnx = conectar();
    }
    
    /**
     * Se conecta a la base de datos y me retorna la connection
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    private Connection conectar() throws SQLException, ClassNotFoundException{
        if(cnx == null){
            try{
                Class.forName(DRIVER);
                cnx = DriverManager.getConnection(URLDATABASE, USER, PASSWORD);
            }catch(SQLException e){
                throw new SQLException(e);
            }catch(ClassNotFoundException c){
                throw new ClassCastException(c.getMessage());
            }
        }
        return cnx;
    }
    
    /**
     * Sirve para realizar una consulta en general, con el param "table" me ubico
     * en la tabla que quiero buscar y con el param "id" busco en la tabla
     * el elemento o elementos con dicho "id"(nunca he usado este metodo desde que lo cree xd pero sirve)
     * @param table
     * @param id
     * @return
     * @throws SQLException 
     */
    public ResultSet consulta(String table, String id)throws SQLException{
        ps = cnx.prepareStatement("SELECT * FROM "+table+" WHERE id="+id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs;
        }
        return null;
    }
  
    /**
     * Retorna la conexion
     * @return 
     */
    public Connection getConnection(){
        return cnx;
    }
    
    /**
     * Cierra la conexion
     */
    public static void cerrar(){
        if(cnx!=null){
            try{
                cnx.close();
            }catch(Exception e){}
        }
    }
}
