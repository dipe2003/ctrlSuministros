
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    
    private List<Suministro> Suministros;
    private List<Suministro> ListaSuministros;
    private String NombreSuministro;
    
    //  Getters
    public List<Suministro> getListaSuministros() {return ListaSuministros;}
    public List<Suministro> getSuministros(){return this.Suministros;}
    public String getNombreSuministro() {return NombreSuministro;}
    
    //  Setters
    public void setListaSuministros(List<Suministro> ListaSuministros) {this.ListaSuministros = ListaSuministros;}
    public void setNombreSuministro(String NombreSuministro) {this.NombreSuministro = NombreSuministro;}
    public void setSuministros(List<Suministro> suministros){this.Suministros = suministros;}   
    
    public void verMasInfo(int IdSuministro){
        FacesContext context = FacesContext.getCurrentInstance();
        String url = context.getExternalContext().getRequestContextPath();
        try{
            context.getExternalContext().redirect(url+"/Views/Suministro/InfoSuministro.xhtml?id=" + IdSuministro);
        }catch(IOException ex){}
    }
    
    /**
     * llena la lista de suministros según el nombre ingresado.
     */
    public void filtrarLista(){
        ListaSuministros.clear();
        if(!NombreSuministro.isEmpty()){
           ListaSuministros= Suministros.stream()
                    .filter(s->s.getNombreSuministro().toLowerCase().contains(NombreSuministro.toLowerCase()))
                    .collect(Collectors.toList());
        }else{
            ListaSuministros = new ArrayList<>(Suministros);
        }  
    }
    
    @PostConstruct
    public void init() {
        Suministros = fSuministro.ListarSuministros(true);
        ListaSuministros = new ArrayList<>(Suministros);
    }
    
}
