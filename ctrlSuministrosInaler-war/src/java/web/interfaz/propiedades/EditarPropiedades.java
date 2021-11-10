
package web.interfaz.propiedades;

import com.dperez.inalerlab.email.ControladorPropiedad;
import com.dperez.inalerlab.email.Propiedad;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;
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
        mapPropiedades = cProp.getPropiedades().stream()
                .collect(Collectors.toMap(Propiedad::getNombrePropiedad, Propiedad->Propiedad));       
    }

    
}
