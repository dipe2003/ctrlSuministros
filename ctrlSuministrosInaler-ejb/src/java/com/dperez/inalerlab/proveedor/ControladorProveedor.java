package com.dperez.inalerlab.proveedor;

import com.dperez.inalerlab.buffer.BufferGenerico;
import com.dperez.inalerlab.buffer.FabricaBuffer;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorProveedor implements Serializable{
    @Inject
    private ManejadorProveedor mProveedor;
    @Inject
    private FabricaBuffer fBuffer;
    
    private BufferGenerico<Proveedor> buffer;
    
    @PostConstruct
    public void init(){
        buffer = fBuffer.getBufferProveedor(mProveedor.ListarProveedores());
    }
    
    /**
     * Crea un proveedor en la base de datos.
     * @param NombreProveedor
     * @param ContactoProveedor
     * @return Retorna el id del proveedor creado. Si no se creo retorna -1.
     */
    public int CrearProveedor(String NombreProveedor, String ContactoProveedor){
        Proveedor proveedor = new Proveedor(NombreProveedor, ContactoProveedor);
        int id = mProveedor.CrearProveedor(proveedor);
        if(id>0){
            proveedor.setIdProveedor(id);
            buffer.putEntidad(proveedor, id);
        }
        return id;
    }
    
    /**
     * Busca un proveedor en la base de datos.
     * @param IdProveedor
     * @return
     */
    public Proveedor BuscarProveedor(int IdProveedor){
        return buffer.getEntidad(IdProveedor);
    }
    
    /**
     * Devuelve una lista con todos los proveedores registrados en la base de datos.
     * Si no hay proveedores registrados devuelve una lista vacia.
     * @return
     */
    public List<Proveedor> ListarProveedores(){
        return buffer.getListaEntidades();
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
        if(id>0){
            buffer.updateEntidad(proveedor, id);
        }
        return id;
    }
    
}	