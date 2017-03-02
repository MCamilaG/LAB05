/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * C1:crear un cliente sin parametros de entrada. Tipo:Normal. Resultado: se creo el cliente y esta disponible para futuros alquileres
 * C2:crear un cliente con todos los atributos a excepcion de si esta vetado y del item rentado. Tipo:Normal. Resultado:se creo el cliente con todos los atributos menos si esta vetado y el item rentado y esta disponible para futuros alquileres
 * C3:crear un cliente con todos los atributos. Tipo:Normal. Resultado: se creo el cliente con todos los atributos y esta disponible para futuros alquileres
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
    public void testC3(){
        String nombre="Maria Camila Gomez Ramirez";
        long documento=1019132336;
        String telefono="5162353";
    }
    
    
    
    
    
    
    
}
