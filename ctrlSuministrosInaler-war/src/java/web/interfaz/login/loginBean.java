
package web.interfaz.login;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class loginBean implements Serializable{
    @Inject
    private FacadeManejoOperario fOperario;
    @Inject
    private  ControladorPermiso cPermiso;
    
    private String IdOperario;
    private String Password;
    
    //  getters
    public String getIdOperario() {return IdOperario;}
    public String getPassword() {return Password;}
    
    //  setters
    public void setIdOperario(String IdOperario) {this.IdOperario = IdOperario;}
    public void setPassword(String Password) {this.Password = Password;}
    
    public void login(){
        if (fOperario.ValidarOperario(Integer.parseInt(this.IdOperario), Password)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("OK", "OK"));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("NO OK", "NO OK"));
        }
    }
    @PostConstruct
    public void init(){
        try{
            int idPermiso = cPermiso.CrearPermiso("Admin").getIdPermiso();
            int idOp = fOperario.RegistrarOperario(2425, "diego", "perez", "2017.Calidad").getIdOperario();
            fOperario.AgregarPermiso(idOp, idPermiso);
            idPermiso = cPermiso.CrearPermiso("Analista").getIdPermiso();
            idOp = fOperario.RegistrarOperario(914, "bruno", "braco", "labo.2015").getIdOperario();
            fOperario.AgregarPermiso(idOp, idPermiso);
        }catch(NullPointerException ex){
            System.out.print(ex.getStackTrace());
        }
    }
}
