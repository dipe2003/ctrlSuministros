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
    
    public int CrearUnidad(String NombreUnidad){
        Unidad unidad = new Unidad(NombreUnidad);
        return mUnidad.CrearUnidad(unidad);
    }
        
    public Unidad BuscarUnidad(int IdUnidad){
        return mUnidad.ObtenerUnidad(IdUnidad);
    }
    
    public List<Unidad> ListarUnidades(){
        return mUnidad.ListarUnidades();
    }
    
}	