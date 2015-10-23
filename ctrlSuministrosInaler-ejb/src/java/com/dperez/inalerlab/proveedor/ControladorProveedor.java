package com.dperez.inalerlab.proveedor;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorProveedor implements Serializable{
    @Inject
    private ManejadorProveedor mProveedor;
    
    /**
     * Crea un proveedor en la base de datos.
     * @param NombreProveedor
     * @param ContactoProveedor
     * @return 
     */
    public Proveedor CrearProveedor(String NombreProveedor, String ContactoProveedor){
        Proveedor proveedor = new Proveedor(NombreProveedor, ContactoProveedor);
        if(mProveedor.CrearProveedor(proveedor)!=-1){
            return proveedor;
        }
        return null;
    }
    
    /**
     * Busca un proveedor en la base de datos.
     * @param IdProveedor
     * @return 
     */
    public Proveedor BuscarProveedor(int IdProveedor){
        return mProveedor.ObtenerProveedor(IdProveedor);
    }
    
    /**
     * Devuelve una lista con todos los proveedores registrados en la base de datos.
     * Si no hay proveedores registrados devuelve una lista vacia.
     * @return 
     */
    public List<Proveedor> ListarProveedores(){
        return mProveedor.ListarProveedores();
    }
    
}	