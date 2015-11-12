
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class RegistrarUnidad implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;
    
    private String NombreUnidad;
    
    //  getters
    public String getNombreUnidad() {return NombreUnidad;}
    
    //  setters
    public void setNombreUnidad(String NombreUnidad) {this.NombreUnidad = NombreUnidad;}
    
    public void registrarUnidad() throws IOException{
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if (fSuministro.RegistrarUnidadSuministro(NombreUnidad)!=-1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url+"/Views/index.xhtml");
        }else{
            String msj="No se pudo registrar.";
            FacesContext.getCurrentInstance().addMessage("frmRegUnidad:btnRegistrarUnidad", new FacesMessage(msj));
        }
    }
    
}
