/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dperez.inalerlab.buffer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejo intermedio de coleccion de datos para acceso de lectura sin llegar a base de datos.
 * 04/11/2021
 * @author dperez
 * @param <T>
 */
public class BufferGenerico <T> {
        
    private Map<Integer, T> Entidades = new HashMap<>();
    
    public void setEntidades(Map<Integer, T> entidades){
        this.Entidades = entidades;
    }

    public T getEntidad(int id){
        return Entidades.get(id);
    }
    
    public void putEntidad(T entidad, int id){
        Entidades.put(id, entidad);
    }
    
    public void removeEntiidad(int id){
        Entidades.remove(id);
    }
    
    public void updateEntidad(T entidad, int id){
        Entidades.replace(id, entidad);
    }
    
    public boolean existeEntidad(int id){
        return Entidades.containsKey(id);
    }
    
    public int bufferSize(){
        return Entidades.size();
    }
    
    public List<T> getListaEntidades(){
        return Entidades.values().stream()
                .collect(Collectors.toList());
    }
}
