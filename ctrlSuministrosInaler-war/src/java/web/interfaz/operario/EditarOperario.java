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
import java.util.stream.Collectors;
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
public class EditarOperario implements Serializable {
    
    @EJB
    private FacadeManejoOperario fOperario;
    @EJB
    private ControladorPermiso cPermiso;
    @EJB
    private ControladorSeguridad cSeg;
    @Inject
    private LoginOperario login;
    
    private final static String DEFAULT_PASSWORD = "1234";
    
    private int numeroCarga = 0;
    
    private String IdOperario;
    private String PasswordActual;
    private String PasswordNuevo;
    private String RepPassword;
    private String NombreOperario;
    private String ApellidoOperario;
    private String CorreoOperario;
    private boolean AlertasOperario;
    private boolean VigenciaOperario;
    private int PermisoOperario;
    private Map<String, Integer> PermisosOperarios;
    List<Permiso> Permisos;
    
    private int IdOperarioSeleccionado;
    
    private String[] PasswordsOperario;
    
    //  getters
    public String getIdOperario() {
        return IdOperario;
    }
    
    public String getPasswordNuevo() {
        return PasswordNuevo;
    }
    
    public String getRepPassword() {
        return RepPassword;
    }
    
    public Map<String, Integer> getPermisosOperarios() {
        return PermisosOperarios;
    }
    
    public String getNombreOperario() {
        return NombreOperario;
    }
    
    public int getPermisoOperario() {
        return PermisoOperario;
    }
    
    public List<Permiso> getPermisos() {
        return Permisos;
    }
    
    public String getApellidoOperario() {
        return ApellidoOperario;
    }
    
    public int getIdOperarioSeleccionado() {
        return IdOperarioSeleccionado;
    }
      
    public String getPasswordActual() {
        return PasswordActual;
    }
    
    public String getCorreoOperario() {
        return CorreoOperario;
    }
    
    public boolean isAlertasOperario() {
        return AlertasOperario;
    }
    
    public boolean isVigenciaOperario() {
        return VigenciaOperario;
    }
    
    //  setters
    public void setIdOperario(String IdOperario) {
        this.IdOperario = IdOperario;
    }
    
    public void setPasswordNuevo(String PasswordNuevo) {
        this.PasswordNuevo = PasswordNuevo;
    }
    
    public void setRepPassword(String RepPassword) {
        this.RepPassword = RepPassword;
    }
    
    public void setPermisosOperarios(Map<String, Integer> PermisosOperarios) {
        this.PermisosOperarios = PermisosOperarios;
    }
    
    public void setNombreOperario(String NombreOperario) {
        this.NombreOperario = NombreOperario;
    }
    
    public void setPermisoOperario(int PermisoOperario) {
        this.PermisoOperario = PermisoOperario;
    }
    
    public void setPermisos(List<Permiso> Permisos) {
        this.Permisos = Permisos;
    }
    
    public void setApellidoOperario(String ApellidoOperario) {
        this.ApellidoOperario = ApellidoOperario;
    }
    
    public void setIdOperarioSeleccionado(int IdOperarioSeleccionado) {
        this.IdOperarioSeleccionado = IdOperarioSeleccionado;
    }
    
    public void setPasswordActual(String PasswordActual) {
        this.PasswordActual = PasswordActual;
    }
    
    public void setCorreoOperario(String CorreoOperario) {
        this.CorreoOperario = CorreoOperario;
    }
    
    public void setAlertasOperario(boolean AlertasOperario) {
        this.AlertasOperario = AlertasOperario;
    }
    
    public void setVigenciaOperario(boolean VigenciaOperario) {
        this.VigenciaOperario = VigenciaOperario;
    }
    
