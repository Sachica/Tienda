/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import servicios.*;
import modelo.Cliente;
import vista.ViewCliente;
/**
 *
 * @author kuroy
 */
public class Controlador implements java.awt.event.ActionListener{
    Conexion conexion;
    ViewCliente vista;

    public Controlador() throws SQLException, ClassNotFoundException {
        conexion = new Conexion();
        vista = new ViewCliente();
        vista.initListeners(this);
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //Registrar cliente
        if(ae.getSource() == vista.btnRegis){
            try{
                ClienteServicios.guardarCliente(this.getClienteRegistro(), conexion.getConnection());
                vista.cleanRegistro();
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        
        //Buscar cliente
        if(ae.getSource() == vista.btnBus1){
            Cliente cliente = new Cliente();
            Integer codigo = Integer.parseInt(vista.txtCod2.getText());
            cliente.setCodigo(codigo);
            try{
                cliente = ClienteServicios.buscar(cliente, conexion.getConnection());
                if(cliente!=null){
                    this.cargarCliente(cliente);
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro un cliente con codigo: "+codigo+" en la base de datos", "Operacion Fallida!", JOptionPane.ERROR_MESSAGE);
                }
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        
        //Actualizar cliente
        if(ae.getSource() == vista.btnUpd){
            Integer codigo = Integer.parseInt(vista.txtCod2.getText());
            try{
                ClienteServicios.actualizar(getClienteBusqueda(), codigo, conexion.getConnection());
                vista.cleanBusqueda();
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        
        //Mostrar todos los clientes actuales en la base de datos
        if(ae.getSource() == vista.btnBus2){
            try{
                listar(ClienteServicios.getAll(conexion.getConnection()));
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        
        if(ae.getSource() == vista.btnDel){
            try{
                Integer codigo = Integer.parseInt(vista.txtCod2.getText());
                Cliente cliente = new Cliente();
                cliente.setCodigo(codigo);
                ClienteServicios.eliminar(cliente, conexion.getConnection());
                vista.cleanBusqueda();
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        
        if(ae.getSource() == vista.btnOrderAsc){
            String criterio = "ASC";
            try{               
                listar(ClienteServicios.ordenar(conexion.getConnection(), criterio));
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        
        if(ae.getSource() == vista.btnOrderDesc){
            String criterio = "DESC";
            try{
                listar(ClienteServicios.ordenar(conexion.getConnection(), criterio));
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
    }
    
    private void cargarCliente(Cliente cliente){
        vista.txtCodA.setText(""+cliente.getCodigo());
        vista.txtDocA.setText(""+cliente.getDocumento());
        vista.txtNomA.setText(""+cliente.getNombre());
        vista.txtApeA.setText(""+cliente.getApellido());
    }
    
    private Cliente getClienteRegistro(){
        Integer codigo = Integer.parseInt(vista.txtCod1.getText());
        Integer documento = Integer.parseInt(vista.txtDoc.getText());
        String nombres = vista.txtNom.getText();
        String apellidos = vista.txtApe.getText();
        return new Cliente(codigo, documento, nombres, apellidos);
    }
    
    private Cliente getClienteBusqueda(){
        Integer codigo = Integer.parseInt(vista.txtCodA.getText());
        Integer documento = Integer.parseInt(vista.txtDocA.getText());
        String nombres = vista.txtNomA.getText();
        String apellidos = vista.txtApeA.getText();
        return new Cliente(codigo, documento, nombres, apellidos);
    }
    
    private void listar(java.util.ArrayList<Cliente> clientes){
        String clientesString = "";
        for(Cliente cliente : clientes){
                    clientesString += cliente.toString()+"\n";
                }  
                vista.txtArea.setText(clientesString);
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Controlador c = new Controlador();
    }
}
