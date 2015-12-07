
package com.dperez.inalerlab.proveedor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * Manejo intermedio de coleccion de datos para acceso de lectura sin llegar a base de datos.
 * @author dperez
 */
@Singleton
public class BufferProveedores {

    @Inject
    ManejadorProveedor mProveedor;
    
    public BufferProveedores() {}
    
    @PostConstruct
    public void init(){
        MapProveedores = mProveedor.ListarMapProveedoresFull();
    }
        
    private Map<Integer, Proveedor> MapProveedores = new HashMap<>();
    
    public Proveedor getProveedor(int IdProveedor){
        return MapProveedores.get(IdProveedor);
    }
    
    public void putProveedor(Proveedor proveedor){
        MapProveedores.put(proveedor.getIdProveedor(), proveedor);
    }
    
    public void removeProveedor(Proveedor proveedor){
        MapProveedores.remove(proveedor.getIdProveedor());
    }
    
    public void updateProveedor(Proveedor proveedor){
        MapProveedores.replace(proveedor.getIdProveedor(), proveedor);
    }
    
    public boolean containsProveedor(int IdProveedor){
        return MapProveedores.containsKey(IdProveedor);
    }
    
    public int bufferSize(){
        return MapProveedores.size();
    }
    
    public List<Proveedor> getListaProveedors(){
        List<Proveedor> lista = new ArrayList<>();
        for(Proveedor proveedor: MapProveedores.values()){
            lista.add(proveedor);
        }
        return lista;
    }
    
    
    public Map<String, Integer> getMapNombreProveedores(){
        Map<String, Integer> map = new HashMap<>();
        for(Proveedor proveedor: MapProveedores.values()){
            map.put(proveedor.getNombreProveedor(), proveedor.getIdProveedor());
        }
        return new TreeMap<>(map);
    }
    
    public Map<Integer, Proveedor> getMapProveedors(){
        return MapProveedores;
    }
}
