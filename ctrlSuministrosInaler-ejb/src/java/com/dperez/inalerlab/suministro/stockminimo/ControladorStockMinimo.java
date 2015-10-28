package com.dperez.inalerlab.suministro.stockminimo;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorStockMinimo implements Serializable{
    @Inject
    private ManejadorStockMinimo mStockMinimo;
   
    public StockMinimo CrearStockMinimo(float CantidadStockMinimo, Date FechaVigenciaStockMinimo){
        StockMinimo stock = new StockMinimo(CantidadStockMinimo, FechaVigenciaStockMinimo, Boolean.TRUE);
        if (mStockMinimo.CrearStockMinimo(stock)!=-1) {
            return stock;
        }
        return null;
    }    
       
    /**
     * Busca un stockMinimo segun el id especificado.
     * @param IdStockMinimo
     * @return 
     */
    public StockMinimo BuscarStockMinimo(int IdStockMinimo){
        return mStockMinimo.ObtenerStockMinimo(IdStockMinimo);
    }
    
}	