package com.dperez.inalerlab.proveedor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorProveedor implements Serializable{
    @Inject
    private ManejadorProveedor mProveedor;
    
    @Inject
    private BufferProveedores buffer;
    /**
     * Crea un proveedor en la base de datos.
     * @param NombreProveedor
     * @param ContactoProveedor
     * @return Retorna el id del proveedor creado. Si no se creo retorna -1.
     */
    public int CrearProveedor(String NombreProveedor, String ContactoProveedor){
        Proveedor proveedor = new Proveedor(NombreProveedor, ContactoProveedor);
        int id = mProveedor.CrearProveedor(proveedor);
        if(id!=-1){
            buffer.putProveedor(proveedor);
        }
        return id;
    }
    
    /**
     * Busca un proveedor en la base de datos.
     * @param IdProveedor
     * @return 
     */
    public Proveedor BuscarProveedor(int IdProveedor){
        if(buffer.containsProveedor(IdProveedor)) return buffer.getProveedor(IdProveedor);
        return mProveedor.ObtenerProveedor(IdProveedor);
    }
    
    /**
     * Devuelve una lista con todos los proveedores registrados en la base de datos.
     * Si no hay proveedores registrados devuelve una lista vacia.
     * @return 
     */
    public List<Proveedor> ListarProveedores(){
        if(buffer.bufferSize()>0)return buffer.getListaProveedors();
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
        if(id!=-1){
            buffer.updateProveedor(proveedor);
        }
        return id;
    }
    
    /**
     * Devuelve un Map con los proveedores registrados en el sistema.
     * @return Retorna un Map con los Nombres de los proveedores (key) y los id (value)
     */
    public Map<String, Integer> ListarMapProveedores(){
        if(buffer.bufferSize()>0) return buffer.getMapNombreProveedors();
        return mProveedor.ListarMapProveedores();
    }
    
    /**
     * Crea y devuelve un Map con el proveedor del suministro.
     * @param IdSuministro
     * @return Retorna un Map con los nombres de los proveedores (key) y sus id(values).
     */
    public Map<String, Integer>MapProveedorSuministro(int IdSuministro){
        List<Proveedor> proveedores;
        if(buffer.bufferSize()>=0){
            proveedores = buffer.getListaProveedors();
        }else{
            proveedores = mProveedor.ListarProveedores();
        }
        Map<String, Integer> map = new HashMap<>();
        for(Proveedor proveedor: proveedores){
            if(proveedor.esProveedorSuministro(IdSuministro)) map.put(proveedor.getNombreProveedor(), proveedor.getIdProveedor());
        }
        return new TreeMap<> (map);
    }
    
    
}	