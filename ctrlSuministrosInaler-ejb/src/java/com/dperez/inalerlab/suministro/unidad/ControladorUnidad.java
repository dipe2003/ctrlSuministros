package com.dperez.inalerlab.suministro.unidad;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorUnidad implements Serializable{
    @Inject
    private ManejadorUnidad mUnidad;
    
    public Unidad CrearUnidad(){
        Unidad unidad = new Unidad();
        if(mUnidad.CrearUnidad(unidad)!=-1){
            return unidad;
        }
        return null;
    }
        
    public Unidad BuscarUnidad(int IdUnidad){
        return mUnidad.ObtenerUnidad(IdUnidad);
    }
    
    public List<Unidad> ListarUnidades(){
        return mUnidad.ListarUnidades();
    }
    
}	