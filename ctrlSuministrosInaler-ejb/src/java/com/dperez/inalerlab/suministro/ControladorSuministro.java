package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.proveedor.ControladorProveedor;
import com.dperez.inalerlab.suministro.lote.ControladorLote;
import com.dperez.inalerlab.suministro.unidad.ControladorUnidad;
import java.io.Serializable;
import java.util.List;
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
    private ControladorLote cLote;
    
    /**
     * Crea un Crea un Reactivo Quimico en la base de datos.
     * @param NombreSuministro
     * @param DescripcionSuministro
     * @param CodigoSAPSuministro
     * @param IdUnidadSuministro
     * @param IdProveedorSuministro
     * @return 
     */
    public Suministro CrearReactivoQuimico(String NombreSuministro, String DescripcionSuministro, 
            String CodigoSAPSuministro, int IdUnidadSuministro, int IdProveedorSuministro){
        Suministro suministro = new ReactivoQuimico(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, 
                cUnidad.BuscarUnidad(IdUnidadSuministro), cProveedor.BuscarProveedor(IdProveedorSuministro));
        if(mSuministro.CrearSuministro(suministro)!=-1){
            return suministro;
        }
        return null;
    }
    
    /**
     * Crea un Medio de Cultivo en la base de datos.
     * @param NombreSuministro
     * @param DescripcionSuministro
     * @param CodigoSAPSuministro
     * @param IdUnidadSuministro
     * @param IdProveedorSuministro
     * @return 
     */
    public Suministro CrearMedioCultivo(String NombreSuministro, String DescripcionSuministro, 
            String CodigoSAPSuministro, int IdUnidadSuministro, int IdProveedorSuministro){
        Suministro suministro = new MedioEnsayo(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, 
                cUnidad.BuscarUnidad(IdUnidadSuministro), cProveedor.BuscarProveedor(IdProveedorSuministro));
        if(mSuministro.CrearSuministro(suministro)!=-1){
            return suministro;
        }
        return null;
    }
    
    /**
     * Crea un Material en la base de datos.
     * @param NombreSuministro
     * @param DescripcionSuministro
     * @param CodigoSAPSuministro
     * @param IdUnidadSuministro
     * @param IdProveedorSuministro
     * @return 
     */
    public Suministro CrearMaterial(String NombreSuministro, String DescripcionSuministro, 
            String CodigoSAPSuministro, int IdUnidadSuministro, int IdProveedorSuministro){
        Suministro suministro = new Material(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, 
                cUnidad.BuscarUnidad(IdUnidadSuministro), cProveedor.BuscarProveedor(IdProveedorSuministro));
        if(mSuministro.CrearSuministro(suministro)!=-1){
            return suministro;
        }
        return null;
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
    
}	