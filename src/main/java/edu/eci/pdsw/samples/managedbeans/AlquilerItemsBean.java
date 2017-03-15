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
    private long cotizacion=0;
    String nombrec=null;
    long identificacionc=0 ;
    private int idit=0;
    private int dias=0;
    List<ItemRentado> rentados= new ArrayList<ItemRentado>();
    private ArrayList<ElMensaje> mensaje;
    
    public AlquilerItemsBean() {
        mensaje=new ArrayList<ElMensaje>();
        mensaje.add(new ElMensaje());
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
    
    public ArrayList<ElMensaje> getMensaje(){
        return mensaje;
    }
    
    public String getNombrec(){
        String nom = "";
        
        try {
            Cliente c = sp.consultarCliente(getIdentificacionc());
            if (c == null) {
                Logger.logMsg(Logger.ERROR, "El resultado de consultar cliente es null (" + getIdentificacionc() + ")");
            }
            nom = c != null ? c.getNombre() : "null";
        } catch (ExcepcionServiciosAlquiler ex) {
            java.util.logging.Logger.getLogger(AlquilerItemsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Logger.logMsg(Logger.DEBUG, "(AlquilerIteamsBean) Se consiguio el nombre " + nom);
        return nom;
    }
    
    public void setNombrec(String nombre){
        Logger.logMsg(Logger.DEBUG, "(AlquilerIteamsBean) Se coloco el nombre" + nombre);
        nombrec=nombre;
    }
    
    public long getIdentificacionc(){
        Logger.logMsg(Logger.DEBUG, "(AlquilerIteamsBean) Se consiguio la identificacion " + getCliente().getDocumento());
        return getCliente().getDocumento();
    }
    
    public void setIdentificacionc(long id){
        Logger.logMsg(Logger.DEBUG, "(AlquilerIteamsBean) Se coloco la identificacion " + id);
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
    
    public List<ItemRentado> getRentados() throws ExcepcionServiciosAlquiler{
        this.rentados=sp.consultarItemsCliente(identificacionc);
        return  rentados;
    }
    
    public void setRentados(List<ItemRentado> items){
        this.rentados=items;
    }
    
    public void cotizacion(){
        List<Item> disponibles = sp.consultarItemsDisponibles();
        boolean existe = false;
        for(int i=0; i< disponibles.size();i++){
            if(disponibles.get(i).getId() == idit){
                existe = true;
            } 
        }
        if(existe){
            try{
                setCotizacion(sp.consultarCostoAlquiler(idit, dias));
            }
            catch(Exception e){
            }
        }
        
    }
    
    public void agregarAlquiler() throws ExcepcionServiciosAlquiler{
        Logger.logMsg(Logger.DEBUG, "Se agrego alquiler");
        Cliente clienten = sp.consultarCliente(identificacionc);
        Item item = sp.consultarItem(idit);
        java.util.Date fecha = new Date();
        java.sql.Date sfecha = new java.sql.Date(fecha.getTime());
        long documento = clienten.getDocumento();
        sp.registrarAlquilerCliente(sfecha, documento, item, dias);
        setCotizacion(0);
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

    /**
     * @return the idit
     */
    public int getIdit() {
        return idit;
    }

    /**
     * @param idit the idit to set
     */
    public void setIdit(int idit) {
        this.idit = idit;
    }

    /**
     * @return the dias
     */
    public int getDias() {
        return dias;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(int dias) {
        this.dias = dias;
    }
    
    
    
     
    

}
