
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ListadoSuministros implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;

    private List<Suministro> ListaSuministros;

    //  Getters
    public List<Suministro> getListaSuministros() {return ListaSuministros;}   
    
    //  Setters
    public void setListaSuministros(List<Suministro> ListaSuministros) {this.ListaSuministros = ListaSuministros;}
    
    @PostConstruct
    public void init() {
        ListaSuministros = fSuministro.ListarSuministros();        
    }
}
