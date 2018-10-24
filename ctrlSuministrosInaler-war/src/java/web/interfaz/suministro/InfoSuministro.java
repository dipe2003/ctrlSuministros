
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import com.dperez.inalerlab.suministro.lote.Lote;
import com.dperez.inalerlab.suministro.lote.Salida;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class InfoSuministro implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;
    
    private Suministro SuministroSeleccionado;
    private List<Lote> LotesSuministro;
    private Map<Integer, List<Ingreso>> IngresosSuministro;
    private Map<Integer, List<Salida>> SalidasSuministro;
    private Map<Integer, Boolean> LotesVencido;
    
    //  Getters
    public List<Lote> getLotesSuministro() {return LotesSuministro;}
    public Map<Integer, List<Ingreso>> getIngresosSuministro() {return IngresosSuministro;}
    public Map<Integer, List<Salida>> getSalidasSuministro() {return SalidasSuministro;}
    public Suministro getSuministroSeleccionado() {return SuministroSeleccionado;}
    public Map<Integer, Boolean> getLotesVencido() {return LotesVencido;}
    
    //  Setters
    public void setLotesSuministro(List<Lote> LotesSuministro) {this.LotesSuministro = LotesSuministro;}
    public void setIngresosSuministro(Map<Integer, List<Ingreso>> IngresosSuministro) {this.IngresosSuministro = IngresosSuministro;}
    public void setSalidasSuministro(Map<Integer, List<Salida>> SalidasSuministro) {this.SalidasSuministro = SalidasSuministro;}
    public void setSuministroSeleccionado(Suministro SuministroSeleccionado) {this.SuministroSeleccionado = SuministroSeleccionado;}
    public void setLotesVencido(Map<Integer, Boolean> LotesVencido) {this.LotesVencido = LotesVencido;}
        
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        int id = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("id"));
        SuministroSeleccionado = fSuministro.BuscarSuministro(id);
        LotesSuministro = SuministroSeleccionado.getLotesSuministros();
        IngresosSuministro = new HashMap<>();
        SalidasSuministro = new HashMap<>();
        LotesVencido = new HashMap<>();
        
        LotesSuministro.stream()
                .forEachOrdered(lote-> {
                    IngresosSuministro.put(lote.getIdLote(), lote.getIngresosLote());
                    SalidasSuministro.put(lote.getIdLote(), lote.getSalidasLote());
                    LotesVencido.put(lote.getIdLote(), lote.EstaVencido());
                });
    }
}
