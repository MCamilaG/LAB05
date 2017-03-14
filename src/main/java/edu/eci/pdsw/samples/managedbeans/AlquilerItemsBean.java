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
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import com.sun.media.jfxmedia.logging.Logger;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author 2106913
 */
@ManagedBean(name = "AlquilerItems")
@RequestScoped
public class AlquilerItemsBean implements Serializable {
    @ManagedProperty(value="#{RegistroClientesBean}")
    private RegistroClientesBean cliente;    
    ServiciosAlquiler sp = ServiciosAlquiler.getInstance();
    long multas=0;
    long cotizacion=0;
    String nombrec=null;
    long identificacionc=0 ;
    int idit=0;
    
    public AlquilerItemsBean() {
    }
    
    @PostConstruct
    public void init(){
        if(cliente!=null){
            Logger.logMsg(Logger.DEBUG, "El numero de cliente actual es " + cliente.getDocumento());
            this.identificacionc=cliente.getDocumento();
        } else {
            Logger.logMsg(Logger.ERROR, "El cliente es nulo en metodo init de " + this.getClass().getName());
        }
    }
    
    public String getNombrec(){
        String nom = "";
        
        try {
            nom=sp.consultarCliente(getIdentificacionc()).getNombre();
        } catch (ExcepcionServiciosAlquiler ex) {
            java.util.logging.Logger.getLogger(AlquilerItemsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.logMsg(Logger.DEBUG, "Se consiguio el nombre " + nom);
        return nom;
    }
    
    public void setNombrec(String nombre){
        Logger.logMsg(Logger.DEBUG, "Se coloco el nombre" + nombre);
        nombrec=nombre;
    }
    
    public long getIdentificacionc(){
        Logger.logMsg(Logger.DEBUG, "Se consiguio la identificacion " + getCliente().getDocumento());
        return getCliente().getDocumento();
    }
    
    public void setIdentificacionc(long id){
        Logger.logMsg(Logger.DEBUG, "Se coloco la identificacion " + id);
        identificacionc=id;
    }
    
    public long getCotizacion(){
        return  cotizacion;
    }
    public void setCotizacion(long valor){
        cotizacion=valor;
    }
    
    public long getMultas(){
         try{
            multas =0;
            Cliente c = sp.consultarCliente(identificacionc);
            ArrayList<ItemRentado> items = c.getRentados();
            for(int i =0; i< items.size(); i++){
                java.util.Date fecha = new Date();
                java.sql.Date sfecha = new java.sql.Date(fecha.getTime());
                multas = multas + sp.consultarMultaAlquiler(items.get(i).getItem().getId() , sfecha);
            }
        }
        catch(Exception e){
        }
        Logger.logMsg(Logger.DEBUG, "Se consiguio la multa " + multas);
        return  multas;
    }
    public void setMultas(long multa){
        Logger.logMsg(Logger.DEBUG, "Se coloco la multa " + multa);
        multas=multa;
    }
    
    public List<Cliente> getClientes() throws ExcepcionServiciosAlquiler{
        List<Cliente> clientes= new ArrayList<Cliente>();
        clientes = getCliente().getClientes();
        return  clientes;
    }
    
    public List<ItemRentado> getRentados() throws ExcepcionServiciosAlquiler{
        List<ItemRentado> rentados= new ArrayList<ItemRentado>();
        rentados=sp.consultarItemsCliente(identificacionc);
        return  rentados;
    }
    
    public void cotizacion(int id,int dias){
        List<Item> disponibles = sp.consultarItemsDisponibles();
        boolean existe = false;
        for(int i=0; i< disponibles.size();i++){
            if(disponibles.get(i).getId() == id){
                existe = true;
            } 
        }
        if(existe){
            try{
                cotizacion =sp.consultarCostoAlquiler(id, dias);
            }
            catch(Exception e){
            }
        }
        
    }
    
    public void agregarAlquiler(int id, int dias) throws ExcepcionServiciosAlquiler{
        Cliente clienten = sp.consultarCliente(identificacionc);
        Item item = sp.consultarItem(id);
        java.util.Date fecha = new Date();
        java.sql.Date sfecha = new java.sql.Date(fecha.getTime());
        long documento = clienten.getDocumento();
        sp.registrarAlquilerCliente(sfecha, documento, item, dias);
        cotizacion=0;
    }

    /**
     * @return the cliente
     */
    public RegistroClientesBean getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(RegistroClientesBean cliente) {
        this.cliente = cliente;
    }
    
    
    
     
    

}
