
package web.interfaz.index;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.Lote;
import com.dperez.inalerlab.timer.TimerService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    private TimerService timerService;
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
    private Map<Integer, List<Lote>> MapLotesVencidos;
    private List<Suministro> ListSuministroUnMesVigencia;
    private Map<Integer, List<Lote>> MapLotesUnMesVigencia;
    
    //  Getters
    public float getStockSuministro(){return StockSuministro;}
    public Suministro getSuministro() {return suministro;}
    public String getUnidadCantidad() {return UnidadCantidad;}
    public Map<Integer, Suministro> getMapSuministrosFull() {return MapSuministrosFull;}
    public int getIdSuministroSeleccionado() {return IdSuministroSeleccionado;}
    public Map<String, Integer> getMapSuministros() {return MapSuministros;}
    
    public List<Suministro> getListSuministrosDebajoStock() {return ListSuministrosDebajoStock;}
    public List<Suministro> getListSuministrosVencidos() {return ListSuministrosVencidos;}
    public Map<Integer, List<Lote>> getMapLotesVencidos() {return MapLotesVencidos;}
    public List<Suministro> getSuministroUnMesVigencia(){return ListSuministroUnMesVigencia;}
    public Map<Integer, List<Lote>> getMapLotesUnMesVigencia() {return MapLotesUnMesVigencia;}
    
    //  Setters
    public void setStockSuministro(float StockSuministro){this.StockSuministro = StockSuministro;}
    public void setSuministro(Suministro suministro) {this.suministro = suministro;}
    public void setUnidadCantidad(String UnidadCantidad) {this.UnidadCantidad = UnidadCantidad;}
    public void setMapSuministrosFull(Map<Integer, Suministro> MapSuministrosFull) {this.MapSuministrosFull = MapSuministrosFull;}
    public void setIdSuministroSeleccionado(int IdSuministroSeleccionado) {this.IdSuministroSeleccionado = IdSuministroSeleccionado;}
    public void setMapSuministros(Map<String, Integer> MapSuministros) {this.MapSuministros = MapSuministros;}
    
    public void setListSuministrosDebajoStock(List<Suministro> ListSuministrosDebajoStock) {this.ListSuministrosDebajoStock = ListSuministrosDebajoStock;}
    public void setListSuministrosVencidos(List<Suministro> ListSuministrosVencidos) {this.ListSuministrosVencidos = ListSuministrosVencidos;    }
    public void setMapLotesVencidos(Map<Integer, List<Lote>> MapLotesVencidos) {this.MapLotesVencidos = MapLotesVencidos;}
    public void setListSuministroUnMesVigencia(List<Suministro> ListSuministroUnMesVigencia){this.ListSuministroUnMesVigencia = ListSuministroUnMesVigencia;}
    public void setMapLotesUnMesVigencia(Map<Integer, List<Lote>> MapLotesUnMesVigencia) {this.MapLotesUnMesVigencia = MapLotesUnMesVigencia;}
    
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
    
    public void enviarAlertas(){
        try{
            timerService.myTimer();
        }catch(Exception ex){
            System.out.println("Error al enviar alertas: " + ex.getMessage());
        }
    }
    
    @PostConstruct
    public void init(){
        MapSuministrosFull = fSuministro.ListarMapSuministrosFull();
        MapSuministros = fSuministro.ListarMapSuministros(true);
        List<Integer> idsStockBajo = fSuministro.GetIdsSuministrosDebajoStockMinimo();
        ListSuministrosVencidos = fSuministro.getSuministrosConLotesVencidos();
        ListSuministrosDebajoStock = new ArrayList<>();
        MapLotesVencidos = new HashMap<>();
        for(Integer id: idsStockBajo){
            Suministro sum = MapSuministrosFull.get(id);
            if(sum.isVigente())ListSuministrosDebajoStock.add(sum);
        }
        for(Suministro sum: ListSuministrosVencidos){
            if(sum.isVigente())
                MapLotesVencidos.put(sum.getIdSuministro(), sum.getLotesVencidosEnStock());
        }
        
        ListSuministroUnMesVigencia = fSuministro.getSuministrosUnMesVigencia();
        MapLotesUnMesVigencia = new HashMap<>();
        Iterator<Suministro> it = ListSuministroUnMesVigencia.iterator();
        while(it.hasNext()){
            Suministro sum = it.next();
            if(!sum.isVigente()){
                it.remove();
            }else{
                MapLotesUnMesVigencia.put(sum.getIdSuministro(), sum.getLotesUnMesVigenciaEnStock());
            }
        }
    }
    
    
}
