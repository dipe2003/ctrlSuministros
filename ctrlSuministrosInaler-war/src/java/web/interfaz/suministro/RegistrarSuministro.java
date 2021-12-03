
package web.interfaz.suministro;

import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.EnumSuministro;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
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
    private boolean AvisoCambioLote;
    
    private float CantidadStockMinimo;
    private Date FechaVigenteStock;
    private String strFechaVigenteStock;
    private int IdUnidadSuministro;
    private int IdProveedor;
    private EnumSuministro TipoSuministro;
    private EnumSuministro[] TiposSuministros;
    
    private List<Unidad> Unidades;
    private List<Proveedor> Proveedores;
    
//  getters
    public int getIdSuministro() {return IdSuministro;}
    public String getNombreSuministro() {return NombreSuministro;}
    public String getDescripcionSuministro() {return DescripcionSuministro;}
    public String getCodigoSAPSuministro() {return CodigoSAPSuministro;}
    public boolean isAvisoCambioLote() {return AvisoCambioLote;}    
    public int getIdProveedor() {return IdProveedor;}
    public int getIdUnidadSuministro() {return IdUnidadSuministro;}
    public EnumSuministro getTipoSuministro() {return TipoSuministro;}
    public EnumSuministro[] getTiposSuministros() {return TiposSuministros;}
    public float getCantidadStockMinimo() {return CantidadStockMinimo;}
    public Date getFechaVigenteStock() {return FechaVigenteStock;}
    public String getStrFechaVigenteStock() {
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        if (FechaVigenteStock == null) {
            return this.strFechaVigenteStock;
        }else{
            return fDate.format(FechaVigenteStock);
        }
    }
    
    public List<Unidad> getUnidades(){return this.Unidades;}
    public List<Proveedor> getProveedores(){return this.Proveedores;}
    
    //  setters
    public void setIdSuministro(int IdSuministro) {this.IdSuministro = IdSuministro;}
    public void setNombreSuministro(String NombreSuministro) {this.NombreSuministro = NombreSuministro;}
    public void setDescripcionSuministro(String DescripcionSuministro) {this.DescripcionSuministro = DescripcionSuministro;}
    public void setCodigoSAPSuministro(String CodigoSAPSuministro) {this.CodigoSAPSuministro = CodigoSAPSuministro;}
    public void setAvisoCambioLote(boolean AvisoCambioLote) {this.AvisoCambioLote = AvisoCambioLote;}    
    public void setIdProveedor(int IdProveedor) {this.IdProveedor = IdProveedor;}
    public void setIdUnidadSuministro(int IdUnidadSuministro) {this.IdUnidadSuministro = IdUnidadSuministro;}
    public void setTipoSuministro(EnumSuministro TipoSuministro) {this.TipoSuministro = TipoSuministro;}
    public void setTiposSuministros(EnumSuministro[] TiposSuministros) {this.TiposSuministros = TiposSuministros;}
    public void setCantidadStockMinimo(float CantidadStockMinimo) {this.CantidadStockMinimo = CantidadStockMinimo;}
    public void setFechaVigenteStock(Date FechaVigenteStock) {this.FechaVigenteStock = FechaVigenteStock;}
    public void setStrFechaVigenteStock(String strFechaVigenteStock) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            cal.setTime(sdf.parse(strFechaVigenteStock));
        }catch(ParseException ex){}
        this.strFechaVigenteStock = strFechaVigenteStock;
        this.FechaVigenteStock = cal.getTime();
    }
    
    public void setUnidades(List<Unidad> unidades){this.Unidades = unidades;}
    public void setProveedores(List<Proveedor> proveedores){this.Proveedores = proveedores;}
    
    public void registrarSuministro() throws IOException{
        int idSuministro = -1;
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        switch(TipoSuministro){
            case REACTIVO_QUIMICO:
                if((idSuministro = fSuministro.RegistrarReactivoQuimico(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, IdUnidadSuministro, IdProveedor, AvisoCambioLote))!=-1){
                    fSuministro.RegistrarStockMinimo(CantidadStockMinimo, FechaVigenteStock, idSuministro);
                    FacesContext.getCurrentInstance().getExternalContext().redirect(url+"/Views/index.xhtml");
                }
                break;
                
            case MEDIO_ENSAYO:
                if((idSuministro = fSuministro.RegistrarMedioEnsayo(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, IdUnidadSuministro, IdProveedor, AvisoCambioLote))!=-1){
                    fSuministro.RegistrarStockMinimo(CantidadStockMinimo, FechaVigenteStock, idSuministro);
                    FacesContext.getCurrentInstance().getExternalContext().redirect(url+"/Views/index.xhtml");
                }
                break;
                
            default:
                if((idSuministro = fSuministro.RegistrarMaterial(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, IdUnidadSuministro, IdProveedor, AvisoCambioLote))!=-1){
                    fSuministro.RegistrarStockMinimo(CantidadStockMinimo, FechaVigenteStock, idSuministro);
                    FacesContext.getCurrentInstance().getExternalContext().redirect(url+"/Views/index.xhtml");
                }
                break;
        }
    }
    
    @PostConstruct
    public void init(){
        Unidades = fSuministro.ListarUnidades();
               Proveedores = fProveedor.ListarProveedores()
                .stream()
                .sorted(Comparator.comparing(Proveedor::getNombreProveedor))
                .collect(Collectors.toList());
        
        TiposSuministros = EnumSuministro.values(); 
        TipoSuministro = TiposSuministros[0];
    }
    
    
}
