
package com.dperez.inalerlab.suministro;

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
public class BufferSuministros {
    
    @Inject
            ManejadorSuministro mSuministros;
    
    @PostConstruct
    public void init(){
        MapSuministros = mSuministros.ListarMapSuministrosFull();
    }
    
    private Map<Integer, Suministro> MapSuministros = new HashMap<>();
    
    public Suministro getSuministro(int IdSuministro){
        return MapSuministros.get(IdSuministro);
    }
    
    public void putSuministro(Suministro suministro){
        MapSuministros.put(suministro.getIdSuministro(), suministro);
    }
    
    public void removeSuministro(Suministro suministro){
        MapSuministros.remove(suministro.getIdSuministro());
    }
    
    public void updateSuministro(Suministro suministro){
        MapSuministros.replace(suministro.getIdSuministro(), suministro);
    }
    
    public boolean containsSuministro(int IdSuministro){
        return MapSuministros.containsKey(IdSuministro);
    }
    
    public int bufferSize(){
        return MapSuministros.size();
    }
    
    public List<Suministro> getListaSuministros(boolean Vigente){
        if(Vigente){
            return MapSuministros.values().stream()
                    .sorted()
                    .filter(suministro->suministro.isVigente())
                    .collect(Collectors.toList());
        }else{
            return MapSuministros.values().stream()
                    .sorted()
                    .collect(Collectors.toList());
        }
    }
    
    public  Map<String, Integer> getMapSuministrosPorProveedor(int IdProveedor){
        Map<String, Integer> map = new HashMap<>();
        MapSuministros.values().stream().filter(suministro -> suministro.getProveedorSuministro().getIdProveedor()==IdProveedor).forEachOrdered((suministro) -> {
            map.put(suministro.getProveedorSuministro().getNombreProveedor(), suministro.getProveedorSuministro().getIdProveedor());
        });
        return new TreeMap<>(map);
    }
    
    public Map<String, Integer> getMapNombreSuministros(boolean Vigente){
        Map<String, Integer> map = new HashMap<>();
        MapSuministros.values().stream().filter((suministro) -> (suministro.isVigente())).forEachOrdered((suministro) -> {
            map.put(suministro.getNombreSuministro() + " (" + suministro.getProveedorSuministro().getNombreProveedor() + ")", suministro.getIdSuministro());
        });
        return new TreeMap<>(map);
    }
    
    public Map<Integer, Suministro> getMapSuministros(){
        return new TreeMap<>(MapSuministros);
    }
}
