/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.ItemRentado;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquilerItemsStub;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * C1:crear un cliente sin parametros de entrada. Tipo:Normal. Resultado: se creo el cliente y esta disponible para futuros alquileres
 * C2:crear un cliente con todos los atributos a excepcion de si esta vetado y del item rentado. Tipo:Normal. Resultado:se creo el cliente con todos los atributos menos si esta vetado y el item rentado y esta disponible para futuros alquileres
 * C3:crear un cliente con todos los atributos. Tipo:Normal. Resultado: se creo el cliente con todos los atributos y esta disponible para futuros alquileres
 * C4:crear un cliente que ya esta registrado.Tipo:Error.Resultado:excepcion
 * 
 */
public class ClientesTest {

    public ClientesTest() {
    }
    
    @Before
    public void setUp() {
    }
    
  
    @Test
    public void additems1() throws ExcepcionServiciosAlquiler{
    	   
    }
    
    @Test
    public void testC1(){
        Cliente cliente=new Cliente();
         ServiciosAlquilerItemsStub alquiler=new ServiciosAlquilerItemsStub();
         try{
             alquiler.registrarCliente(cliente);
             assertEquals("El cliente no exixste",alquiler.consultarClientes().size(),1);
         }catch(Exception e){}
    }
    
    @Test
    public void testC2(){
        String nombre="Maria Camila Gomez Ramirez";
        long documento=1019132336;
        String telefono="5162353";
        String direccion="Cra 56#152-42";
        String email="camilagomezr05@gmail.com";
        Cliente cliente=new Cliente(nombre,documento,telefono,direccion,email);
        ServiciosAlquilerItemsStub alquiler=new ServiciosAlquilerItemsStub();
        
        try{
            alquiler.registrarCliente(cliente);
            Cliente pruebaCliente=alquiler.consultarCliente(documento);
            assertEquals("El cliente no existe",cliente.getDocumento(),pruebaCliente.getDocumento());
            assertEquals("El cliente no existe",cliente.getTelefono(),pruebaCliente.getTelefono());
            assertEquals("El cliente no existe",cliente.getDireccion(),pruebaCliente.getDireccion());
            assertEquals("El cliente no existe",cliente.getEmail(),pruebaCliente.getEmail());
            assertEquals("El cliente no existe",alquiler.consultarClientes().size(),1);
            
        }catch(Exception e){}
    } 
    
    @Test
    public void testC3(){
        String nombre="Maria Camila Gomez Ramirez";
        long documento=1019132336;
        String telefono="5162353";
        String direccion="Cra 56#152-42";
        String email="camilagomezr05@gmail.com";
        boolean vetado=false;
        
        ArrayList<ItemRentado> Irentados=new ArrayList<ItemRentado>();
        Cliente cliente=new Cliente(nombre,documento,telefono,direccion,email,vetado,Irentados);
        ServiciosAlquilerItemsStub alquiler=new ServiciosAlquilerItemsStub();
        
        try{
            alquiler.registrarCliente(cliente);
            Cliente pruebaCliente=alquiler.consultarCliente(documento);
            assertEquals("El cliente no existe",cliente.getDocumento(),pruebaCliente.getDocumento());
            assertEquals("El cliente no existe",cliente.getTelefono(),pruebaCliente.getTelefono());
            assertEquals("El cliente no existe",cliente.getDireccion(),pruebaCliente.getDireccion());
            assertEquals("El cliente no existe",cliente.getEmail(),pruebaCliente.getEmail());
            assertEquals("El cliente no existe",cliente.isVetado(),pruebaCliente.isVetado());
            assertEquals("El cliente no existe",cliente.getRentados(),pruebaCliente.getRentados());
            assertEquals("El cliente no existe",alquiler.consultarClientes().size(),1);
            
        }catch(Exception e){}     
        
    }
    
    @Test
    public void testC4(){
        String nombre="Maria Camila Gomez Ramirez";
        long documento=1019132336;
        String telefono="5162353";
        String direccion="Cra 56#152-42";
        String email="camilagomezr05@gmail.com";
        boolean vetado=false;
        
        ArrayList<ItemRentado> Irentados=new ArrayList<ItemRentado>();
        Cliente cliente=new Cliente(nombre,documento,telefono,direccion,email,vetado,Irentados);
        ServiciosAlquilerItemsStub alquiler=new ServiciosAlquilerItemsStub();
        
        try{
            alquiler.registrarCliente(cliente);
            Cliente pruebaCliente=alquiler.consultarCliente(documento);
            assertEquals("El cliente no existe",cliente.getDocumento(),pruebaCliente.getDocumento());
            assertEquals("El cliente no existe",cliente.getTelefono(),pruebaCliente.getTelefono());
            assertEquals("El cliente no existe",cliente.getDireccion(),pruebaCliente.getDireccion());
            assertEquals("El cliente no existe",cliente.getEmail(),pruebaCliente.getEmail());
            assertEquals("El cliente no existe",cliente.isVetado(),pruebaCliente.isVetado());
            assertEquals("El cliente no existe",cliente.getRentados(),pruebaCliente.getRentados());
            assertEquals("El cliente no existe",alquiler.consultarClientes().size(),1);
            alquiler.registrarCliente(cliente);
        }catch (Exception e){
            assertTrue("No exixste la excepcion",true);
        }
        
    }
    
    
    
    
    
    
    
}
