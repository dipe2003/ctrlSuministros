
package web.interfaz.login;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.lote.FacadeLote;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class loginBean implements Serializable{
    @EJB
    private FacadeManejoOperario fOperario;
    @EJB
    private ControladorPermiso cPermiso;
    @EJB
    private FacadeManejoSuministros fSuministro;
    @EJB
    private FacadeManejoProveedor fProveedor;
    @EJB
    private FacadeLote fLote;
        
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
            //  Operarios
            int idPermiso = cPermiso.CrearPermiso("Admin");
            int idOp = fOperario.RegistrarOperario(3002425, "diego", "perez", "2017.Calidad");
            fOperario.AgregarPermiso(idOp, idPermiso);
            
            idPermiso = cPermiso.CrearPermiso("Analista");
            idOp = fOperario.RegistrarOperario(300914, "bruno", "bracco", "labo.2015");
            fOperario.AgregarPermiso(idOp, idPermiso);
            
            //  Suministros
            int idUnidad = fSuministro.RegistrarUnidadSuministro("unidades");
            int idProveedor = fProveedor.RegistrarProveedor("El Proveedor S.A.", "Fulano Perengano");
            int idSuministro = fSuministro.RegistrarMaterial("material", "es un material de laboratorio", "1000743", idUnidad, idProveedor);
            float stock = 12.0f;
            fSuministro.RegistrarStockMinimo(stock, Calendar.getInstance().getTime(), idSuministro);
            
            idUnidad = fSuministro.RegistrarUnidadSuministro("motones");
            idProveedor = fProveedor.RegistrarProveedor("Segundo Proveedor Ltda", "Rompelotas");
            idSuministro = fSuministro.RegistrarReactivoQuimico("reactivo", "es un reactivo de laboratorio", "1000744", idUnidad, idProveedor);
            stock = 5.0f;
            fSuministro.RegistrarStockMinimo(stock, Calendar.getInstance().getTime(), idSuministro);
            
            // Lote
            Calendar fProd = Calendar.getInstance();
            fProd.set(2010, 11, 3);
            Calendar fVenc = Calendar.getInstance();
            fVenc.set(2015, 11, 3);
            int idlote = fLote.CrearLote( fProd.getTime(), fVenc.getTime(), "A001");
            fLote.AgregarLoteSuministro(idSuministro, idlote);
            
            //  Ingreso
            fLote.CrearIngreso(Calendar.getInstance().getTime(), 12.5f, "ABC 1234", idlote, 300914);
            
            //  Salida
            fLote.CrearSalida(Calendar.getInstance().getTime(), 6.25f, fLote.BuscarLotePorNumeroLote("A001").getIdLote(), 300914);
            
        }catch(NullPointerException ex){
            System.out.print(Arrays.toString(ex.getStackTrace()));
        }
    }
    
    public void comprobarNumeroOperario(String NumeroOperario){
        try{
            if (!fOperario.ExisteOperario(Integer.parseInt(NumeroOperario))){
                FacesContext.getCurrentInstance().addMessage("frm", new FacesMessage("No existe ese operario"));
            }
        }catch(NumberFormatException | NullPointerException e){
            FacesContext.getCurrentInstance().addMessage("frm", new FacesMessage("No es un numero valido."));
        }
    }
}
