
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.Lote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
    
    //  Getters
    public List<Lote> getLotesSuministro() {return LotesSuministro;}
    public Suministro getSuministroSeleccionado() {return SuministroSeleccionado;}
    
    //  Setters
    public void setLotesSuministro(List<Lote> LotesSuministro) {this.LotesSuministro = LotesSuministro;}
    public void setSuministroSeleccionado(Suministro SuministroSeleccionado) {this.SuministroSeleccionado = SuministroSeleccionado;}
    
    public void cargarDatosLotes(){
        LotesSuministro = SuministroSeleccionado.getLotesSuministros().stream()
                .filter((Lote l)-> !LotesSuministro.contains(l))
                .sorted(Comparator.comparing((Lote lote)->lote.getIdLote()))
                .collect(Collectors.toList());
    }
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        int id = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("id"));
        SuministroSeleccionado = fSuministro.BuscarSuministro(id);
        LotesSuministro = new ArrayList<>();
        cargarDatosLotes();
    }
}
