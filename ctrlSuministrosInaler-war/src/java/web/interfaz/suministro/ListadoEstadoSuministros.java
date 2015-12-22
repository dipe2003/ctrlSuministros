
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
    private Map<String, Suministro> mapFiltroSuministros;
    private List<Suministro> ListaSuministros;
    private String NombreSuministro;
    
    //  Getters
    public List<Suministro> getListaSuministros() {return ListaSuministros;}
    public String getNombreSuministro() {return NombreSuministro;}

    public Map<String, Suministro> getMapFiltroSuministros() {
        return mapFiltroSuministros;
    }
    
    //  Setters
    public void setListaSuministros(List<Suministro> ListaSuministros) {this.ListaSuministros = ListaSuministros;}
    public void setNombreSuministro(String NombreSuministro) {this.NombreSuministro = NombreSuministro;}

    public void setMapFiltroSuministros(Map<String, Suministro> mapFiltroSuministros) {
        this.mapFiltroSuministros = mapFiltroSuministros;
    }
    
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
        if(!NombreSuministro.isEmpty()){
            mapFiltroSuministros.clear();
            //ListaSuministros.clear();
            for(String nom: MapSuministros.keySet()){
                if(nom.contains(NombreSuministro.toLowerCase())) {
                    //ListaSuministros.add(MapSuministros.get(nom));
                    mapFiltroSuministros.put(nom, MapSuministros.get(nom));
                }
            }
        }else{
            //ListaSuministros = new ArrayList<>(MapSuministros.values());
            mapFiltroSuministros = new TreeMap(MapSuministros);
        }
        
    }
    
    @PostConstruct
    public void init() {
        ListaSuministros = fSuministro.ListarSuministros(true);
        MapSuministros = new HashMap<>();
        mapFiltroSuministros = new HashMap<>();
        try{
            for(Suministro sum: ListaSuministros){
                String nombre = sum.getNombreSuministro().toLowerCase()+" ("+sum.getIdSuministro()+")";
                MapSuministros.put(nombre, sum);                
            }
            mapFiltroSuministros = new TreeMap(MapSuministros);
        }catch(IndexOutOfBoundsException ex){
            System.out.println("Error: " +ex.getMessage());
        }
    }
    
}
