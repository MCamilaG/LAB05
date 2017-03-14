/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managedbeans;
import com.sun.media.jfxmedia.logging.Logger;
import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.entities.ItemRentado;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquilerItemsStub;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author juan
 */

@ManagedBean(name = "RegistroClientesBean")
@SessionScoped
public class RegistroClientesBean {
    private List<Cliente> clientes;
    private Cliente clienteseleccionado;
    private ServiciosAlquiler mitienda;
    private String nombre;
    private long documento;
    private String telefono;
    private String direccion;
    private String email;
    private ArrayList<ElMensaje> mensaje;
    
public RegistroClientesBean() throws ExcepcionServiciosAlquiler {
      mitienda = ServiciosAlquiler.getInstance();
      //clientes=mitienda.consultarClientes();
      mensaje=new ArrayList<ElMensaje>() ;
      mensaje.add(new ElMensaje());
      nombre="";
      direccion="";
      telefono="";
      email="";
      documento=0;
}
    public List<Cliente> getClientes() throws ExcepcionServiciosAlquiler{
        return mitienda.consultarClientes();
    }
    public Cliente getClienteseleccionado(){
        return clienteseleccionado;
    }
    public void setClienteseleccionado(Cliente clienteseleccionado){
        Logger.logMsg(Logger.DEBUG, "Se coloco el cliente seleccionado" + clienteseleccionado);
        this.clienteseleccionado=clienteseleccionado;
    }
    public void setNombre(String s){
        Logger.logMsg(Logger.DEBUG, "Se coloco el nombre" + s);
        nombre=s;
    }
    public void setDireccion(String s){
        Logger.logMsg(Logger.DEBUG, "Se coloco direccion" + s);
        direccion=s;
    }
    public void setTelefono(String s){
        Logger.logMsg(Logger.DEBUG, "Se coloco el telefono" + s);
        telefono=s;
    }
    public void setEmail(String s){
        Logger.logMsg(Logger.DEBUG, "Se coloco el email" + s);
        email=s;
    }
    public void setDocumento(long l){
        Logger.logMsg(Logger.DEBUG, "Se coloco el documento" + l);
        documento=l;
    }
    public String getNombre (){
        Logger.logMsg(Logger.DEBUG, "El nombre del cliente es: " + nombre);
        return nombre;
    }
    public long  getDocumento(){
        Logger.logMsg(Logger.DEBUG, "El documento del cliente es: " + documento);
        return documento;
    }
    public String getTelefono(){
        Logger.logMsg(Logger.DEBUG, "El telefono del cliente es: " + telefono);
        return telefono;
    }
    public String getDireccion(){
        Logger.logMsg(Logger.DEBUG, "El direccion del cliente es: " + direccion);
        return direccion;
    }
    public String getEmail(){
        Logger.logMsg(Logger.DEBUG, "El email del cliente es: " + email);
        return email;
    }
    public ArrayList<ElMensaje> getMensaje(){
        return mensaje;
    }
    public void agregarCliente(){
        if(!nombre.equals("")&&documento!=0&&!telefono.equals("")&&!direccion.equals("")&&!email.equals("")){
            ArrayList<ItemRentado> list=new ArrayList<ItemRentado>();
            Cliente cliente=new Cliente(nombre,documento,telefono,direccion,email);
            try{
                mitienda.registrarCliente(cliente);
                clientes=mitienda.consultarClientes();
            }
            catch(Exception e){}
            nombre="";
            direccion="";
            telefono="";
            email="";
            documento=0;
        }
        
    }
    public String cambiarVista(long id) {
        this.documento = id;
        Logger.logMsg(Logger.DEBUG, "*Id del usuario: "+documento);
        return "RegistroClienteItem.xhtml";
    }
}
