package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.suministro.unidad.ControladorUnidad;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
     * @return Retorna una lista vacia si no existen operarios registrados.
     */
    public List<Suministro> ListarSuministros(){
        return cSuministro.ListarSuministros();
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
     * Comprueba la existencia de un suministro registrado en la base de datos según el id especificado.
     * @param IdSuministro Numero de Suministro
     * @return Retorna <b>True</b> si existe el suministro.
     */
    public boolean ExisteSuministro(int IdSuministro){
        return cSuministro.BuscarSuministro(IdSuministro)!=null;
    }
    
    /**
     * Registra un material en la base de datos.
     * @param NombreSuministro
     * @param DescripcionSuministro
     * @param CodigoSAPSuministro
     * @param IdUnidadSuministro
     * @param IdProveedorSuministro
     * @return Retorna el id de suministro creado. Si no se creo retorna -1.
     */
    public int RegistrarMaterial(String NombreSuministro, String DescripcionSuministro, String CodigoSAPSuministro,
            int IdUnidadSuministro, int  IdProveedorSuministro){
        return cSuministro.CrearSuministro(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, IdUnidadSuministro,
                IdProveedorSuministro, EnumSuministro.Material);
    }
    /**
     * Registra un medio de ensayo en la base de datos.
     * @param NombreSuministro
     * @param DescripcionSuministro
     * @param CodigoSAPSuministro
     * @param IdUnidadSuministro
     * @param IdProveedorSuministro
     * @return Retorna el id de suministro creado. Si no se creo retorna -1.
     */
    public int RegistrarMedioEnsayo(String NombreSuministro, String DescripcionSuministro, String CodigoSAPSuministro,
            int IdUnidadSuministro, int  IdProveedorSuministro){
        return cSuministro.CrearSuministro(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, IdUnidadSuministro,
                IdProveedorSuministro, EnumSuministro.MedioEnsayo);
    }
    /**
     * Registra un reactivo quimico en la base de datos.
     * @param NombreSuministro
     * @param DescripcionSuministro
     * @param CodigoSAPSuministro
     * @param IdUnidadSuministro
     * @param IdProveedorSuministro
     * @return Retorna el id de suministro creado. Si no se creo retorna -1.
     */
    public int RegistrarReactivoQuimico(String NombreSuministro, String DescripcionSuministro, String CodigoSAPSuministro,
            int IdUnidadSuministro, int  IdProveedorSuministro){
        return cSuministro.CrearSuministro(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, IdUnidadSuministro,
                IdProveedorSuministro, EnumSuministro.ReactivoQuimico);
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
     * Devuelve los suministros del proveedore especificado por su id.
     * @param IdProveedor
     * @return Retorna un map con el nombre de los suministros (key) y sus id (value). Retorna un map vacio si no hay suministros registrados.
     */
    public Map<String, Integer> ListarSuministrosProveedor(int IdProveedor){
        return cSuministro.ListarSuministrosProveedor(IdProveedor);
    }
    
    /**
     * Devuelve los suministros registrados en la base de datos..
     * @return Retorna un map con el nombre de los suministros (key) y sus id (value). Retorna un map vacio si no hay suministros registrados.
     */
    public Map<String, Integer> ListarMapSuministros(){
        return cSuministro.ListarMapSuministros();
    }
    
    /**
     * Calcula la cantidad de suministros que están por debajo de su stock minimo.
     * @return Retorna array[0] = total de suministros y array[1]= total de suministros debajo de stock minimo
     */
    public int[] GetTotalSuministrosDebajoStockMinimo(){
        return cSuministro.GetTotalSuministrosDebajoStockMinimo();
    }
    
    /**
     * Devuelve un map con los suministros vencidos en stock
     * @return Retorna un map con el nombre de los suministros (key) y sus id (value). Retorna un map vacio si no hay suministros registrados.
     */
    public Map<String, Integer> getMapSuministrosConLotesVencidosEnStock(){
        return cSuministro.getMapSuministrosConLotesVencidos(true);
    }
    
    /**
     * Devuelve un map con los suministros vencidos.
     * @return Retorna un map con el nombre de los suministros (key) y sus id (value). Retorna un map vacio si no hay suministros registrados.
     */
    public Map<String, Integer> getMapSuministrosConLotesVencidos(){
        return cSuministro.getMapSuministrosConLotesVencidos(false);
    }
    
}