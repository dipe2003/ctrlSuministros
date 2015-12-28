
package web.interfaz.operario;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.operario.permiso.Permiso;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
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
    private String ApellidoOperario; 
    private String CorreoOperario;
    private int PermisoOperario;
    private Map<String, Integer> PermisosOperarios;
    List<Permiso> Permisos;
    
    //  getters
    public String getIdOperario() {return IdOperario;}
    public String getPassword() {return Password;}
    public String getRepPassword() {return RepPassword;}
    public Map<String, Integer> getPermisosOperarios() {return PermisosOperarios;}
    public String getNombreOperario() {return NombreOperario;}
    public int getPermisoOperario() {return PermisoOperario;}
    public List<Permiso> getPermisos() {return Permisos;}
    public String getApellidoOperario() {return ApellidoOperario;}
    public String getCorreoOperario() {return CorreoOperario;}
    
    //  setters
    public void setIdOperario(String IdOperario) {this.IdOperario = IdOperario;}
    public void setPassword(String Password) {this.Password = Password;}
    public void setRepPassword(String RepPassword) {this.RepPassword = RepPassword;}
    public void setPermisosOperarios(Map<String, Integer> PermisosOperarios) {this.PermisosOperarios = PermisosOperarios;}
    public void setNombreOperario(String NombreOperario) {this.NombreOperario = NombreOperario;}
    public void setPermisoOperario(int PermisoOperario) {this.PermisoOperario = PermisoOperario;}
    public void setPermisos(List<Permiso> Permisos) {this.Permisos = Permisos;}
    public void setApellidoOperario(String ApellidoOperario) {this.ApellidoOperario = ApellidoOperario;}
    public void setCorreoOperario(String CorreoOperario) {this.CorreoOperario = CorreoOperario;}
    
    /**
     * Registra el nuevo operario con los datos ingresados.
     * @throws IOException 
     */
    public void registrarOperario() throws IOException{
        String msj="";
        if(comprobarDatosOperario().equals("ok")){
            if((fOperario.RegistrarOperario(Integer.parseInt(IdOperario), NombreOperario, ApellidoOperario, Password, CorreoOperario))!=-1){
                fOperario.AgregarPermiso(Integer.parseInt(IdOperario), PermisoOperario);
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }else{
                msj = "No se pudo registrar.";
            }
        }else{
            FacesContext.getCurrentInstance().addMessage("frmRegOp:btnRegistrar", new FacesMessage(msj));
        }
    }
    
    @PostConstruct
    public void init(){
        PermisosOperarios = new HashMap<>();
        Permisos = cPermiso.ListarPermisos();
        for(Permiso permiso: Permisos){
            PermisosOperarios.put(permiso.getNombrePermiso(), permiso.getIdPermiso());
        }
    }
    
    public String comprobarDatosOperario(){
        try{
            if (fOperario.ExisteOperario(Integer.parseInt(IdOperario))){
                return "Ya existe ese operario";
            }else{
                if(!Password.equals(RepPassword)){
                    return "Las contraseñas no coinciden.";
                }
            }
        }catch(NumberFormatException | NullPointerException e ){
            return "No es un numero de operario valido.";
        }
        return "ok";
    }
    
    public String getMensajePass(){
        try{
            if(Password.isEmpty() || RepPassword.isEmpty()){
                return "Ingresa contraseñas.";
            }else{
                if(!Password.equals(RepPassword)){
                    return "Las contraseñas no coinciden.";
                }
            }
        }catch(NullPointerException ex){
            return "Ingresa contraseñas.";
        }
        return "Ok";
    }
    
    public void generarCorreo(){
        CorreoOperario = NombreOperario.toLowerCase() +"."+ ApellidoOperario.toLowerCase() + "@marfrig.com";
    }
}
