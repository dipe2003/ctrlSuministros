
package web.interfaz.proveedor;

import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class RegistrarProveedor implements Serializable{
    @EJB
    private FacadeManejoProveedor fProveedor;

    private String NombreProveedor;
    private String ContactoProveedor;
    
    //  getters
    public String getNombreProveedor() {return NombreProveedor;}
    public String getContactoProveedor() {return ContactoProveedor;}
    
    //  setters
    public void setNombreProveedor(String NombreProveedor) {this.NombreProveedor = NombreProveedor;}
    public void setContactoProveedor(String ContactoProveedor) {this.ContactoProveedor = ContactoProveedor;}
    
    public void registrarProveedor() throws IOException{
        int IdProveedor = -1;
        if (fProveedor.RegistrarProveedor(NombreProveedor, ContactoProveedor)!=-1) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }else{
            String msj="No se pudo registrar.";
            FacesContext.getCurrentInstance().addMessage("frmRegProv:btnRegistrarProveedor", new FacesMessage(msj));
        }
    }
    
 }
