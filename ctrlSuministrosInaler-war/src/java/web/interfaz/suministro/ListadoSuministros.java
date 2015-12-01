
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class ListadoSuministros implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;
    
    private List<Suministro> ListaSuministros;
    private Map<Integer, Suministro> MapSuministros;
    
    //  Getters
    public List<Suministro> getListaSuministros() {return ListaSuministros;}   
    public Map<Integer, Suministro> getMapSuministros() {return MapSuministros;}
    
    //  Setters
    public void setListaSuministros(List<Suministro> ListaSuministros) {this.ListaSuministros = ListaSuministros;}
    public void setMapSuministros(Map<Integer, Suministro> MapSuministros) {this.MapSuministros = MapSuministros;}
    
    public void verMasInfo(int IdSuministro){
        FacesContext context = FacesContext.getCurrentInstance();
        String url = context.getExternalContext().getRequestContextPath();
        try{
            context.getExternalContext().redirect(url+"/Views/Suministro/InfoSuministro.xhtml?id=" + IdSuministro);
        }catch(IOException ex){}
    }
    
    
    @PostConstruct
    public void init() {
        ListaSuministros = fSuministro.ListarSuministros();
        MapSuministros = fSuministro.ListarMapSuministrosFull();
    }
}
