package com.dperez.inalerlab.suministro.lote;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
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
     * Crea un lote y lo agrega al suministro.
     * @param VencimientoLote
     * @param NumeroLote
     * @param IdSuministro
     * @return Retorna el id del lote. Retorna -1 si no se pudo crear.
     */
    public int CrearLote(Date VencimientoLote, String NumeroLote, int IdSuministro){
        return cLote.CrearLote(VencimientoLote, NumeroLote, IdSuministro);
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
        return cInSal.CrearIngreso(FechaIngreso, CantidadIngreso, NumeroFactura, IdLoteIngreso, IdOperario, Observaciones, IdSuministro);
    }
    
    /**
     * Crea una salida de suministro para el lote especificado.
     * @param FechaSalida
     * @param CantidadSalida
     * @param IdLoteSalida
     * @param IdOperario
     * @param ObservacionesSalida
     * @return
     */
    public int CrearSalida(Date FechaSalida, float CantidadSalida, int IdLoteSalida, int IdOperario, String ObservacionesSalida){
        return cInSal.CrearSalida(FechaSalida, CantidadSalida, IdLoteSalida, IdOperario, ObservacionesSalida);
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
    
    /**
     * Comprueba la existencia del numero de lote para el suministro especificado.
     * @param NumeroLote
     * @param IdSuministro
     * @return Retorna el id del suministro. Retorna 0 si no existe.
     */
    public int ExisteLoteSuministro(String NumeroLote, int IdSuministro){
        return cLote.ExisteLoteSuministro(NumeroLote, IdSuministro);
    }
    
    /**
     * Devuelve un Map con los lotes registrados en el sistema.
     * @param IdSuministro
     * @return Retorna un Map con el numero de lote (key) e id (value)
     */
    public Map<String, Integer> ListarMapLotes(int IdSuministro){
        return cLote.ListarMapLotes(IdSuministro);
    }
    /**
     * Devuelve un Map con los lotes con stock registrados en el sistema.
     * @param IdSuministro
     * @param Vencimiento True para incluir la fecha de vencimiento con el numero de lote.
     * @return Retorna un Map con el numero de lote (key) e id (value)
     */
    public Map<String, Integer> ListarMapLotesConStock(int IdSuministro, boolean Vencimiento){
        return cLote.ListarMapLotesStock(IdSuministro, Vencimiento);
    }
    
    /**
     * Devuelve el ingreso especificado por su Id.
     * @param IdIngreso
     * @return
     */
    public Ingreso BuscarIngreso(int IdIngreso){
        return cInSal.BuscarIngreso(IdIngreso);
    }
    
    /**
     * Actualiza los datos del ingreso especificado y del lote relacionado.No se actualiza el buffer.
     * @param IdLote
     * @param IdIngreso
     * @param NumeroLote
     * @param CantidadIngreso
     * @param FechaVencimientoLote
     * @param NumeroFactura
     * @return -1 si no se actualizo. IdLote si se actualizo.
     */
    public int ActualizarLoteIngreso(int IdLote, int IdIngreso, String NumeroLote, float CantidadIngreso, Date FechaVencimientoLote, String NumeroFactura){
        return cLote.ActualizarLoteIngreso(IdLote, IdIngreso, NumeroLote, CantidadIngreso, FechaVencimientoLote, NumeroFactura);
    }
    
}
