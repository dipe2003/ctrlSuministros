
package web.interfaz.operario;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.Operario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class ListadoOperarios implements Serializable{
    @EJB
    private FacadeManejoOperario fOperario;
    
    private Map<String, Operario> MapOperario;
    private List<Operario> ListaOperario;
    private String NombreOperario;
    
    //  Getters
    public Map<String, Operario> getMapOperario() {return MapOperario;}
    public List<Operario> getListaOperario() {return ListaOperario;}
    public String getNombreOperario() {return NombreOperario;} 
    
    //  Setters
    public void setMapOperario(Map<String, Operario> MapOperario) {this.MapOperario = MapOperario;}
    public void setListaOperario(List<Operario> ListaOperario) {this.ListaOperario = ListaOperario;}
    public void setNombreOperario(String NombreOperario) {this.NombreOperario = NombreOperario;}    
    
    public void filtrarLista(){
        if(!NombreOperario.isEmpty()){
            ListaOperario.clear();
            for(String nom: MapOperario.keySet()){
                if(nom.toLowerCase().contains(NombreOperario.toLowerCase())) {
                    ListaOperario.add(MapOperario.get(nom));
                }
            }
        }else{
            ListaOperario = new ArrayList<>(MapOperario.values());
        }
        
    }
    
    @PostConstruct
    public void init() {
        ListaOperario = fOperario.ListarOperarios();
        MapOperario = new HashMap<>();
        for(Operario op: ListaOperario){
            MapOperario.put(op.getNombreOperario()+" "+op.getApellidoOperario()+"-"+String.valueOf(op.getIdOperario()) , op);
        }
    }    
    
}
