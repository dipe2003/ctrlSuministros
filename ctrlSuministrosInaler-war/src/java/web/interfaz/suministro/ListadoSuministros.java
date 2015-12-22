
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ListadoSuministros implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;
    
    private Map<String, Suministro> MapSuministros;
    private List<Suministro> ListaSuministros;
    private String NombreSuministro;
    
    //  Getters
    public List<Suministro> getListaSuministros() {return ListaSuministros;}
    public String getNombreSuministro() {return NombreSuministro;}
    
    //  Setters
    public void setListaSuministros(List<Suministro> ListaSuministros) {this.ListaSuministros = ListaSuministros;}
    public void setNombreSuministro(String NombreSuministro) {this.NombreSuministro = NombreSuministro;}
    
    public void filtrarLista(){
        ListaSuministros.clear();
        if(!NombreSuministro.isEmpty()){            
            for(String nom: MapSuministros.keySet()){
                if(nom.contains(NombreSuministro.toLowerCase())) {
                    ListaSuministros.add(MapSuministros.get(nom));
                }
            }
        }else{
            ListaSuministros = new ArrayList<>(MapSuministros.values());
        }
    }
    
    @PostConstruct
    public void init() {
        ListaSuministros = fSuministro.ListarSuministros(false);
        MapSuministros = new HashMap<>();
        for(Suministro sum: ListaSuministros){
            MapSuministros.put(sum.getNombreSuministro().toLowerCase()+" ("+sum.getProveedorSuministro().getNombreProveedor().toLowerCase()+")", sum);
        }
    }
    
    public String checkTipo(int IdSuministro){
        String clase = "";
        for(Suministro sum: ListaSuministros){
            if(sum.getIdSuministro()==IdSuministro){
                clase =  sum.getClass().getSimpleName();
                break;
            }
        }
        return clase;
    }
}
