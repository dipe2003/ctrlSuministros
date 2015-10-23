package com.dperez.inalerlab.suministro.lote;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorLote implements Serializable{
    @Inject
    private ManejadorLote mLote;
    
    /**
     * Crea un lote en la base de datos.
     * @param VencimientoLote
     * @return 
     */
    public Lote CrearLote(Calendar VencimientoLote){
        Lote lote = new Lote(VencimientoLote);
        if(mLote.CrearLote(lote)!=-1){
            return lote;
        }
        return null;
    }
    
    /**
     * Busca un lote en la base de datos.
     * @param IdLote
     * @return 
     */
    public Lote BuscarLote(int IdLote){
        return mLote.ObtenerLote(IdLote);
    }
    
    /**
     * Devuelve una lista con todos los lotes registrados en la base de datos.
     * Si no hay lotes registrados devuelve una lista vacia.
     * @return 
     */
    public List<Lote> ListarLotes(){
        return mLote.ListarLotes();
    }
    
}	