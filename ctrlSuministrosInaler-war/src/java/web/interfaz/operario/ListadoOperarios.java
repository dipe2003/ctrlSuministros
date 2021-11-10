
package web.interfaz.operario;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.Operario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class ListadoOperarios implements Serializable{
    @EJB
    private FacadeManejoOperario fOperario;
    
    private List<Operario> ListaOperario;
    private List<Operario> Operarios;
    private String NombreOperario;
    
    //  Getters
    public List<Operario> getListaOperario() {return ListaOperario;}
    public String getNombreOperario() {return NombreOperario;}
    public List<Operario> getOperarios(){return this.Operarios;}
    
    //  Setters
    public void setListaOperario(List<Operario> ListaOperario) {this.ListaOperario = ListaOperario;}
    public void setNombreOperario(String NombreOperario) {this.NombreOperario = NombreOperario;}
    public void setOperarios(List<Operario> operarios){this.Operarios = operarios;}
    
    public void filtrarLista(){
        if(!NombreOperario.isEmpty()){
            ListaOperario = Operarios.stream()
                    .filter((Operario operario)->operario.getNombreCompleto().toLowerCase().contains(NombreOperario))
                    .collect(Collectors.toList());
        }else{
            ListaOperario = new ArrayList<>(Operarios);
        }
    }
    
    @PostConstruct
    public void init() {        
        Operarios = fOperario.ListarOperarios();
        ListaOperario = new ArrayList<>(Operarios);
    }
}
