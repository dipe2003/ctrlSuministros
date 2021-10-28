
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
import java.util.ArrayList;
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
    
    private List<Suministro> ListaTodosLosSuministros;
    private List<Suministro> ListaSuministros;
    private String NombreSuministro;
    
    //  Getters
    public List<Suministro> getListaSuministros() {return ListaSuministros;}
    public String getNombreSuministro() {return NombreSuministro;}
    public List<Suministro> getListaTodosSuministros(){return this.ListaTodosLosSuministros;}
    
    //  Setters
    public void setListaSuministros(List<Suministro> ListaSuministros) {this.ListaSuministros = ListaSuministros;}
    public void setNombreSuministro(String NombreSuministro) {this.NombreSuministro = NombreSuministro;}
    public void setListaTodosLosSuministros(List<Suministro> listaTodosLosSuministros){this.ListaTodosLosSuministros = listaTodosLosSuministros;}
    
    public void filtrarLista(){
        ListaSuministros.clear();
        if(!NombreSuministro.isEmpty()){
            ListaTodosLosSuministros.stream()
                    .filter(s->s.getNombreSuministro().toLowerCase().contains(NombreSuministro.toLowerCase()))
                    .forEachOrdered(s->{
                        ListaSuministros.add(s);
                    });
        }else{
            ListaSuministros = new ArrayList<>(ListaTodosLosSuministros);
        }
    }
    
    @PostConstruct
    public void init() {
        ListaTodosLosSuministros = fSuministro.ListarSuministros(false, true);
        ListaSuministros = new ArrayList<>(ListaTodosLosSuministros);
    }
    
    public String checkTipo(int IdSuministro){
        return ListaSuministros.stream()
                .filter(suministro->suministro.getIdSuministro()==IdSuministro)
                .findFirst().get().getClass().getSimpleName();
    }
}
