
package web.interfaz.suministro;


import com.dperez.inalerlab.operario.permiso.Permiso;
import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class RegistrarSuministro implements Serializable{
    @EJB
    private FacadeManejoProveedor fProveedor;
    @EJB
    private FacadeManejoSuministros fSuministro;
    
    private int IdSuministro;
    private String NombreSuministro;
    private String DescripcionSuministro;
    private String CodigoSAPSuministro;
    
    //private List<StockMinimo> StocksMinimosSuministro;
    
    private int IdUnidadSuministro;
    private Map<String, Integer> UnidadesSuministros;
    private int IdProveedor;
    private Map<String, Integer> ProveedoresSuministros;    
    private String TipoSuministro;
    private String[] TiposSuministros;
    
//  getters
    public int getIdSuministro() {return IdSuministro;}
    public String getNombreSuministro() {return NombreSuministro;}
    public String getDescripcionSuministro() {return DescripcionSuministro;}
    public String getCodigoSAPSuministro() {return CodigoSAPSuministro;}
    public int getIdProveedor() {return IdProveedor;}
    public Map<String, Integer> getProveedoresSuministros() {return ProveedoresSuministros;}
    public Map<String, Integer> getUnidadesSuministros() {return UnidadesSuministros;}
    public int getIdUnidadSuministro() {return IdUnidadSuministro;}    
    public String getTipoSuministro() {return TipoSuministro;}
    public String[] getTiposSuministros() {return TiposSuministros;}
    
    //  setters
    public void setIdSuministro(int IdSuministro) {this.IdSuministro = IdSuministro;}
    public void setNombreSuministro(String NombreSuministro) {this.NombreSuministro = NombreSuministro;}
    public void setDescripcionSuministro(String DescripcionSuministro) {this.DescripcionSuministro = DescripcionSuministro;}
    public void setCodigoSAPSuministro(String CodigoSAPSuministro) {this.CodigoSAPSuministro = CodigoSAPSuministro;}
    public void setIdProveedor(int IdProveedor) {this.IdProveedor = IdProveedor;}
    public void setProveedoresSuministros(Map<String, Integer> ProveedoresSuministros) {this.ProveedoresSuministros = ProveedoresSuministros;}
    public void setUnidadesSuministros(Map<String, Integer> UnidadesSuministros) {this.UnidadesSuministros = UnidadesSuministros;}
    public void setIdUnidadSuministro(int IdUnidadSuministro) {this.IdUnidadSuministro = IdUnidadSuministro;}
    public void setTipoSuministro(String TipoSuministro) {this.TipoSuministro = TipoSuministro;}
    public void setTiposSuministros(String[] TiposSuministros) {this.TiposSuministros = TiposSuministros;}
    
    public void registrarSuministro() throws IOException{
        switch(TipoSuministro){
            case "Reactivo Quimico":
                break;
                
            case "Medio de Ensayo":
                break;
                
            default:
                break;                
        }
    }
    
    @PostConstruct
    public void init(){
        UnidadesSuministros = new HashMap<>();
        List<Unidad> Unidades = fSuministro.ListarUnidades();
        for(Unidad unidad: Unidades){
            UnidadesSuministros.put(unidad.getNombreUnidad(), unidad.getIdUnidad());
        }
        
        ProveedoresSuministros = new HashMap<>();
        List<Proveedor> Proveedores = fProveedor.ListarProveedores();
        for(Proveedor proveedor: Proveedores){
            ProveedoresSuministros.put(proveedor.getNombreProveedor(), proveedor.getIdProveedor());
        }
        
        TiposSuministros = new String[]{"Reactivo Quimico", "Medio de Ensayo", "Material"};
        TipoSuministro = TiposSuministros[0];
        
        
    }
    
    
}