    /**
     * Guarda los datos modificados del operario. Se comprueban que sean
     * correctas las contraseñas (si se modificaron).
     *
     * @throws IOException
     */
    public void editarOperario() throws IOException {
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        
        if ((fOperario.ModificarDatosOperario(Integer.parseInt(IdOperario), NombreOperario, ApellidoOperario, CorreoOperario, AlertasOperario)) != -1) {
            fOperario.AgregarPermiso(Integer.parseInt(IdOperario), PermisoOperario);
            
            if (login.getIdOperario().equals(IdOperario)) {
                login.ActualizarInfoLogin(IdOperario);
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect(url + "/Views/Operario/ListadoOperarios.xhtml");
        } else {
            String msj = "No se pudo actualizar.";
            FacesContext.getCurrentInstance().addMessage("frmEditOp:btnEditarOperario", new FacesMessage(msj));
            FacesContext.getCurrentInstance().renderResponse();
        }
    }
    
    /**
     * Guarda el valor de la constante DEFAULT_PASSWORD como nuevo password del operario.
     *
     * @throws IOException
     */
    public void resetPassword() throws IOException {
        String msj = "";
        if ((fOperario.ModificarPassword(Integer.parseInt(IdOperario), DEFAULT_PASSWORD)) != -1) {
            msj = "Se actualizó la contraseña a: " + DEFAULT_PASSWORD;
            FacesContext.getCurrentInstance().addMessage("frmEditOp:btnResetPassword", new FacesMessage(msj));
        }
        msj = "No se pudo actualizar.";
        FacesContext.getCurrentInstance().addMessage("frmEditOp:btnResetPassword", new FacesMessage(msj));
        FacesContext.getCurrentInstance().renderResponse();
    }
    
    /**
     * Guarda el valor del nuevo password del operario.
     *
     * @throws IOException
     */
    public void actualizarPassword() throws IOException {
        String msj = getMensajePass();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        
        if (msj.equals("cambio")) {
            if (fOperario.ModificarPassword(Integer.parseInt(IdOperario), PasswordNuevo) == -1) {
                FacesContext.getCurrentInstance().addMessage("frmEditOp:btnActualizarPass", new FacesMessage(msj));
            } else {
                if (login.getIdOperario().equals(IdOperario)) {
                    login.ActualizarInfoLogin(IdOperario);
                }
                FacesContext.getCurrentInstance().getExternalContext().redirect(url + "/Views/Operario/ListadoOperarios.xhtml");
            }
        }else{
            FacesContext.getCurrentInstance().addMessage("frmEditOp:btnActualizarPass", new FacesMessage(msj));
        }
    }
    
    /**
     * Guarda el valor de la constante DEFAULT_PASSWORD como nuevo password del operario.
     *
     * @param nuevaVigencia
     * @throws IOException
     */
    public void cambiarVigencia(boolean nuevaVigencia) throws IOException {
        String msj = "No se pudo actualizar.";
        if ((fOperario.CambiarVigenciaOperario(Integer.parseInt(IdOperario), nuevaVigencia)) != -1) {
            msj = "Se actualizó la vigencia";
        }
        if(nuevaVigencia){
            FacesContext.getCurrentInstance().addMessage("frmEditOp:btnDarAlta", new FacesMessage(msj));
        }else{
            FacesContext.getCurrentInstance().addMessage("frmEditOp:btnDarBaja", new FacesMessage(msj));
        }
        FacesContext.getCurrentInstance().renderResponse();
    }
    
    @PostConstruct
    public void init() {
        PermisosOperarios = new HashMap<>();
        Permisos = cPermiso.ListarPermisos();
        PermisosOperarios = Permisos.stream()
                .collect(Collectors.toMap(Permiso::getNombrePermiso, permiso -> permiso.getIdPermiso()));
        PasswordsOperario = new String[2];
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            IdOperario = request.getParameter("id");
            cargarDatosOperario();
        } catch (NullPointerException | NumberFormatException ex) {
        }
    }
    
    /**
     * Carga los datos del operario a modificar.
     */
    private void cargarDatosOperario() {
        Operario op = fOperario.BuscarOperario(Integer.parseInt(IdOperario));
        NombreOperario = op.getNombreOperario();
        ApellidoOperario = op.getApellidoOperario();
        PermisoOperario = op.getPermisoOperario().getIdPermiso();
        PasswordsOperario[0] = op.getPasswordKeyOperario();
        PasswordsOperario[1] = op.getPasswordOperario();
        CorreoOperario = op.getCorreoOperario();
        AlertasOperario = op.isRecibeAlertas();
        VigenciaOperario = op.isEsVigente();
    }
    
    /**
     * Realiza las comprobaciones sobre las contraseñas y devuelve los mensajes
     * correspondientes.
     *
     * @return
     */
    public String getMensajePass() {
        numeroCarga++;
        if(numeroCarga>1){
            try {
                if (PasswordNuevo.isEmpty() | RepPassword.isEmpty()) {
                    return "Ingresa contraseña.";
                } else {
                    if (!PasswordNuevo.equals(RepPassword)) {
                        return "Las contraseñas no coinciden.";
                    } else {
                        String nuevoPass = cSeg.getPasswordSeguro(PasswordActual, PasswordsOperario[0]);
                        if (!nuevoPass.equals(PasswordsOperario[1])) {
                            return "La contraseña ingresada no coincide con la actual";
                        } else {
                            return "cambio";
                        }
                    }
                }
            }
            catch (NullPointerException ex) {
                return "Ingresa contraseñas.";
            }
        }
        return "";
    }
    
    public void generarCorreo() {
        CorreoOperario = NombreOperario.toLowerCase() + "." + ApellidoOperario.toLowerCase() + "@marfrig.com";
    }
}
