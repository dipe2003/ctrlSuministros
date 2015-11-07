
package web.interfaz.operario;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.operario.permiso.Permiso;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ViewScoped
public class RegistrarOperario implements Serializable{
    @EJB
    private FacadeManejoOperario fOperario;
    @EJB
    private ControladorPermiso cPermiso;
    
    private String IdOperario;
    private String Password;
    private String RepPassword;    
    private String NombreOperario;
    private String PermisoOperario;
    private Map<String, Integer> PermisosOperarios;
    
    //  getters
    public String getIdOperario() {return IdOperario;}
    public String getPassword() {return Password;}
    public String getRepPassword() {return RepPassword;}
    public Map<String, Integer> getPermisosOperarios() {return PermisosOperarios;}
    public String getNombreOperario() {return NombreOperario;}
    public String getPermisoOperario() {return PermisoOperario;}
    
    //  setters
    public void setIdOperario(String IdOperario) {this.IdOperario = IdOperario;}
    public void setPassword(String Password) {this.Password = Password;}
    public void setRepPassword(String RepPassword) {this.RepPassword = RepPassword;}
    public void setPermisosOperarios(Map<String, Integer> PermisosOperarios) {this.PermisosOperarios = PermisosOperarios;}
    public void setNombreOperario(String NombreOperario) {this.NombreOperario = NombreOperario;}
    public void setPermisoOperario(String PermisoOperario) {this.PermisoOperario = PermisoOperario;}
    
    @PostConstruct
    public void init(){
        PermisosOperarios = new HashMap<>();
        List<Permiso> permisos = cPermiso.ListarPermisos();
        for(Permiso permiso: permisos){
            PermisosOperarios.put(permiso.getNombrePermiso(), permiso.getIdPermiso());
        }
    }
    
    public void comprobarNumeroOperario(String NumeroOperario){
        try{
            if (!fOperario.ExisteOperario(Integer.parseInt(NumeroOperario))){
                FacesContext.getCurrentInstance().addMessage("frmLogin:msjLogin", new FacesMessage("No existe ese operario"));
            }
        }catch(NumberFormatException | NullPointerException e ){
            FacesContext.getCurrentInstance().addMessage("frmLogin:msjLogin", new FacesMessage("No es un numero valido."));
        }
    }
}
