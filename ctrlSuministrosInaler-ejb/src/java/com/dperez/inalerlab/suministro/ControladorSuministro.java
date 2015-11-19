package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.suministro.stockminimo.StockMinimo;
import com.dperez.inalerlab.proveedor.ControladorProveedor;
import com.dperez.inalerlab.suministro.stockminimo.ControladorStockMinimo;
import com.dperez.inalerlab.suministro.unidad.ControladorUnidad;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorSuministro implements Serializable{
    @Inject
    private ManejadorSuministro mSuministro;
    
    @Inject
    private ControladorUnidad cUnidad;
    
    @Inject
    private ControladorProveedor cProveedor;
    
    @Inject
    private ControladorStockMinimo cStock;
    
    /**
     * Crea un Crea un Reactivo Quimico en la base de datos.
     * @param NombreSuministro
     * @param DescripcionSuministro
     * @param CodigoSAPSuministro
     * @param IdUnidadSuministro
     * @param IdProveedorSuministro
     * @param TipoSuministro
     * @return Devuelve el Id del suministro creado. Devuelve -1 cuando no se guardo el suministro.
     */
    public int CrearSuministro(String NombreSuministro, String DescripcionSuministro, 
            String CodigoSAPSuministro, int IdUnidadSuministro, int IdProveedorSuministro, EnumSuministro TipoSuministro){
        Suministro suministro = null;
        switch(TipoSuministro.toString()){
            case  "Material":
                suministro = new Material(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, 
                        cUnidad.BuscarUnidad(IdUnidadSuministro), cProveedor.BuscarProveedor(IdProveedorSuministro));
                break;
                
            case "MedioEnsayo":
                suministro = new MedioEnsayo(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, 
                        cUnidad.BuscarUnidad(IdUnidadSuministro), cProveedor.BuscarProveedor(IdProveedorSuministro));
                break;
                
            case "ReactivoQuimico":
                suministro = new ReactivoQuimico(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, 
                        cUnidad.BuscarUnidad(IdUnidadSuministro), cProveedor.BuscarProveedor(IdProveedorSuministro));
                break;
        }
        return mSuministro.CrearSuministro(suministro);
    }    
       
    /**
     * Busca un suministro segun el id especificado.
     * @param IdSuministro
     * @return 
     */
    public Suministro BuscarSuministro(int IdSuministro){
        return mSuministro.ObtenerSuministro(IdSuministro);
    }
    
    /**
     * Devuelve todos los suministros registrados en la base de datos.
     * Si no hay suministros devuelve una lista vacia.
     * @return 
     */
    public List<Suministro> ListarSuministros(){
        return mSuministro.ListarSuministros();
    }
    
    /**
     *  STOCK MINIMO
     */
    
    /**
     * Registrar Stock Minimo de Suministro
     * @param CantidadStockMinimo cantidad de suministro
     * @param FechaVigenteStockMinimo fecha de entrada en vigencia
     * @param IdSuministro id del suministro
     * @return 
     */
    public int RegistrarStockMinimoSuministro(float CantidadStockMinimo, Date FechaVigenteStockMinimo, int IdSuministro){
        StockMinimo stockMinimo = cStock.CrearStockMinimo(CantidadStockMinimo, FechaVigenteStockMinimo);
        Suministro suministro = mSuministro.ObtenerSuministro(IdSuministro);
        suministro.addStockMinimoSuministro(stockMinimo);
        return mSuministro.ActualizarSuministro(suministro);
    }
    
    /**
     * Devuelve los suministros del proveedore especificado por su id.
     * @param IdProveedor
     * @return Retorna un map con el nombre de los suministros (key) y sus id (value). Retorna un map vacio si no hay suministros registrados.
     */
    public Map<String, Integer> ListarSuministrosProveedor(int IdProveedor){
        return mSuministro.ListarSuministrosProveedor(IdProveedor);
    }
    
    /**
     * Devuelve los suministros registrados en la base de datos..
     * @return Retorna un map con el nombre de los suministros (key) y sus id (value). Retorna un map vacio si no hay suministros registrados.
     */
    public Map<String, Integer> ListarMapSuministros(){
        return mSuministro.ListarMapSuministros();
    }
    
    /**
     * Calcula la cantidad de suministros que están por debajo de su stock minimo.
     * Solo se toman en cuenta aquellos suministros que tengan un stock minimo definido (cantidad mayor a 0).
     * @return Retorna array[0] = total de suministros y array[1]= total de suministros debajo de stock minimo
     */
    public int[] GetTotalSuministrosDebajoStockMinimo(){
        List<Suministro> suministros = mSuministro.ListarSuministros();
        int cantidad = 0;
        for(Suministro suministro: suministros){
            if(suministro.getStock() < suministro.getStockMinimoSuministro().getCantidadStockMinimo() &&
                    suministro.getStockMinimoSuministro().getCantidadStockMinimo()>0){
                cantidad ++;
            }
        }
        return new int[]{suministros.size(), cantidad};
    }
}	