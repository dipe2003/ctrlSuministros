
package com.dperez.inalerlab.proveedor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 * Manejo intermedio de coleccion de datos para acceso de lectura sin llegar a base de datos.
 * @author dperez
 */
@Stateful
@SessionScoped
public class BufferProveedores {

    @Inject
    private ManejadorProveedor mProveedor;
      
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
    
    public List<Proveedor> getListaProveedores(){
        return MapProveedores.values().stream()
                .collect(Collectors.toList());
    }
    
    public Map<String, Integer> getMapNombreProveedores(){
        return MapProveedores.values().stream()
                .sorted()
                .collect(Collectors.toMap(Proveedor::getNombreProveedor, proveedor->proveedor.getIdProveedor()));
    }
    
    public Map<Integer, Proveedor> getMapProveedors(){
        return MapProveedores;
    }
}
