
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
import web.interfaz.herramientas.Excelsea;
import web.interfaz.herramientas.TipoLibro;

@Named
@ViewScoped
public class InfoSuministro implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;
    
    private Suministro SuministroSeleccionado;
    private List<Lote> LotesSuministro;
    private static List<Lote> LotesSuministrosSinFiltro;
    private boolean VerTodosLotes = false;
        
    //  Getters
    public List<Lote> getLotesSuministro() {return LotesSuministro;}
    public Suministro getSuministroSeleccionado() {return SuministroSeleccionado;}
    public boolean isVerTodosLotes() {return VerTodosLotes;}
    
    //  Setters
    public void setLotesSuministro(List<Lote> LotesSuministro) {this.LotesSuministro = LotesSuministro;}
    public void setSuministroSeleccionado(Suministro SuministroSeleccionado) {this.SuministroSeleccionado = SuministroSeleccionado;}
    public void setVerTodosLotes(boolean VerTodosLotes) {this.VerTodosLotes = VerTodosLotes;}    
    
    public void cargarDatosLotes(){
        if(VerTodosLotes){
            LotesSuministro = LotesSuministrosSinFiltro.stream()
                    .sorted(Comparator.comparing((Lote lote)->lote.getIdLote()))
                    .collect(Collectors.toList());
        }else{
            LotesSuministro = LotesSuministrosSinFiltro.stream()
                    .filter((Lote l)->l.getCantidadStock()>0)
                    .collect(Collectors.toList());
        }
    }
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        int id = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("id"));
        SuministroSeleccionado = fSuministro.BuscarSuministro(id);
        LotesSuministrosSinFiltro = new ArrayList<>(SuministroSeleccionado.getLotesSuministros());
        cargarDatosLotes();
    }
    
    public void excelseaInfoSuministro() {
        Excelsea excel = new Excelsea();
        excel.ExportarLibroExcelInfo(SuministroSeleccionado, "Movimientos " + SuministroSeleccionado.getNombreSuministro());
    }
}
