
package web.interfaz.proveedor;

import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
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
public class ListadoProveedores implements Serializable{
    @EJB
    private FacadeManejoProveedor fProveedor;
    
    private Map<String, Proveedor> MapProveedor;
    private List<Proveedor> ListaProveedor;
    private String NombreProveedor;
    
    //  Getters
    public Map<String, Proveedor> getMapProveedor() {return MapProveedor;}
    public List<Proveedor> getListaProveedor() {return ListaProveedor;}
    public String getNombreProveedor() {return NombreProveedor;} 
    
    //  Setters
    public void setMapProveedor(Map<String, Proveedor> MapProveedor) {this.MapProveedor = MapProveedor;}
    public void setListaProveedor(List<Proveedor> ListaProveedor) {this.ListaProveedor = ListaProveedor;}
    public void setNombreProveedor(String NombreProveedor) {this.NombreProveedor = NombreProveedor;}    
    
    public void filtrarLista(){
        if(!NombreProveedor.isEmpty()){
            ListaProveedor.clear();
            for(String nom: MapProveedor.keySet()){
                if(nom.toLowerCase().contains(NombreProveedor.toLowerCase())) {
                    ListaProveedor.add(MapProveedor.get(nom));
                }
            }
        }else{
            ListaProveedor = new ArrayList<>(MapProveedor.values());
        }
        
    }
    
    @PostConstruct
    public void init() {
        ListaProveedor = fProveedor.ListarProveedores();
        MapProveedor = new HashMap<>();
        for(Proveedor prov: ListaProveedor){
            MapProveedor.put(prov.getNombreProveedor() + "-" + prov.getContactoProveedor() , prov);
        }
    }    
    
}
