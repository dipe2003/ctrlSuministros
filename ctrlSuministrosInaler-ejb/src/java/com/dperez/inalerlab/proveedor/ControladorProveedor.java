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
     * @return Retorna el id del proveedor creado. Si no se creo retorna -1.
     */
    public int CrearProveedor(String NombreProveedor, String ContactoProveedor){
        Proveedor proveedor = new Proveedor(NombreProveedor, ContactoProveedor);
        int id = mProveedor.CrearProveedor(proveedor);
        return id;
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
    
    /**
     * Actualiza los datos de un usuario especificado por su id.
     * @param IdProveedor
     * @param NombreProveedor
     * @param ContactoProveedor
     * @return Retorna el id del proveedor. Retorna -1 si no se pudo actualizar.
     */
    public int ModificarDatosProveedor(int IdProveedor, String NombreProveedor, String ContactoProveedor){
        Proveedor proveedor = mProveedor.ObtenerProveedor(IdProveedor);
        proveedor.setNombreProveedor(NombreProveedor);
        proveedor.setContactoProveedor(ContactoProveedor);
        int id = mProveedor.ActualizarProveedor(proveedor);
        return id;
    }
     
}	