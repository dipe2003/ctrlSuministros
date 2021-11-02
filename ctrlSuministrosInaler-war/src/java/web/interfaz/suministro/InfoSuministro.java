
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.Lote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
    private boolean UltimoAnio;
    
    //  Getters
    public List<Lote> getLotesSuministro() {return LotesSuministro;}
    public Suministro getSuministroSeleccionado() {return SuministroSeleccionado;}
    
    public boolean isUltimoAnio() {
        return UltimoAnio;
    }
    
    
    //  Setters
    public void setLotesSuministro(List<Lote> LotesSuministro) {this.LotesSuministro = LotesSuministro;}
    public void setSuministroSeleccionado(Suministro SuministroSeleccionado) {this.SuministroSeleccionado = SuministroSeleccionado;}
    
    public void setUltimoAnio(boolean UltimoAnio) {
        this.UltimoAnio = UltimoAnio;
    }
    
    public void cargarDatosLotes(){
        if(!UltimoAnio){
            LotesSuministro = SuministroSeleccionado.getLotesSuministros().stream()
                    .sorted(Comparator.comparing(l->l.getIdLote()))
                    .collect(Collectors.toList());
        }else{
            LotesSuministro.clear();
            SuministroSeleccionado.getLotesSuministros().stream()
                    .forEach(lote->{
                        lote.getIngresosLote().stream()
                                .forEach(ingreso->{
                                    if(ingreso.getFechaIngreso().getYear() >= new Date().getYear()){
                                        LotesSuministro.add(lote);
                                    }
                                });
                    });
        }
    }
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        int id = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("id"));
        SuministroSeleccionado = fSuministro.BuscarSuministro(id);
        LotesSuministro = new ArrayList<>();
        UltimoAnio = true;
        cargarDatosLotes();
    }
}
