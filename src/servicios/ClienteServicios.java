/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kuroy
 */
public class ClienteServicios {
    public static Boolean guardarCliente(modelo.Cliente cliente, Connection connection) throws SQLException{
        String query = "INSERT INTO cliente (codigo, documento, nombres, apellidos) VALUES(?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareCall(query);
        ps.setInt(1, cliente.getCodigo());
        ps.setInt(2, cliente.getDocumento());
        ps.setString(3, cliente.getNombre());
        ps.setString(4, cliente.getApellido());
        return ps.execute();
    }
    
    public static modelo.Cliente buscar(modelo.Cliente cliente, Connection connection) throws SQLException{
        ResultSet rs = null;
        String query = "SELECT * FROM cliente WHERE codigo=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, cliente.getCodigo());
        rs = ps.executeQuery();
        if(rs.next()){
            Integer codigo = rs.getInt("codigo");
            Integer documento = rs.getInt("documento");
            String nombre = rs.getString("nombres");
            String apellido = rs.getString("apellidos");
            return new modelo.Cliente(codigo, documento, nombre, apellido);
        }
        return null;
    }
    
    public static Boolean actualizar(modelo.Cliente cliente, Integer codigo, Connection connection) throws SQLException{
        String query = "UPDATE cliente SET codigo=?, documento=?, nombres=?, apellidos=? WHERE codigo=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, cliente.getCodigo());
        ps.setInt(2, cliente.getDocumento());
        ps.setString(3, cliente.getNombre());
        ps.setString(4, cliente.getApellido());
        ps.setInt(5, codigo);
        return ps.executeUpdate()!=0;
    }
    
    public static Boolean eliminar(modelo.Cliente cliente, Connection connection) throws SQLException{
        String query = "DELETE FROM cliente WHERE codigo=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, cliente.getCodigo());
        return ps.execute();
    }
    
    public static ArrayList<modelo.Cliente> getAll(Connection connection) throws SQLException{
        ArrayList<modelo.Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM cliente";
        ResultSet rs = null;
        PreparedStatement ps = connection.prepareStatement(query);
        rs = ps.executeQuery();
        while(rs.next()){
            Integer codigo = rs.getInt("codigo");
            Integer documento = rs.getInt("documento");
            String nombre = rs.getString("nombres");
            String apellido = rs.getString("apellidos");
            clientes.add(new modelo.Cliente(codigo, documento, nombre, apellido));
        }
        return clientes;
    }
    
    public static ArrayList<modelo.Cliente> ordenar(Connection connection, String criterio) throws SQLException{
        ArrayList<modelo.Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM cliente ORDER BY codigo "+criterio;
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Integer codigo = rs.getInt("codigo");
            Integer documento = rs.getInt("documento");
            String nombre = rs.getString("nombres");
            String apellido = rs.getString("apellidos");
            clientes.add(new modelo.Cliente(codigo, documento, nombre, apellido));
        }
        return clientes;
    }
}
