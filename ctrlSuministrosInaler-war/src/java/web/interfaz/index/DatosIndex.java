
package web.interfaz.index;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
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
    private int IdSuministro;
    private Map<String, Integer> MapSuministros;
    private float StockSuministro;
    
    private String UnidadCantidad;
    
    //  Getters
    public int getIdSuministro() {return IdSuministro;}
    public Map<String, Integer> getMapSuministros() {return MapSuministros;}
    public float getStockSuministro(){return StockSuministro;}
    public Suministro getSuministro() {return suministro;}
    public String getUnidadCantidad() {return UnidadCantidad;}
    
    //  Setters
    public void setIdSuministro(int IdSuministro) {this.IdSuministro = IdSuministro;}
    public void setMapSuministros(Map<String, Integer> MapSuministros) {this.MapSuministros = MapSuministros;}
    public void setStockSuministro(float StockSuministro){this.StockSuministro = StockSuministro;}
    public void setSuministro(Suministro suministro) {this.suministro = suministro;}
    public void setUnidadCantidad(String UnidadCantidad) {this.UnidadCantidad = UnidadCantidad;}
    
    public void cargarDatosSuministro(){
        try{
            suministro = fSuministro.BuscarSuministro(IdSuministro);
            StockSuministro = suministro.getStock();
            UnidadCantidad = suministro.getUnidadSuministro().getNombreUnidad();
        }catch(NullPointerException ex){
            StockSuministro = 0;
            UnidadCantidad = "";
        }
    }
    
    @PostConstruct
    public void init(){
        MapSuministros = fSuministro.ListarMapSuministros();
    }
    
    
}
