package com.dperez.inalerlab.suministro.lote;

import com.dperez.inalerlab.operario.ControladorOperario;
import com.dperez.inalerlab.operario.Operario;
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
    
    /**
     * Crea un ingreso en la base de datos.
     * @param FechaIngreso
     * @param CantidadIngreso
     * @param NumeroFactura
     * @param IdLoteIngreso
     * @param IdOperarioIngreso
     * @param ObservacionesIngreso
     * @return Retorna el id del ingreso creada. Retorna -1 si no se crea.
     */
    public int CrearIngreso(Date FechaIngreso, float CantidadIngreso, String NumeroFactura, int IdLoteIngreso, int IdOperarioIngreso, String ObservacionesIngreso){
        try{
            Operario operario = cOp.BuscarOperario(IdOperarioIngreso);
            Ingreso ingreso = new Ingreso(FechaIngreso, CantidadIngreso, NumeroFactura, cLote.BuscarLote(IdLoteIngreso), ObservacionesIngreso );
            ingreso.setOperarioIngresoSuministro(operario);
            return mInSal.CrearIngreso(ingreso);
        }catch(NullPointerException ex){}
        return -1;        
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
        try{
            Operario Operario = cOp.BuscarOperario(IdOperarioSalida);
            Salida salida = new Salida(FechaSalida, CantidadSalida, cLote.BuscarLote(IdLoteSalida), ObservacionesSalida);
            salida.setOperarioSalidaSuministro(Operario);
            return mInSal.CrearSalida(salida);
        }catch(NullPointerException ex){}
        return -1;
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