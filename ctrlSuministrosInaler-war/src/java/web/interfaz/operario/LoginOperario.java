
package web.interfaz.operario;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.operario.permiso.Permiso;
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
import javax.servlet.http.HttpServletRequest;

@Named
@SessionScoped
public class LoginOperario implements Serializable{
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
                context.addMessage("frmLogin:msjLogin", new FacesMessage("OK", "OK"));
            }else{
                FacesContext.getCurrentInstance().addMessage("frmLogin:msjLogin", new FacesMessage("Error", "Los datos no son correctos."));
            }            
        }catch(NullPointerException | NumberFormatException ex){
            FacesContext.getCurrentInstance().addMessage("frmLogin:msjLogin", new FacesMessage("Error", "Los datos no son correctos."));
        }
    }
    
    public void logout(){
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.getSession().invalidate();
            Logueado = false;
            FacesContext.getCurrentInstance().getExternalContext().redirect("../Views/index.xhtml");
        }catch(Exception ex){}
    }
    
    @PostConstruct
    public void init(){
        try{
            //  Operarios
            int idPermiso = cPermiso.CrearPermiso("Admin");
            int idOp = fOperario.RegistrarOperario(3002425, "diego", "perez", "2017.Calidad");
            fOperario.AgregarPermiso(idOp, idPermiso);
            
            idPermiso = cPermiso.CrearPermiso("Analista");
            idOp = fOperario.RegistrarOperario(3000914, "Bruno", "Bracco", "labo.2015");
            fOperario.AgregarPermiso(idOp, idPermiso);
            
            idOp = fOperario.RegistrarOperario(3000952, "Lorena", "Peña", "labo.2015");
            fOperario.AgregarPermiso(idOp, idPermiso);
            
            idPermiso = cPermiso.CrearPermiso("Jefecita");
            idOp = fOperario.RegistrarOperario(3000042, "Cristina", "Caputi", "Marfrig2015");
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
            int idlote = fLote.CrearLote(fProd.getTime(), fVenc.getTime(), "A001");
            fLote.AgregarLoteSuministro(idSuministro, idlote);
            
            //  Ingreso
            fLote.CrearIngreso(Calendar.getInstance().getTime(), 12.5f, "ABC 1234", idlote, 300914);
            
            //  Salida
            fLote.CrearSalida(Calendar.getInstance().getTime(), 6.25f, fLote.BuscarLotePorNumeroLote("A001").getIdLote(), 300914);
            
        }catch(Exception ex){
            System.out.print(Arrays.toString(ex.getStackTrace()));
        }
    }
    

}
