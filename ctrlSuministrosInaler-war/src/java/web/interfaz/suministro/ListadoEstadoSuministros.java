
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
public class ListadoEstadoSuministros implements Serializable{
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
    
    public void verMasInfo(int IdSuministro){
        FacesContext context = FacesContext.getCurrentInstance();
        String url = context.getExternalContext().getRequestContextPath();
        try{
            context.getExternalContext().redirect(url+"/Views/Suministro/InfoSuministro.xhtml?id=" + IdSuministro);
        }catch(IOException ex){}
    }
    
    public void filtrarLista(){
        if(!NombreSuministro.isEmpty()){
            ListaSuministros.clear();
            for(String nom: MapSuministros.keySet()){
                if(nom.toLowerCase().contains(NombreSuministro.toLowerCase())) {
                    ListaSuministros.add(MapSuministros.get(nom));
                }
            }
        }else{
            ListaSuministros = new ArrayList<>(MapSuministros.values());
        }
        
    }
    
    @PostConstruct
    public void init() {
        ListaSuministros = fSuministro.ListarSuministros(true);
        MapSuministros = new HashMap<>();
        try{
            for(Suministro sum: ListaSuministros){
                MapSuministros.put(sum.getNombreSuministro()+" ("+sum.getProveedorSuministro().getNombreProveedor() +")", sum);
            }
        }catch(IndexOutOfBoundsException ex){
            System.out.println("Error: " +ex.getMessage());
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
