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
     * @param VencimientoLote
     * @return Retorna el id del lote. Retorna -1 si no se pudo crear.
     */
    public int CrearLote(Date VencimientoLote){
        return cLote.CrearLote(VencimientoLote);
    }
    
    /**
     * Crea un ingreso de suministro para el lote especificado.
     * @param FechaIngreso
     * @param CantidadIngreso
     * @param NumeroFactura
     * @param IdLoteIngreso
     * @return 
     */
    public int CrearIngreso(Date FechaIngreso, float CantidadIngreso, String NumeroFactura, int IdLoteIngreso){
        return cInSal.CrearIngreso(FechaIngreso, CantidadIngreso, NumeroFactura, IdLoteIngreso);
    }
    
    /**
     * Crea una salida de suministro para el lote especificado.
     * @param FechaSalida
     * @param CantidadSalida
     * @param IdLoteSalida
     * @return 
     */
    public int CrearSalida(Date FechaSalida, float CantidadSalida, int IdLoteSalida){
        return cInSal.CrearSalida(FechaSalida, CantidadSalida, IdLoteSalida);
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
}
