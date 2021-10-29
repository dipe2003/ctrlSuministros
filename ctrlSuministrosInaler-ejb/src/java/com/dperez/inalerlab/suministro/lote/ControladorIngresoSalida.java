package com.dperez.inalerlab.suministro.lote;

import com.dperez.inalerlab.operario.ControladorOperario;
import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.suministro.ManejadorSuministro;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorIngresoSalida implements Serializable{
    @Inject
    private ManejadorIngresoSalida mInSal;
    @Inject
    private ControladorLote cLote;
    @Inject
    private ControladorOperario cOp;
    @Inject
    private ManejadorSuministro mSum;
    
    /**
     * Crea un ingreso en la base de datos.
     * @param FechaIngreso
     * @param CantidadIngreso
     * @param NumeroFactura
     * @param IdLoteIngreso
     * @param IdOperarioIngreso
     * @param ObservacionesIngreso
     * @param IdSuministro
     * @return Retorna el id del ingreso creada. Retorna -1 si no se crea.
     */
    public int CrearIngreso(Date FechaIngreso, float CantidadIngreso, String NumeroFactura, int IdLoteIngreso, int IdOperarioIngreso, String ObservacionesIngreso, int IdSuministro){
        int id = -1;
        try{
            Operario operario = cOp.BuscarOperario(IdOperarioIngreso);
            Ingreso ingreso = new Ingreso(FechaIngreso, CantidadIngreso, NumeroFactura, ObservacionesIngreso);
            ingreso.setLoteIngreso(cLote.BuscarLote(IdLoteIngreso));
            ingreso.setOperarioIngresoSuministro(operario);
            id= mInSal.CrearIngreso(ingreso);
        }catch(NullPointerException ex){}
        return id;
    }
    
    /**
     * Busca un ingreso en la base de datos.
     * @param IdIngreso
     * @return
     */
    public Ingreso BuscarIngreso(int IdIngreso){
        return mInSal.ObtenerIngreso(IdIngreso);
    }
    
    /**
     * Devuelve una lista con todos los ingresos registrados en la base de datos.
     * Si no hay ingresos registrados devuelve una lista vacia.
     * @return
     */
    public List<Ingreso> ListarIngresos(){
        return mInSal.ListarIngresos();
    }
    
    /**
     * Actualiza los datos del ingreso indicado.
     * @param IdIngreso
     * @param CantidadIngreso
     * @param NumeroFactura
     * @return -1 si no se actualizo. IdIngreso si se actualizo.
     */
    public int ActualizarIngreso(int IdIngreso, float CantidadIngreso, String NumeroFactura){
        Ingreso ingreso = mInSal.ObtenerIngreso(IdIngreso);
        if(ingreso.getCantidadIngreso()!= CantidadIngreso) ingreso.setCantidadIngreso(CantidadIngreso);
        if(!ingreso.getNumeroFactura().equalsIgnoreCase(NumeroFactura)) ingreso.setNumeroFactura(NumeroFactura);
        return mInSal.ActualizarIngreso(ingreso);
    }
    
    /*
    *  Salidas
    */
    
    /**
     * Crea un salida en la base de datos.
     * @param FechaSalida
     * @param CantidadSalida
     * @param IdLoteSalida
     * @param IdOperarioSalida
     * @param ObservacionesSalida
     * @return Retorna el id de la salida creada. Retorna -1 si no se crea.
     */
    public int CrearSalida(Date FechaSalida, float CantidadSalida, int IdLoteSalida, int IdOperarioSalida, String ObservacionesSalida){
        int id=-1;
        try{
            Operario Operario = cOp.BuscarOperario(IdOperarioSalida);
            Salida salida = new Salida(FechaSalida, CantidadSalida, ObservacionesSalida);
            salida.setLoteSalida(cLote.BuscarLote(IdLoteSalida));
            salida.setOperarioSalidaSuministro(Operario);
            id = mInSal.CrearSalida(salida);
        }catch(NullPointerException ex){}
        return id;
    }
    
    /**
     * Busca un salida en la base de datos.
     * @param IdSalida
     * @return
     */
    public Salida BuscarSalida(int IdSalida){
        return mInSal.ObtenerSalida(IdSalida);
    }
    
    /**
     * Devuelve una lista con todos los salidas registrados en la base de datos.
     * Si no hay salidas registrados devuelve una lista vacia.
     * @return
     */
    public List<Salida> ListarSalidas(){
        return mInSal.ListarSalidas();
    }
    
    
}	