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
                    this.mostrarMensaje("Cliente registrado exitosamente", Boolean.TRUE);
                vista.cleanRegistro();
            }catch(SQLException e){
                this.mostrarMensaje("No se pudo registrar el cliente", Boolean.FALSE);
            }catch(Exception e){
                this.mostrarMensaje("Campos invalidos", Boolean.FALSE);
            }
        }
        
        //Buscar cliente
        if(ae.getSource() == vista.btnBus1){
            if(vista.txtCod2.getText().replaceAll(" ", "").isEmpty()){
                this.mostrarMensaje("Campo vacio", Boolean.FALSE);
                return;
            }
            Cliente cliente = new Cliente();
            Integer codigo = Integer.parseInt(vista.txtCod2.getText());
            cliente.setCodigo(codigo);
            try{
                cliente = ClienteServicios.buscar(cliente, conexion.getConnection());
                if(cliente!=null){
                    this.cargarCliente(cliente);
                }else{
                    this.mostrarMensaje("No se encontro un cliente con codigo: "+codigo+" en la base de datos", Boolean.FALSE);
                }
            }catch(SQLException e){
                this.mostrarMensaje("No se pudo buscar el cliente", Boolean.FALSE);
            }
        }
        
        //Habilita la vista del panel que contiene los componentes para realizar la busqueda
        if(ae.getSource() == vista.btnUpd){
            vista.habilitar();
        }
        
        //Actualiza el cliente
        if(ae.getSource() == vista.btnSave){
            if(vista.txtCod2.getText().replaceAll(" ", "").isEmpty()){
                this.mostrarMensaje("Campo vacio", Boolean.FALSE);
                return;
            }
            Integer codigo = Integer.parseInt(vista.txtCod2.getText());
            try{
                ClienteServicios.actualizar(getClienteBusqueda(), codigo, conexion.getConnection());
                this.mostrarMensaje("Cliente actualizado", Boolean.TRUE);
                vista.deshabilitar();
                vista.cleanBusqueda();
            }catch(SQLException e){
                this.mostrarMensaje("No se pudo registrar el cliente", Boolean.FALSE);
            }catch(Exception e){
                this.mostrarMensaje("Campos invalidos", Boolean.FALSE);
            }
        }
        
        if(ae.getSource() == vista.btnCancelar){
            vista.deshabilitar();
            vista.cleanBusqueda();
        }
        
        //Mostrar todos los clientes actuales en la base de datos
        if(ae.getSource() == vista.btnBus2){
            try{
                listar(ClienteServicios.getAll(conexion.getConnection()));
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        
        //Eliminar un cliente dado su codigo
        if(ae.getSource() == vista.btnDel){
            if(vista.txtCod2.getText().replaceAll(" ", "").isEmpty()){
                this.mostrarMensaje("Campo vacio", Boolean.FALSE);
                return;
            }
            try{
                Integer codigo = Integer.parseInt(vista.txtCod2.getText());
                Cliente cliente = new Cliente();
                cliente.setCodigo(codigo);
                if(ClienteServicios.eliminar(cliente, conexion.getConnection())){
                    this.mostrarMensaje("No se encontro un cliente con codigo: "+codigo+" en la base de datos", false);
                }else{
                    this.mostrarMensaje("El cliente con codigo: "+codigo+" ha sido eliminado de la base de datos", false);
                }
                vista.cleanBusqueda();
            }catch(SQLException e){
                this.mostrarMensaje("No se pudo eliminar el cliente", Boolean.FALSE);
            }catch(NumberFormatException e){
                this.mostrarMensaje("Codigo invalido", Boolean.FALSE);
            }
        }
        
        //Ordena ascendentemente la busqueda
        if(ae.getSource() == vista.btnOrderAsc){
            String criterio = "ASC";
            try{               
                listar(ClienteServicios.ordenar(conexion.getConnection(), criterio));
            }catch(SQLException e){
                System.err.println(e.getMessage());
            }
        }
        
        //Ordena descendentemente la busqueda
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
    
    private Cliente getClienteRegistro() throws Exception{
        Integer codigo = Integer.parseInt(vista.txtCod1.getText());
        Integer documento = Integer.parseInt(vista.txtDoc.getText());
        String nombres = vista.txtNom.getText();
        String apellidos = vista.txtApe.getText();
        return new Cliente(codigo, documento, nombres, apellidos);
    }
    
    private Cliente getClienteBusqueda() throws Exception{
        Integer codigo = Integer.parseInt(vista.txtCodA.getText());
        Integer documento = Integer.parseInt(vista.txtDocA.getText());
        String nombres = vista.txtNomA.getText();
        String apellidos = vista.txtApeA.getText();
        return new Cliente(codigo, documento, nombres, apellidos);
    }
    
    private void listar(java.util.ArrayList<Cliente> clientes){
        String clientesString = "";
        if(clientes.isEmpty()){
            this.mostrarMensaje("No existen clientes registrados en la base de datos", Boolean.TRUE);
            return;
        }
        for(Cliente cliente : clientes){
                    clientesString += cliente.toString()+"\n";
                }  
                vista.txtArea.setText(clientesString);
    }
    
    private void mostrarMensaje(String msg, Boolean x){
        if(x){
            JOptionPane.showMessageDialog(null, msg, "Operacion Exitosa!", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, msg, "Operacion Fallida!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Controlador c = new Controlador();
    }
}
