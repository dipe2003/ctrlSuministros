package com.dperez.inalerlab.suministro.lote;

import com.dperez.inalerlab.suministro.BufferSuministros;
import com.dperez.inalerlab.suministro.ControladorSuministro;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    @Inject
    private BufferSuministros buffer;
    
    
    /**
     * Crea un lote en la base de datos.
     * @param VencimientoLote
     * @param NumeroLote
     * @param IdSuministro
     * @return Retorna el id del lote creado. Retorna -1 si no se creo.
     */
    public int CrearLote(Date VencimientoLote, String NumeroLote, int IdSuministro){
        Lote lote = new Lote(VencimientoLote, NumeroLote);
        Suministro suministro = cSuministro.BuscarSuministro(IdSuministro);
        suministro.addLote(lote);
        return mLote.CrearLote(lote);
    }
    
    /**
     * Busca un lote por su IdLote en la base de datos.
     * @param IdLote
     * @return
     */
    public Lote BuscarLote(int IdLote){
        return mLote.ObtenerLote(IdLote);
    }
    
    /**
     * Busca un lote por su NumeroLote en la base de datos.
     * @param NumeroLote
     * @return
     */
    public Lote BuscarLotePorNumeroLote(String NumeroLote){
        return mLote.ObtenerLote(NumeroLote);
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
        int id= mLote.ActualizarLote(lote);
        if(id!=-1){
            buffer.updateSuministro(cSuministro.BuscarSuministro(IdSuministro));
        }
        return id;
    }
    
    /**
     * Comprueba la existencia del numero de lote para el suministro especificado.
     * @param NumeroLote
     * @param IdSuministro
     * @return Retorna el id del suministro. Retorna 0 si no existe.
     */
    public int ExisteLoteSuministro(String NumeroLote, int IdSuministro){
        if(NumeroLote.isEmpty() || NumeroLote.equals("")){
            return 0;
        }
        return mLote.ExisteNumeroLoteSuministro(NumeroLote, IdSuministro);
    }
    
    /**
     * Devuelve un Map con los lotes registrados de un suministro en el sistema.
     * @param IdSuministro
     * @return Retorna un Map con el numero de lote (key) e id (value)
     */
    public Map<String, Integer> ListarMapLotes(int IdSuministro){
        return mLote.ListarMapLotes(IdSuministro);
    }
    
    /**
     * Actualiza los datos del ingreso especificado y del lote relacionado. No se actualiza buffer.
     * @param IdLote
     * @param IdIngreso
     * @param NumeroLote
     * @param CantidadIngreso
     * @param FechaVencimientoLote
     * @param NumeroFactura
     * @return -1 si no se actualizo. IdLote si se actualizo.
     */
    public int ActualizarLoteIngreso(int IdLote, int IdIngreso, String NumeroLote, float CantidadIngreso,
            Date FechaVencimientoLote, String NumeroFactura){
        Lote lot  = mLote.ObtenerLote(IdLote);
        if(lot != null){
            if(!lot.getNumeroLote().equalsIgnoreCase(NumeroLote)) lot.setNumeroLote(NumeroLote);
            if(lot.getVencimientoLote()!=null){
                if(lot.getVencimientoLote().compareTo(FechaVencimientoLote)!=0){
                    lot.setVencimientoLote(FechaVencimientoLote);
                }
            }else{
                lot.setVencimientoLote(FechaVencimientoLote);
            }
        }        
        
        if(mLote.ActualizarLote(lot)!= -1){
            return cInSal.ActualizarIngreso(IdIngreso, CantidadIngreso, NumeroFactura);
        }
        return -1;
    }
}	