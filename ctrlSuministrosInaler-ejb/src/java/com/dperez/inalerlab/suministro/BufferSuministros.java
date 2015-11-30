
package com.dperez.inalerlab.suministro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;


@Singleton
public class BufferSuministros {

    @Inject
    ManejadorSuministro mSuministros;
    
    public BufferSuministros() {}
    
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
    
    public List<Suministro> getListaSuministros(){
        List<Suministro> lista = new ArrayList<>();
        for(Suministro suministro: MapSuministros.values()){
            lista.add(suministro);
        }
        return lista;
    }
    
    public  Map<String, Integer> getMapSuministrosPorProveedor(int IdProveedor){
        Map<String, Integer> map = new HashMap<>();
        for(Suministro suministro: MapSuministros.values()){
            if(suministro.getProveedorSuministro().getIdProveedor()==IdProveedor) map.put(suministro.getProveedorSuministro().getNombreProveedor(), suministro.getProveedorSuministro().getIdProveedor());
        }
        return new TreeMap<>(map);
    }
    
    public Map<String, Integer> getMapNombreSuministros(){
        Map<String, Integer> map = new HashMap<>();
        for(Suministro suministro: MapSuministros.values()){
            map.put(suministro.getNombreSuministro(), suministro.getIdSuministro());
        }
        return new TreeMap<>(map);
    }
    
    public Map<Integer, Suministro> getMapSuministros(){
        return MapSuministros;
    }
}
