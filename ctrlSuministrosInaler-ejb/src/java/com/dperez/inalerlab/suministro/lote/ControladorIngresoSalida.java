package com.dperez.inalerlab.suministro.lote;

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
    
    /**
     * Crea un ingreso en la base de datos.
     * @param FechaIngreso
     * @param CantidadIngreso
     * @param NumeroFactura
     * @param IdLoteIngreso
     * @return Retorna el id del ingreso creada. Retorna -1 si no se crea.
     */
    public int CrearIngreso(Date FechaIngreso, float CantidadIngreso, String NumeroFactura, int IdLoteIngreso){
        Ingreso ingreso = new Ingreso(FechaIngreso, CantidadIngreso, NumeroFactura, cLote.BuscarLote(IdLoteIngreso) );
        return mInSal.CrearIngreso(ingreso);
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
     * @return Retorna el id de la salida creada. Retorna -1 si no se crea.
     */
    public int CrearSalida(Date FechaSalida, float CantidadSalida, int IdLoteSalida){
        Salida salida = new Salida(FechaSalida, CantidadSalida, cLote.BuscarLote(IdLoteSalida));
        return mInSal.CrearSalida(salida);
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