
package web.interfaz.index;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class GraficosIndex implements Serializable {
   @EJB
   private FacadeManejoSuministros fSuministro;
   
   private int TotalSuministrosDebajoStockMinimo;
   private int TotalSuministrosRegistrados;
   
   private int TotalSuministrosLotesVencidosStock;
   
   //   Getters
    public int getTotalSuministrosDebajoStockMinimo() {return TotalSuministrosDebajoStockMinimo;}
    public int getTotalSuministrosRegistrados() {return TotalSuministrosRegistrados;}
    public int getTotalSuministrosLotesVencidosStock() {return TotalSuministrosLotesVencidosStock;}
    
    //  Setters
    public void setTotalSuministrosDebajoStockMinimo(int TotalSuministrosDebajoStockMinimo) {this.TotalSuministrosDebajoStockMinimo = TotalSuministrosDebajoStockMinimo;}
    public void setTotalSuministrosRegistrados(int TotalSuministrosRegistrados) {this.TotalSuministrosRegistrados = TotalSuministrosRegistrados;}
    public void setTotalSuministrosLotesVencidosStock(int TotalSuministrosLotesVencidosStock) {this.TotalSuministrosLotesVencidosStock = TotalSuministrosLotesVencidosStock;}
    
    @PostConstruct
    public void init(){
        int[] arr = fSuministro.GetTotalSuministrosDebajoStockMinimo();
        TotalSuministrosRegistrados = arr[0];
        TotalSuministrosDebajoStockMinimo = arr[1];
        
        TotalSuministrosLotesVencidosStock = fSuministro.getMapSuministrosConLotesVencidos().size();
        
    }
    
    
}
