package com.dperez.inalerlab.suministro.lote;

import com.dperez.inalerlab.suministro.ControladorSuministro;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorLote implements Serializable{
    @Inject
    private ManejadorLote mLote;
    @Inject
    private ControladorIngresoSalida cInSal;
    @Inject
    private ControladorSuministro cSuministro;
    
    /**
     * Crea un lote en la base de datos.
     * @param VencimientoLote
     * @return Retorna el id del lote creado. Retorna -1 si no se creo.
     */
    public int CrearLote(Date VencimientoLote){
        Lote lote = new Lote(VencimientoLote);
        return mLote.CrearLote(lote);
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
    
    /**
     * Agrega un lote ya creado al suministro especificado por su id.
     * @param IdLote
     * @param IdSuministro
     * @return Retorna el id del lote. Retorna -1 si no se agrego.
     */
    public int AgregarLoteSuministro(int IdLote, int IdSuministro){
        Lote lote = mLote.ObtenerLote(IdLote);
        lote.setSuministroLote(cSuministro.BuscarSuministro(IdSuministro));
        return mLote.ActualizarLote(lote);
    }
    
}	