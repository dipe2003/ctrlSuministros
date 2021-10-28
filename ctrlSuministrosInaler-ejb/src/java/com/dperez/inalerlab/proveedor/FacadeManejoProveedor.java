package com.dperez.inalerlab.proveedor;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
/**
 * @author dperez
 */
@Named
@Stateless
public class FacadeManejoProveedor implements Serializable {
    @Inject
    private ControladorProveedor cProveedor;

    /**
     * Lista todos los proveedor registrados en la base de datos.
     * @return Retorna una lista vacia si no existen operarios registrados.
     */
    public List<Proveedor> ListarProveedores(){
        return cProveedor.ListarProveedores();
    }
    
    /**
     * Busca un proveedor registrado en la base de datos.
     * El proveedor debe estar registrado previamente.
     * @param IdProveedor Numero de Proveedor
     * @return 
     */
    public Proveedor BuscarProveedor(int IdProveedor){
        return cProveedor.BuscarProveedor(IdProveedor); 
    }
    
    /**
     * Comprueba la existencia de un proveedor registrado en la base de datos según el id especificado.
     * @param IdProveedor Numero de Proveedor
     * @return Retorna <b>True</b> si existe el proveedor.
     */
    public boolean ExisteProveedor(int IdProveedor){
        return cProveedor.BuscarProveedor(IdProveedor)!=null;
    }    
       
    /**
     * Registra un proveedor en la base de datos.
     * @param NombreProveedor
     * @param ContactoProveedor
     * @return Retorna el id del proveedor creado. Si no se creo retorna -1.
     */
    public int RegistrarProveedor(String NombreProveedor, String ContactoProveedor){        
        return cProveedor.CrearProveedor(NombreProveedor, ContactoProveedor);
    }
        
    /**
     * Actualiza los datos de un usuario especificado por su id.
     * @param IdProveedor
     * @param NombreProveedor
     * @param ContactoProveedor
     * @return Retorna el id del proveedor. Retorna -1 si no se pudo actualizar.
     */
    public int ModificarDatosProveedor(int IdProveedor, String NombreProveedor, String ContactoProveedor){
        return cProveedor.ModificarDatosProveedor(IdProveedor, NombreProveedor, ContactoProveedor);
    }

 }