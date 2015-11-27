
package web.interfaz.index;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class DatosIndex implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;
    private Suministro suministro;
    private float StockSuministro;
    private int IdSuministroSeleccionado;
    private Map<String, Integer> MapSuministros;
    private Map<Integer, Suministro> MapSuministrosFull;
    private String UnidadCantidad;
    
    //  detalles stock
    private List<Suministro> ListSuministrosDebajoStock;
    
    //  detalles vencimiento
    private List<Suministro> ListSuministrosVencidos;
    
    //  Getters
    public float getStockSuministro(){return StockSuministro;}
    public Suministro getSuministro() {return suministro;}
    public String getUnidadCantidad() {return UnidadCantidad;}
    public Map<Integer, Suministro> getMapSuministrosFull() {return MapSuministrosFull;}
    public int getIdSuministroSeleccionado() {return IdSuministroSeleccionado;}
    public Map<String, Integer> getMapSuministros() {return MapSuministros;}

    public List<Suministro> getListSuministrosDebajoStock() {return ListSuministrosDebajoStock;}
    public List<Suministro> getListSuministrosVencidos() {return ListSuministrosVencidos;}
    
    //  Setters
    public void setStockSuministro(float StockSuministro){this.StockSuministro = StockSuministro;}
    public void setSuministro(Suministro suministro) {this.suministro = suministro;}
    public void setUnidadCantidad(String UnidadCantidad) {this.UnidadCantidad = UnidadCantidad;}
    public void setMapSuministrosFull(Map<Integer, Suministro> MapSuministrosFull) {this.MapSuministrosFull = MapSuministrosFull;}
    public void setIdSuministroSeleccionado(int IdSuministroSeleccionado) {this.IdSuministroSeleccionado = IdSuministroSeleccionado;}
    public void setMapSuministros(Map<String, Integer> MapSuministros) {this.MapSuministros = MapSuministros;}

    public void setListSuministrosDebajoStock(List<Suministro> ListSuministrosDebajoStock) {this.ListSuministrosDebajoStock = ListSuministrosDebajoStock;}
    public void setListSuministrosVencidos(List<Suministro> ListSuministrosVencidos) {this.ListSuministrosVencidos = ListSuministrosVencidos;    }
            
    public void cargarDatosSuministro(){
        try{            
            suministro = MapSuministrosFull.get(IdSuministroSeleccionado);
            StockSuministro = suministro.getStock();
            UnidadCantidad = suministro.getUnidadSuministro().getNombreUnidad();
        }catch(NullPointerException ex){
            StockSuministro = 0;
            UnidadCantidad = "";
        }
    }
    
    @PostConstruct
    public void init(){
        MapSuministrosFull = fSuministro.ListarMapSuministrosFull();
        MapSuministros = fSuministro.ListarMapSuministros();
        List<Integer> idsStockBajo = fSuministro.GetIdsSuministrosDebajoStockMinimo();
        ListSuministrosVencidos = new ArrayList<>();
        ListSuministrosDebajoStock = new ArrayList<>();
        for(Integer id: idsStockBajo){
            ListSuministrosDebajoStock.add(MapSuministrosFull.get(id));
        }
    }
    
    
}
