/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquilerFactory;
import edu.eci.pdsw.samples.services.ServiciosAlquilerItemsStub;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 * 
 * Calculo Multa:
 * 
 * Frontera:
 * CF1: Multas a devoluciones hechas en la fecha exacta (multa 0).
 * Clases de equivalencia:
 * CE1: Multas hechas a devolciones realizadas en fechas posteriores
 * a la limite. (multa multa_diaria*dias_retraso)
 * CE2:Multas con fechas no coherentes, es decir fecha de entrega antecede fecha de recolección de documento: debe dar error
 * CE3:Multas cuando el alquiler se entrega antes de la fecha de bencimiento: debe dar 0
 * CE4: Multas cuando item no está en alquiler deben dar 0
 * Registro de alquileres
 * CE5: Debe dar error cuando numdias es negativo
 * CE6: No debe dejar agregar un articulo que ya fue alquilado
 */
public class AlquilerTest {

    public AlquilerTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void CF1Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquiler();
        
        Item i1=new Item(sa.consultarTipoItem(1), 44, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Perez",3842,"24234","calle 123","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(44);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 3842, item, 5);
        
        assertEquals("No se calcula correctamente la multa (0) "
                + "cuando la devolucion se realiza el dia limite."
                ,0,sa.consultarMultaAlquiler(44, java.sql.Date.valueOf("2005-12-25")));
                
    }
    

    @Test
    public void CE1Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquiler();
        
        Item i1=new Item(sa.consultarTipoItem(1), 55, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Perez",9843,"24234","calle 123","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(55);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 9843, item, 5);
        //prueba: 3 dias de retraso
        assertEquals("No se calcula correctamente la multa "
                + "cuando la devolucion se realiza varios dias despues del limite."
                ,sa.valorMultaRetrasoxDia()*3,sa.consultarMultaAlquiler(55, java.sql.Date.valueOf("2005-12-28")));
                
    }
    @Test
    public void CE2Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquiler();
        
        Item i1=new Item(sa.consultarTipoItem(1), 55, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Andrade",9823,"24234","calle 13","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(55);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 9843, item, -5);
        //prueba:días negativos
        boolean bool=false;
        try{
            long x=sa.consultarMultaAlquiler(55, java.sql.Date.valueOf("2005-12-28"));
            if (x ==13){
                bool=false;
            }      
                    }
        catch(Exception e){
            bool=true;
        }
        if(bool==false){
            assertFalse("No se esta lanzando un error al haber una fecha negativa en el servicio de alquiler",false);
        }
        else{
            assertTrue(true);
        }          
    }
    @Test
    public void CE3Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquiler();
        
        Item i1=new Item(sa.consultarTipoItem(1), 55, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Andrade 2",2107990,"30144543","calle crack","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(55);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 2107990, item, 5);
        //prueba: 2 días antes d ela fecha de vencimiento
        assertEquals("No se calcula correctamente la multa "
                + "cuando la devolucion se realiza varios dias antes del limite."
                ,0,sa.consultarMultaAlquiler(55, java.sql.Date.valueOf("2005-12-23")));
                
    }
    @Test
    public void CE4Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquiler();
        
        Item i1=new Item(sa.consultarTipoItem(1), 57, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Andrade 3",2107991,"30144543","calle crack","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(57);
        
        //prueba: no debe calcular multa de un item que noe ste en alquiler debe lanzar error
        boolean bool=false;
        try{
            long x=sa.consultarMultaAlquiler(57, java.sql.Date.valueOf("2005-12-28"));
                  
                    }
        catch(Exception e){
            bool=true;
        }
        if(bool==false){
            assertFalse("No se esta lanzando un error al haber ingresado un item que no existe",false);
        }
        else{
            assertTrue(true);
        }   
    }
    
    public void CE5Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquiler();
        
        Item i1=new Item(sa.consultarTipoItem(1), 58, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Andrade 3",2107992,"30144543","calle crack","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(58);
        
        //prueba: debe lanzar error cuando s eingresa una fecha negativa
        boolean bool=false;
        try{
           sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 2107992, item, -5);
                  
                    }
        catch(Exception e){
            bool=true;
        }
        if(bool==false){
            assertFalse("Esta dejando registrar un alquiler con un numero de días negativo",false);
        }
        else{
            assertTrue(true);
        }   
    }
    public void CE6Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquiler();
        
        Item i1=new Item(sa.consultarTipoItem(1), 59, "Los 4 Fantasticos", "Los 4 Fantásticos  es una película de superhéroes  basada en la serie de cómic homónima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Andrade 3",2107993,"30144543","calle crack","aa@gmail.com"));
        sa.registrarCliente(new Cliente("Juan Andrade 3",2107994,"30144543","calle crack","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(59);
        
        //prueba: debe lanzar eror cuando se alquila un item que ya fue alquilado
        boolean bool=false;
        try{
           sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 2107993, item, 5);
           sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 2107994, item, 5);
                  
                    }
        catch(Exception e){
            bool=true;
        }
        if(bool==false){
            assertFalse("Esta dejando registrar un alquiler con un numero de días negativo",false);
        }
        else{
            assertTrue(true);
        }   
    }
    
    
    
    
    
    
    
    
}
