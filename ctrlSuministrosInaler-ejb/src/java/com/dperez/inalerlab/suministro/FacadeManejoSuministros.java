package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.suministro.unidad.ControladorUnidad;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
/**
 * @author dperez
 */
@Named
@Stateless
public class FacadeManejoSuministros implements Serializable {
    @Inject
    private ControladorSuministro cSuministro;
    @Inject
    private ControladorUnidad cUnidad;
    
    /**
     * Lista todos los suministros registrados en la base de datos.
     * @param Vigentes True: indica si solo se devuelven los suministros en uso.
     * @return Retorna una lista vacia si no existen operarios registrados.
     */
    public List<Suministro> ListarSuministros(boolean Vigentes){
        return cSuministro.ListarSuministros(Vigentes);
    }
    
    /**
     * Busca un suministro registrado en la base de datos.
     * El suministro debe estar registrado previamente.
     * @param IdSuministro Numero de Suministro
     * @return
     */
    public Suministro BuscarSuministro(int IdSuministro){
        return cSuministro.BuscarSuministro(IdSuministro);
    }
    
    /**
     * Registra un suministro en la base de datos.
     * @param Nombre
     * @param Descripcion
     * @param CodigoSAP
     * @param IdUnidad
     * @param IdProveedor
     * @param AvisoCambioLote
     * @param TipoSuministro
     * @return Retorna el id de suministro creado. Si no se creo retorna -1.
     */
    public int RegistrarSuministro(String Nombre, String Descripcion, String CodigoSAP,
            int IdUnidad, int  IdProveedor, boolean AvisoCambioLote, EnumSuministro TipoSuministro){
        return cSuministro.CrearSuministro(Nombre, Descripcion, CodigoSAP, IdUnidad,
                IdProveedor, TipoSuministro, AvisoCambioLote);
    }
    
    public void EnviarNotificacionCambioLote(int IdSuministro, String NumeroLoteSuministro){
        cSuministro.EnviarNotificacionCambioLote(IdSuministro, NumeroLoteSuministro);
    }
    
    /*
    *  Stock Minimo
    */
    /**
     * Registra el stock minimo para el suministro indicado.
     * @param CantidadStockMinimo
     * @param FechaVigenteStockMinimo
     * @param IdSuministro
     * @return Retorna el id del stock minimo creado. Si no se creo retorna -1.
     */
    public int RegistrarStockMinimo(float CantidadStockMinimo, Date FechaVigenteStockMinimo, int IdSuministro){
        return cSuministro.RegistrarStockMinimoSuministro(CantidadStockMinimo, FechaVigenteStockMinimo, IdSuministro);
    }
    
    /**
     *  Unidades
     */
    
    /**
     * Registra la uindad definida en la base de datos.
     * @param NombreUnidad
     * @return
     */
    public int RegistrarUnidadSuministro(String NombreUnidad){
        return cUnidad.CrearUnidad(NombreUnidad);
    }
    
    /**
     * Lista todas las unidades registradas en la base de datos.
     * @return Retorna una lista de unidades. Retorna una lista vacia si no hay unidades.
     */
    public List<Unidad> ListarUnidades(){
        return cUnidad.ListarUnidades();
    }
    
    /**
     *
     * @param IdSuministro
     * @return
     */
    public Unidad BuscarUnidadSuministro(int IdSuministro){
        return cUnidad.BuscarUnidadSuministro(IdSuministro);
    }
    
    /**
     * Calcula la cantidad de suministros que están por debajo de su stock minimo.
     * @return Retorna array[0] = total de suministros y array[1]= total de suministros debajo de stock minimo
     */
    public int[] GetTotalSuministrosDebajoStockMinimo(){
        return cSuministro.GetTotalSuministrosDebajoStockMinimo();
    }
    
    /**
     * Devuelve los suministros que están por debajo de su stock minimo.
     * Solo se toman en cuenta aquellos suministros que tengan un stock minimo definido (cantidad mayor a 0).
     * @return Retorna una lista con los ids de suministros debajo de stock minimo, retorna una lista vacia si no los hay.
     */
    public List<Integer> GetIdsSuministrosDebajoStockMinimo(){
        return cSuministro.GetIdsSuministrosDebajoStockMinimo();
    }
    
    
    /**
     * Devuelve los suministros con lotes vencidos en stock.
     * @return Retorna una lista con los ids de los suministros.
     */
    public List<Integer> getIdsSuministrosConLotesVencidos(){
        return cSuministro.getIdsSuministrosConLotesVencidos(true);
    }
    /**
     * Devuelve los suministros con lotes vencidos.
     * @return Retorna una lista con los suministros. Retorna una lista vacia si no existen lotes vencidos en stock
     */
    public List<Suministro> getSuministrosConLotesVencidos(){
        return cSuministro.getSuministrosConLotesVencidos(true);
    }
    
    /**
     * Devuelve los suministros con lotes a un mes de su vencimiento.
     * @return Retorna una lista con los suministros. Retorna una lista vacia si no existen lotes vencidos en stock.
     */
    public List<Suministro> getSuministrosUnMesVigencia(){
        return cSuministro.getSuministrosUnMesVigencia();
    }
    
    /**
     * Actualiza un suministro en la base de datos.
     * @param suministro objeto con la informacion basica del suministro (nombre, id, codigoSAP, Descripcion)
     * @param IdUnidad id de unidad de medida
     * @param IdProveedor id de proveedor
     * @param StockMinimo cantidad de stock minimo.
     * @return Retorna el id de suministro actualizado. Si no se creo retorna -1.
     */
    public int ActualizarSuministro(Suministro suministro, int IdProveedor,int IdUnidad, float StockMinimo){
        return cSuministro.ActualizarSuministro(suministro, IdProveedor, IdUnidad, StockMinimo);
    }
    
}