package com.dperez.inalerlab.operario.permiso;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorPermiso implements Serializable{
    @Inject
    private ManejadorPermiso mPermiso;
    
    /**
     * Crea un permiso en la base de datos.
     * @param NombrePermiso
     * @return 
     */
    public Permiso CrearPermiso(String NombrePermiso){
        Permiso permiso = new Permiso(NombrePermiso);
        if(mPermiso.CrearPermiso(permiso)!=-1){
            return permiso;
        }
        return null;
    }
    
    /**
     * Busca un permiso en la base de datos.
     * @param IdPermiso
     * @return 
     */
    public Permiso BuscarPermiso(int IdPermiso){
        return mPermiso.ObtenerPermiso(IdPermiso);
    }
    
    /**
     * Devuelve una lista con todos los permisos registrados en la base de datos.
     * Si no hay permisos registrados devuelve una lista vacia.
     * @return 
     */
    public List<Permiso> ListarPermisos(){
        return mPermiso.ListarPermisos();
    }
    
}	