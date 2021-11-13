package com.dperez.inalerlab.suministro.lote;

import com.dperez.inalerlab.suministro.ControladorSuministro;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class FacadeLote implements Serializable{
    @Inject
    private ControladorSuministro cSuministro;
    
    /**
     * Crea un lote y lo agrega al suministro.
     * @param VencimientoLote
     * @param NumeroLote
     * @param IdSuministro
     * @return Retorna el id del lote. Retorna -1 si no se pudo crear.
     */
    public int AgregarLote(Date VencimientoLote, String NumeroLote, int IdSuministro){
        return cSuministro.AgregarLote(VencimientoLote, NumeroLote, IdSuministro);
    }
    
    /**
     * Crea un ingreso de suministro para el lote especificado.
     * @param FechaIngreso
     * @param CantidadIngreso
     * @param NumeroFactura
     * @param IdLoteIngreso
     * @param IdOperario
     * @param Observaciones
     * @return
     */
    public int CrearIngreso(Date FechaIngreso, float CantidadIngreso, String NumeroFactura, int IdLoteIngreso, int IdOperario, String Observaciones, int IdSuministro ){
        return cSuministro.CrearIngreso(FechaIngreso, CantidadIngreso, NumeroFactura, IdLoteIngreso, IdOperario, Observaciones, IdSuministro);
    }
    
    /**
     * Crea una salida de suministro para el lote especificado.
     * @param IdSuministro
     * @param FechaSalida
     * @param CantidadSalida
     * @param IdLoteSalida
     * @param IdOperario
     * @param ObservacionesSalida
     * @return
     */
    public int CrearSalida(int IdSuministro, Date FechaSalida, float CantidadSalida, int IdLoteSalida, int IdOperario, String ObservacionesSalida){
        return cSuministro.CrearSalida(IdSuministro, FechaSalida, CantidadSalida, IdLoteSalida, IdOperario, ObservacionesSalida);
    }
    
    /**
     * Actualiza los datos del ingreso especificado y del lote relacionado.No se actualiza el buffer.
     * @param idSuministro
     * @param IdLote
     * @param IdIngreso
     * @param NumeroLote
     * @param CantidadIngreso
     * @param FechaVencimientoLote
     * @param NumeroFactura
     * @return -1 si no se actualizo. IdLote si se actualizo.
     */
    public int ActualizarLoteIngreso(int idSuministro, int IdLote, int IdIngreso, String NumeroLote, float CantidadIngreso, Date FechaVencimientoLote, String NumeroFactura){
        return cSuministro.ActualizarLoteIngreso(idSuministro, IdLote, IdIngreso, NumeroLote, CantidadIngreso, FechaVencimientoLote, NumeroFactura);
    }
    
}
