
package web.interfaz.operario;

import cargainicial.carga;
import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.Operario;
import java.io.Serializable;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@SessionScoped
public class LoginOperario implements Serializable{
    @EJB
    private FacadeManejoOperario fOperario;
    @EJB
    private carga cargaDatos;
   
    private String IdOperario;
    private String Password;
    private String NombreOperario;
    private String PermisoOperario;
    private boolean Logueado;
    
    //  getters
    public String getIdOperario() {return IdOperario;}
    public String getPassword() {return Password;}
    public boolean isLogueado() {return Logueado;}
    public String getNombreOperario() {return NombreOperario;}
    public String getPermisoOperario() {return PermisoOperario;}
    
    //  setters
    public void setIdOperario(String IdOperario) {this.IdOperario = IdOperario;}
    public void setPassword(String Password) {this.Password = Password;}
    public void setLogueado(boolean Logueado) {this.Logueado = Logueado;}
    public void setNombreOperario(String NombreOperario) {this.NombreOperario = NombreOperario;}
    public void setPermisoOperario(String PermisoOperario) {this.PermisoOperario = PermisoOperario;}
    
    public void login(){
        try{
            if (fOperario.ValidarOperario(Integer.parseInt(this.IdOperario), Password)) {     
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                Operario operario = fOperario.BuscarOperario(Integer.parseInt(this.IdOperario));
                request.getSession().setAttribute("Operario", operario);
                this.NombreOperario = operario.getNombreOperario() + " " + operario.getApellidoOperario();
                PermisoOperario = operario.getPermisoOperario().getNombrePermiso();
                Logueado = true;
            }else{
                FacesContext.getCurrentInstance().addMessage("login:msjLogin", new FacesMessage("Error", "Los datos no son correctos."));
            }            
        }catch(NullPointerException | NumberFormatException ex){
            FacesContext.getCurrentInstance().addMessage("login:msjLogin", new FacesMessage("Error", "Los datos no son correctos."));
        }
    }
    
    public void logout(){
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().invalidate();
            Logueado = false;
            FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
        }catch(Exception ex){}
    }
    
    @PostConstruct
    public void init(){
        try{
            cargaDatos.cargar();
        }catch(Exception ex){
            System.out.print(Arrays.toString(ex.getStackTrace()));
        }
    }
    

}
