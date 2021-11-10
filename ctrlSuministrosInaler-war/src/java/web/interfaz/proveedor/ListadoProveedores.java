
package web.interfaz.proveedor;

import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class ListadoProveedores implements Serializable{
    @EJB
    private FacadeManejoProveedor fProveedor;
    
    private List<Proveedor> Proveedores;
    private List<Proveedor> ListaProveedor;
    private String NombreProveedor;
    
    //  Getters
    public List<Proveedor> getProveedores(){return this.Proveedores;}
    public List<Proveedor> getListaProveedor() {return ListaProveedor;}
    public String getNombreProveedor() {return NombreProveedor;}
    
    //  Setters
    public void setOperarios(List<Proveedor> proveedores){this.Proveedores = proveedores;}
    public void setListaProveedor(List<Proveedor> ListaProveedor) {this.ListaProveedor = ListaProveedor;}
    public void setNombreProveedor(String NombreProveedor) {this.NombreProveedor = NombreProveedor;}
    
    public void filtrarLista(){
        if(!NombreProveedor.isEmpty()){
            ListaProveedor = Proveedores.stream()
                    .filter((Proveedor proveedor) -> proveedor.getNombreProveedor().toLowerCase().contains(NombreProveedor.toLowerCase()))
                    .collect(Collectors.toList());
        }else{
            ListaProveedor = new ArrayList<>(Proveedores);
        }
    }
    
    @PostConstruct
    public void init() {
        Proveedores = fProveedor.ListarProveedores();
        ListaProveedor =  new ArrayList<>(Proveedores);
    }
    
}
