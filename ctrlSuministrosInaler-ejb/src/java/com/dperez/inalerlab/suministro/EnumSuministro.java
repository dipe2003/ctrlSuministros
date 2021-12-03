
package com.dperez.inalerlab.suministro;

/**
 *
 * @author dperez
 */
public enum EnumSuministro {
    REACTIVO_QUIMICO("Reactivo Quimico"),
    MEDIO_ENSAYO("Medio de Ensayo"),
    MATERIAL("Material");
    
    private final String nombre;
    
    EnumSuministro(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
}
