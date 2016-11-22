
package web.interfaz.proveedor;

import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ViewScoped
public class EditarProveedor implements Serializable{
    @EJB
    private FacadeManejoProveedor fProveedor;
    
    private int IdProveedor;
    private Map<String, Integer> MapProveedores;

    private String NombreProveedor;
    private String ContactoProveedor;
    
    //  getters
    public String getNombreProveedor() {return NombreProveedor;}
    public String getContactoProveedor() {return ContactoProveedor;}
    public int getIdProveedor() {return IdProveedor;}
    public Map<String, Integer> getMapProveedores() {return MapProveedores;}
    
    //  setters
    public void setNombreProveedor(String NombreProveedor) {this.NombreProveedor = NombreProveedor;}
    public void setContactoProveedor(String ContactoProveedor) {this.ContactoProveedor = ContactoProveedor;}
    public void setIdProveedor(int IdProveedor) {this.IdProveedor = IdProveedor;}
    public void setMapProveedores(Map<String, Integer> MapProveedores) {this.MapProveedores = MapProveedores;}
    
    public void cargarDatosProveedor(){
        Proveedor prov = fProveedor.BuscarProveedor(IdProveedor);
        NombreProveedor = prov.getNombreProveedor();
        ContactoProveedor = prov.getContactoProveedor();
    }
    
    public void editarProveedor() throws IOException{
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();        
        if (fProveedor.ModificarDatosProveedor(IdProveedor, NombreProveedor, ContactoProveedor)!=-1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url+"/Views/Proveedor/ListadoProveedores.xhtml");
        }else{
            String msj="No se pudo actualizar.";            
            FacesContext.getCurrentInstance().addMessage("frmEditProv:btnEditarProveedor", new FacesMessage(msj));
        }
    }
    
    @PostConstruct
    public void init(){
        MapProveedores = fProveedor.ListarMapProveedores();
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();        
            IdProveedor = Integer.parseInt(request.getParameter("id"));
            cargarDatosProveedor();
        }catch(NullPointerException | NumberFormatException ex){}
    }
    
 }
