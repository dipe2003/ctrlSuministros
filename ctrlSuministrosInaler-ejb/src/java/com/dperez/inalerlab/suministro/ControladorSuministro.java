package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.suministro.stockminimo.StockMinimo;
import com.dperez.inalerlab.proveedor.ControladorProveedor;
import com.dperez.inalerlab.suministro.stockminimo.ControladorStockMinimo;
import com.dperez.inalerlab.suministro.unidad.ControladorUnidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
    @Inject
    private BufferSuministros buffer;
    
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
        int id = mSuministro.CrearSuministro(suministro);
        if(id!=-1){
            buffer.putSuministro(suministro);
        }
        return id;
    }
    
    /**
     * Busca un suministro segun el id especificado.
     * @param IdSuministro
     * @return
     */
    public Suministro BuscarSuministro(int IdSuministro){
        if(buffer.containsSuministro(IdSuministro)) return buffer.getSuministro(IdSuministro);
        return mSuministro.ObtenerSuministro(IdSuministro);
    }
    
    /**
     * Devuelve todos los suministros registrados en la base de datos.
     * Si no hay suministros devuelve una lista vacia.
     * @param Vigente True: indica si solo se devuelven los suministros en uso.
     * @param UsarBuffer True: indica si se utiliza el buffer (False para solucionar error de contexto en Timer singleton).
     * @return
     */
    public List<Suministro> ListarSuministros(boolean Vigente, boolean UsarBuffer){
        if(UsarBuffer){
            if(buffer.bufferSize()>0) return buffer.getListaSuministros(Vigente);
        }
        return mSuministro.ListarSuministros(Vigente);
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
        int id = mSuministro.ActualizarSuministro(suministro);
        if(id!=-1){
            buffer.updateSuministro(suministro);
        }
        return id;
    }
    
    /**
     * Devuelve los suministros del proveedore especificado por su id.
     * @param IdProveedor
     * @return Retorna un map con el nombre de los suministros (key) y sus id (value). Retorna un map vacio si no hay suministros registrados.
     */
    public Map<String, Integer> ListarSuministrosProveedor(int IdProveedor){
        if(buffer.bufferSize()>0) return buffer.getMapSuministrosPorProveedor(IdProveedor);
        return mSuministro.ListarSuministrosProveedor(IdProveedor);
    }
    
    /**
     * Devuelve los suministros registrados en la base de datos. Solo los suministros vigentes.
     * @param Vigente
     * @return Retorna un map con el nombre de los suministros (key) y sus id (value). Retorna un map vacio si no hay suministros registrados.
     */
    public Map<String, Integer> ListarMapSuministros(boolean Vigente){
        if(buffer.bufferSize()>0) return buffer.getMapNombreSuministros(Vigente);
        return mSuministro.ListarMapSuministros(Vigente);
    }
    /**
     * Devuelve los suministros registrados en la base de datos. Solo los suministros vigentes.
     * @return Retorna un map con los ids de suministros (key) y los suministros (value). Retorna un map vacio si no hay suministros registrados.
     */
    public Map<Integer, Suministro> ListarMapSuministrosFull(){
        if(buffer.bufferSize()>0) return buffer.getMapSuministros();
        return mSuministro.ListarMapSuministrosFull();
    }
    
    /**
     * Calcula la cantidad de suministros que están por debajo de su stock minimo.
     * Solo se toman en cuenta aquellos suministros que tengan un stock minimo definido (cantidad mayor a 0) y esten vigentes.
     * @return Retorna array[0] = total de suministros y array[1]= total de suministros debajo de stock minimo
     */
    public int[] GetTotalSuministrosDebajoStockMinimo(){
        List<Suministro> suministros;
        if(buffer.bufferSize()>0){
            suministros = buffer.getListaSuministros(true);
        }else{
            suministros = mSuministro.ListarSuministros(true);
        }
        int cantidad = 0;
        for(Suministro suministro: suministros){
            if(suministro.getStock() < suministro.getStockMinimoSuministro().getCantidadStockMinimo() &&
                    suministro.getStockMinimoSuministro().getCantidadStockMinimo()>0 ){
                cantidad ++;
            }
        }
        return new int[]{suministros.size(), cantidad};
    }
    
    /**
     * Devuelve los suministros que están por debajo de su stock minimo.
     * Solo se toman en cuenta aquellos suministros que tengan un stock minimo definido (cantidad mayor a 0) y esten vigentes.
     * @return Retorna una lista con los ids de suministros debajo de stock minimo, retorna una lista vacia si no los hay.
     */
    public List<Integer> GetIdsSuministrosDebajoStockMinimo(){
        List<Suministro> suministros;
        if(buffer.bufferSize()>0){
            suministros = buffer.getListaSuministros(true);
        }else{
            suministros = mSuministro.ListarSuministros(true);
        }
        List<Integer> lista = new ArrayList<>();
        for(Suministro suministro: suministros){
            try{
                if(suministro.getStock() < suministro.getStockMinimoSuministro().getCantidadStockMinimo() &&
                        suministro.getStockMinimoSuministro().getCantidadStockMinimo()>0){
                    lista.add(suministro.getIdSuministro());
                }
            }catch(NullPointerException ex){
                System.out.println("Error: " + ex.getMessage() + " en " + suministro.getNombreSuministro() + suministro.getIdSuministro());
            }
        }
        return lista;
    }
    
    
    /**
     * Devuelve los suministros con lotes vencidos y que estén vigentes.
     * @param ConStock <b>True:</b> para obtener solo los que tengan stock. <b>False:</b> para obtener todos.
     * @return Map: key: idSuministros, value: nombreSuministro
     */
    public Map<String, Integer> getMapSuministrosConLotesVencidos(boolean ConStock){
        List<Suministro> suministros;
        if(buffer.bufferSize()>0){
            suministros = buffer.getListaSuministros(true);
        }else{
            suministros = mSuministro.ListarSuministros(true);
        }
        Map<String, Integer> map = new HashMap<>();
        if(ConStock){
            for(Suministro suministro: suministros){
                if(suministro.getLotesVencidosEnStock().size()>0) map.put(suministro.getNombreSuministro(), suministro.getIdSuministro());
            }
            return new TreeMap<>(map);
        }
        for(Suministro suministro: suministros){
            if(suministro.getLotesVencidos().size()>0 ) map.put(suministro.getNombreSuministro(), suministro.getIdSuministro());
        }
        return new TreeMap<>(map);
    }
    /**
     * Devuelve los suministros con lotes vencidos y que estén vigentes.
     * @param ConStock <b>True:</b> para obtener solo los que tengan stock. <b>False:</b> para obtener todos.
     * @return Retorna una lista con los ids de los suministros.
     */
    public List<Integer> getIdsSuministrosConLotesVencidos(boolean ConStock){
        List<Suministro> suministros;
        if(buffer.bufferSize()>0){
            suministros = buffer.getListaSuministros(true);
        }else{
            suministros = mSuministro.ListarSuministros(true);
        }
        List<Integer> lista = new ArrayList<>();
        if(ConStock){
            for(Suministro suministro: suministros){
                if(suministro.getLotesVencidosEnStock().size()>0) lista.add(suministro.getIdSuministro());
            }
            return lista;
        }
        for(Suministro suministro: suministros){
            if(suministro.getLotesVencidos().size()>0 ) lista.add(suministro.getIdSuministro());
        }
        return lista;
    }
    /**
     * Devuelve los suministros con lotes vencidos y que estén vigentes.
     * @param ConStock <b>True:</b> para obtener solo los que tengan stock. <b>False:</b> para obtener todos.
     * @return Retorna una lista con los suministros. Retorna una lista vacia si no existen lotes vencidos en stock
     */
    public List<Suministro> getSuministrosConLotesVencidos(boolean ConStock){
        List<Suministro> suministros;
        if(buffer.bufferSize()>0){
            suministros = buffer.getListaSuministros(true);
        }else{
            suministros = mSuministro.ListarSuministros(true);
        }
        List<Suministro> lista = new ArrayList<>();
        if(ConStock){
            for(Suministro suministro: suministros){
                if(suministro.getLotesVencidosEnStock().size()>0 ) lista.add(suministro);
            }
            return lista;
        }
        for(Suministro suministro: suministros){
            if(suministro.getLotesVencidos().size()>0) lista.add(suministro);
        }
        return lista;
    }
    
    /**
     * Devuelve los suministros con lotes a un mes de su vencimiento, que esten vigentes y con stock,
     * @return Lista de Suminstros.
     */
    public List<Suministro> getSuministrosUnMesVigencia(){
        List<Suministro> suministros;
        if(buffer.bufferSize()>0){
            suministros = buffer.getListaSuministros(true);
        }else{
            suministros = mSuministro.ListarSuministros(true);
        }
        List<Suministro> lista = new ArrayList<>();
        for(Suministro suministro: suministros){
            if(suministro.isVigente() && suministro.getLotesUnMesVigenciaEnStock().size()>0) lista.add(suministro);
        }
        return lista;
    }
    
    /**
     * Actualiza un suministro en la base de datos.
     * Compara unidad y proveedor y actualiza si es necesario.
     * Compara el stockminimo y si es diferente se crea con la fecha actual del sistema como vigencia.
     * @param suministro objeto con la informacion basica del suministro (nombre, id, codigoSAP, Descripcion)
     * @param IdUnidad id de unidad de medida
     * @param IdProveedor id de proveedor
     * @param StockMinimoSuministro cantidad de stock minimo.
     * @return Retorna el id de suministro actualizado. Si no se creo retorna -1.
     */
    public int ActualizarSuministro(Suministro suministro, int IdProveedor,int IdUnidad, float StockMinimoSuministro){
        Suministro sumBD;
        int id = -1;
        if(buffer.containsSuministro(suministro.getIdSuministro())){
            sumBD = buffer.getSuministro(suministro.getIdSuministro());
        }else{
            sumBD = mSuministro.ObtenerSuministro(suministro.getIdSuministro());
        }
        try{
            sumBD.setCodigoSAPSuministro(suministro.getCodigoSAPSuministro());
            sumBD.setDescripcionSuministro(suministro.getDescripcionSuministro());
            sumBD.setNombreSuministro(suministro.getNombreSuministro());
            sumBD.setVigente(suministro.isVigente());
            if(sumBD.getUnidadSuministro().getIdUnidad()!= IdUnidad){
                sumBD.setUnidadSuministro(cUnidad.BuscarUnidad(IdUnidad));
            }
            if(sumBD.getProveedorSuministro().getIdProveedor()!=IdProveedor){
                sumBD.setProveedorSuministro(cProveedor.BuscarProveedor(IdProveedor));
            }
            if(sumBD.getStockMinimoSuministro().getCantidadStockMinimo()!=StockMinimoSuministro){
                StockMinimo stock = cStock.CrearStockMinimo(StockMinimoSuministro, Calendar.getInstance().getTime());
                sumBD.addStockMinimoSuministro(stock);
            }
            
            id = mSuministro.ActualizarSuministro(sumBD);
            if(id!=-1) buffer.updateSuministro(sumBD);
        }catch(NullPointerException ex){}
        return id;
    }
    
    /**
     * Actualiza el buffer de suministros con el suministro especificado.
     * @param IdSuministro 
     */
    public void ActualizarSuministroBuffer(int IdSuministro){
        buffer.updateSuministro(mSuministro.ObtenerSuministro(IdSuministro));
    }
}	