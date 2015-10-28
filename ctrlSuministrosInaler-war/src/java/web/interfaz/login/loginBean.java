
package web.interfaz.login;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import javax.annotation.PostConstruct;
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
   @Inject
   private FacadeManejoSuministros fSuministro;
   @Inject
   private FacadeManejoProveedor fProveedor;
    
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
            int idPermiso = cPermiso.CrearPermiso("Admin");
            int idOp = fOperario.RegistrarOperario(2425, "diego", "perez", "2017.Calidad");
            fOperario.AgregarPermiso(idOp, idPermiso);
            
            idPermiso = cPermiso.CrearPermiso("Analista");
            idOp = fOperario.RegistrarOperario(914, "bruno", "bracco", "labo.2015");
            fOperario.AgregarPermiso(idOp, idPermiso);
            
            int idUnidad = fSuministro.RegistrarUnidadSuministro("unidades");
            int idProveedor = fProveedor.RegistrarProveedor("El Proveedor S.A.", "Fulano Perengano");           
            int idSuministro = fSuministro.RegistrarMaterial("material", "es un material de laboratorio", "1000743", idUnidad, idProveedor);
            float stock = 12.0f;
            fSuministro.RegistrarStockMinimo(stock, Calendar.getInstance().getTime(), idSuministro);
        }catch(NullPointerException ex){
            System.out.print(Arrays.toString(ex.getStackTrace()));
        }
    }
    
    public void comprobarNumeroOperario(int NumeroOperario){
        if (!fOperario.ExisteOperario(NumeroOperario)){
            FacesContext.getCurrentInstance().addMessage("frm", new FacesMessage("No existe ese operario"));
        }
    }
}
