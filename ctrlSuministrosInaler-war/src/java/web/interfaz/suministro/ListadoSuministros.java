
package web.interfaz.suministro;

import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ListadoSuministros implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;
    @EJB
    private FacadeManejoProveedor fProveedor;
    
    private List<Suministro> ListaSuministros;
    private List<Proveedor> ListaProveedor;

    //  Getters
    public List<Suministro> getListaSuministros() {return ListaSuministros;}   
    public List<Proveedor> getListaProveedor() {return ListaProveedor;}
    
    //  Setters
    public void setListaSuministros(List<Suministro> ListaSuministros) {this.ListaSuministros = ListaSuministros;}
    public void setListaProveedor(List<Proveedor> ListaProveedor) {this.ListaProveedor = ListaProveedor;}
    
    @PostConstruct
    public void init() {
        ListaSuministros = fSuministro.ListarSuministros();
        ListaProveedor = fProveedor.ListarProveedores();
    }
}
