package com.dperez.inalerlab.suministro.lote;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class FacadeLote implements Serializable{
    @Inject
    private ControladorIngresoSalida cInSal;
    @Inject
    private ControladorLote cLote;
    
    /**
     * Crea un lote.
     * @param ProduccionLote
     * @param VencimientoLote
     * @param NumeroLote
     * @return Retorna el id del lote. Retorna -1 si no se pudo crear.
     */
    public int CrearLote(Date ProduccionLote, Date VencimientoLote, String NumeroLote){
        return cLote.CrearLote(ProduccionLote, VencimientoLote, NumeroLote);
    }
    
    /**
     * Crea un ingreso de suministro para el lote especificado.
     * @param FechaIngreso
     * @param CantidadIngreso
     * @param NumeroFactura
     * @param IdLoteIngreso
     * @param IdOperario
     * @return 
     */
    public int CrearIngreso(Date FechaIngreso, float CantidadIngreso, String NumeroFactura, int IdLoteIngreso, int IdOperario ){
        return cInSal.CrearIngreso(FechaIngreso, CantidadIngreso, NumeroFactura, IdLoteIngreso, IdOperario);
    }
    
    /**
     * Crea una salida de suministro para el lote especificado.
     * @param FechaSalida
     * @param CantidadSalida
     * @param IdLoteSalida
     * @param IdOperario
     * @return 
     */
    public int CrearSalida(Date FechaSalida, float CantidadSalida, int IdLoteSalida, int IdOperario){
        return cInSal.CrearSalida(FechaSalida, CantidadSalida, IdLoteSalida, IdOperario);
    }
    
    /**
     * Agrega un lote creado a un suministro especificado.
     * @param IdSuministro
     * @param IdLote
     * @return 
     */
    public int AgregarLoteSuministro(int IdSuministro, int IdLote){
        return cLote.AgregarLoteSuministro(IdLote, IdSuministro);
    }
    
    
    /**
     * Buscar un lote por numero de lote.
     * @param NumeroLote
     * @return Retorna Null si no existe.
     */
    public Lote BuscarLotePorNumeroLote(String NumeroLote){
        return cLote.BuscarLotePorNumeroLote(NumeroLote);
    }
    
    /**
     * Buscar un lote por Id.
     * @param IdLote
     * @return Retorna Null si no existe.
     */
    public Lote BuscarLotePorIdLote(int IdLote){
        return cLote.BuscarLote(IdLote);
    }
}
