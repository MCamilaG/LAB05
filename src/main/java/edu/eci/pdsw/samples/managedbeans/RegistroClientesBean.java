/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managedbeans;
import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.entities.ItemRentado;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquilerItemsStub;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private ServiciosAlquilerItemsStub mitienda;
    private String nombre;
    private long documento;
    private String telefono;
    private String direccion;
    private String email;
    private ArrayList<ElMensaje> mensaje;
    
public RegistroClientesBean() throws ExcepcionServiciosAlquiler {
      mitienda=new ServiciosAlquilerItemsStub();
      clientes=mitienda.consultarClientes();
      mensaje=new ArrayList<ElMensaje>() ;
      mensaje.add(new ElMensaje());
      nombre="";
      direccion="";
      telefono="";
      email="";
      documento=0;
}
    public List<Cliente> getClientes(){
        return clientes;
    }
    public Cliente getClienteseleccionado(){
        return clienteseleccionado;
    }
    public void setClienteseleccionado(Cliente clienteseleccionado){
        this.clienteseleccionado=clienteseleccionado;
    }
    public void setNombre(String s){
        nombre=s;
    }
    public void setDireccion(String s){
        direccion=s;
    }
    public void setTelefono(String s){
        telefono=s;
    }
    public void setEmail(String s){
        email=s;
    }
    public void setDocumento(long l){
        documento=l;
    }
    public String getNombre (){
        return nombre;
    }
    public long  getDocumento(){
        return documento;
    }
    public String getTelefono(){
        return telefono;
    }
    public String getDireccion(){
        return direccion;
    }
    public String getEmail(){
        return email;
    }
    public ArrayList<ElMensaje> getMensaje(){
        return mensaje;
    }
    public void agregarCliente(){
        if(!nombre.equals("")&&documento!=0&&!telefono.equals("")&&!direccion.equals("")&&!email.equals("")){
            ArrayList<ItemRentado> list=new ArrayList<ItemRentado>();
            Cliente cliente=new Cliente(nombre,documento,telefono,direccion,email,false,list);
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
}
