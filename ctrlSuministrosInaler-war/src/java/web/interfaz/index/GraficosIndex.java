
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
   
   private float PorcientoSuministroDebajoStock;
   private float PorcientoSuministrosSobreStock;
   
   //   Getters
    public int getTotalSuministrosDebajoStockMinimo() {return TotalSuministrosDebajoStockMinimo;}
    public int getTotalSuministrosRegistrados() {return TotalSuministrosRegistrados;}

    public float getPorcientoSuministroDebajoStock() {return PorcientoSuministroDebajoStock;}
    public float getPorcientoSuministrosSobreStock() {return PorcientoSuministrosSobreStock;}   
    
    //  Setters
    public void setTotalSuministrosDebajoStockMinimo(int TotalSuministrosDebajoStockMinimo) {this.TotalSuministrosDebajoStockMinimo = TotalSuministrosDebajoStockMinimo;}
    public void setTotalSuministrosRegistrados(int TotalSuministrosRegistrados) {this.TotalSuministrosRegistrados = TotalSuministrosRegistrados;}

    public void setPorcientoSuministroDebajoStock(float PorcientoSuministroDebajoStock) {this.PorcientoSuministroDebajoStock = PorcientoSuministroDebajoStock;}
    public void setPorcientoSuministrosSobreStock(float PorcientoSuministrosSobreStock) {this.PorcientoSuministrosSobreStock = PorcientoSuministrosSobreStock;}
        
    @PostConstruct
    public void init(){
        int[] arr = fSuministro.GetTotalSuministrosDebajoStockMinimo();
        TotalSuministrosRegistrados = arr[0];
        TotalSuministrosDebajoStockMinimo = arr[1];
        
        PorcientoSuministroDebajoStock = ((float)TotalSuministrosDebajoStockMinimo/(float)TotalSuministrosRegistrados)*100;
        PorcientoSuministrosSobreStock = (((float)TotalSuministrosRegistrados-(float)TotalSuministrosDebajoStockMinimo)/(float)TotalSuministrosRegistrados)*100;
    }
    
    
}
