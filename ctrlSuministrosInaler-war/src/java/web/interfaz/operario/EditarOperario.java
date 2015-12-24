
package web.interfaz.operario;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.operario.permiso.Permiso;
import com.dperez.inalerlab.operario.seguridad.ControladorSeguridad;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ViewScoped
public class EditarOperario implements Serializable{
    @EJB
    private FacadeManejoOperario fOperario;
    @EJB
    private ControladorPermiso cPermiso;
    @EJB
    private ControladorSeguridad cSeg;
    @Inject
    private LoginOperario login;
    
    private String IdOperario;
    private String PasswordActual;
    private String PasswordNuevo;
    private String RepPassword;
    private String NombreOperario;
    private String ApellidoOperario;
    private int PermisoOperario;
    private Map<String, Integer> PermisosOperarios;
    List<Permiso> Permisos;
    
    private int IdOperarioSeleccionado;
    private Map<String, Integer> NombresCompletosOperarios;
    
    private String[] PasswordsOperario;
    
    //  getters
    public String getIdOperario() {return IdOperario;}
    public String getPasswordNuevo() {return PasswordNuevo;}
    public String getRepPassword() {return RepPassword;}
    public Map<String, Integer> getPermisosOperarios() {return PermisosOperarios;}
    public String getNombreOperario() {return NombreOperario;}
    public int getPermisoOperario() {return PermisoOperario;}
    public List<Permiso> getPermisos() {return Permisos;}
    public String getApellidoOperario() {return ApellidoOperario;}
    public int getIdOperarioSeleccionado() {return IdOperarioSeleccionado;}
    public Map<String, Integer> getNombresCompletosOperarios() {return NombresCompletosOperarios;}
    public String getPasswordActual() {return PasswordActual;}
    
    //  setters
    public void setIdOperario(String IdOperario) {this.IdOperario = IdOperario;}
    public void setPasswordNuevo(String PasswordNuevo) {this.PasswordNuevo = PasswordNuevo;}
    public void setRepPassword(String RepPassword) {this.RepPassword = RepPassword;}
    public void setPermisosOperarios(Map<String, Integer> PermisosOperarios) {this.PermisosOperarios = PermisosOperarios;}
    public void setNombreOperario(String NombreOperario) {this.NombreOperario = NombreOperario;}
    public void setPermisoOperario(int PermisoOperario) {this.PermisoOperario = PermisoOperario;}
    public void setPermisos(List<Permiso> Permisos) {this.Permisos = Permisos;}
    public void setApellidoOperario(String ApellidoOperario) {this.ApellidoOperario = ApellidoOperario;}
    public void setIdOperarioSeleccionado(int IdOperarioSeleccionado) {this.IdOperarioSeleccionado = IdOperarioSeleccionado;}
    public void setNombresCompletosOperarios(Map<String, Integer> NombresCompletosOperarios) {this.NombresCompletosOperarios = NombresCompletosOperarios;}
    public void setPasswordActual(String PasswordActual) {this.PasswordActual = PasswordActual;}
    
    /**
     * Guarda los datos modificados del operario.
     * Se comprueban que sean correctas las contraseñas (si se modificaron).
     * @throws IOException
     */
    public void editarOperario() throws IOException{
        String msj= getMensajePass();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if(msj.equals("Ok") || msj.equals("cambio")){
            if((fOperario.ModificarDatosOperario(Integer.parseInt(IdOperario), NombreOperario, ApellidoOperario))!=-1){
                fOperario.AgregarPermiso(Integer.parseInt(IdOperario), PermisoOperario);
                if(msj.equals("cambio")) {
                    msj = "Se actualizaron los datos pero no se pudo cambiar la contraseña.";
                    if(fOperario.ModificarPassword(Integer.parseInt(IdOperario),PasswordNuevo)==-1){
                        FacesContext.getCurrentInstance().addMessage("frmEditOp:btnEditarOperario", new FacesMessage(msj));
                    }else{
                        FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
                    }
                }
                if(login.getIdOperario().equals(IdOperario)){
                    login.ActualizarInfoLogin(IdOperario);
                }
                FacesContext.getCurrentInstance().getExternalContext().redirect(url+"/Views/Operario/ListadoOperarios.xhtml");
            }else{
                msj = "No se pudo actualizar.";
                FacesContext.getCurrentInstance().addMessage("frmEditOp:btnEditarOperario", new FacesMessage(msj));
                FacesContext.getCurrentInstance().renderResponse();
            }
        }else{
            FacesContext.getCurrentInstance().addMessage("frmEditOp:btnEditarOperario", new FacesMessage(msj));
            FacesContext.getCurrentInstance().renderResponse();
        }
    }
    
    
    @PostConstruct
    public void init(){
        PermisosOperarios = new HashMap<>();
        Permisos = cPermiso.ListarPermisos();
        for(Permiso permiso: Permisos){
            PermisosOperarios.put(permiso.getNombrePermiso(), permiso.getIdPermiso());
        }
        NombresCompletosOperarios = fOperario.GetNombresOperarios();
        PasswordsOperario = new String[2];
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            IdOperario = request.getParameter("id");
            cargarDatosOperario();
        }catch(NullPointerException | NumberFormatException ex){}
    }
    
    /**
     * Carga los datos del operario a modificar.
     */
    private void cargarDatosOperario(){
        Operario op = fOperario.BuscarOperario(Integer.parseInt(IdOperario));
        NombreOperario = op.getNombreOperario();
        ApellidoOperario = op.getApellidoOperario();
        PermisoOperario = op.getPermisoOperario().getIdPermiso();
        PasswordsOperario[0] = op.getPasswordKeyOperario();
        PasswordsOperario[1] = op.getPasswordOperario();
    }
    
    /**
     * Realiza las comprobaciones sobre las contraseñas y devuelve los mensajes correspondientes.
     * @return
     */
    public String getMensajePass(){
        try{
            if(!(PasswordNuevo.isEmpty() && RepPassword.isEmpty())){
                if(PasswordNuevo.isEmpty() | RepPassword.isEmpty()){
                    return "Ingresa contraseña.";
                }else{
                    if(!PasswordNuevo.equals(RepPassword)){
                        return "Las contraseñas no coinciden.";
                    }else{
                        String nuevoPass =  cSeg.getPasswordSeguro(PasswordActual, PasswordsOperario[0]);
                        if(!nuevoPass.equals(PasswordsOperario[1])){
                            return "La contraseña ingresada no coincide con la actual";
                        }else{
                            return "cambio";
                        }
                    }
                }
            }
        }catch(NullPointerException ex){
            return "Ingresa contraseñas.";
        }
        return "Ok";
    }
}
