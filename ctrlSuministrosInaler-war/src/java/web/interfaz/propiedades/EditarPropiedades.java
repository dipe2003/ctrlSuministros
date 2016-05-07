
package web.interfaz.propiedades;

import com.dperez.inalerlab.email.ControladorPropiedad;
import com.dperez.inalerlab.email.Propiedad;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class EditarPropiedades implements Serializable{
    @EJB
    private ControladorPropiedad cProp;
    
    private Map<String, Propiedad> mapPropiedades;
    
    //  getters
    public Map<String, Propiedad> getMapPropiedades() {return mapPropiedades;}
    
    //  setters
    public void setMapPropiedades(Map<String, Propiedad> mapPropiedades) {this.mapPropiedades = mapPropiedades;}
    
    public void guardarPropiedad(String nombrePropiedad, String valorPropiedad){
        cProp.guardarPropiedad(nombrePropiedad, valorPropiedad);
    }
    
    @PostConstruct
    public void init(){
        mapPropiedades = new HashMap<>();
        List<Propiedad> propiedades = cProp.getPropiedades();
        for(Propiedad prop: propiedades){
            mapPropiedades.put(prop.getNombrePropiedad(), prop);
        }       
    }

    
}
