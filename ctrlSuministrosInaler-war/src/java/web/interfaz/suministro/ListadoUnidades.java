
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class ListadoUnidades implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;

    private List<Unidad> listaUnidades;
    
    //  Getters
    public List<Unidad> getListaUnidades() {return listaUnidades;}
    
    //  Setters
    public void setListaUnidades(List<Unidad> listaUnidades) {this.listaUnidades = listaUnidades;}

    
    @PostConstruct
    public void init() {
        listaUnidades = fSuministro.ListarUnidades();
    }   
  
}
