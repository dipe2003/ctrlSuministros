
package web.interfaz.suministro;

import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Material;
import com.dperez.inalerlab.suministro.MedioEnsayo;
import com.dperez.inalerlab.suministro.ReactivoQuimico;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ViewScoped
public class EditarSuministro implements Serializable{
    @EJB
    private FacadeManejoProveedor fProveedor;
    @EJB
    private FacadeManejoSuministros fSuministro;
    
    private int IdSuministro;
    private String NombreSuministro;
    private String DescripcionSuministro;
    private String CodigoSAPSuministro;
    private boolean EstaVigente;
    private boolean AvisoCambioLote;
    
    private float CantidadStockMinimo;
    private Date FechaVigenteStock;
    private String strFechaVigenteStock;
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
    public boolean isEstaVigente() {return EstaVigente;}
    public boolean isAvisoCambioLote() {return AvisoCambioLote;}
    
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
    public void setEstaVigente(boolean EstaVigente) {this.EstaVigente = EstaVigente;}
    public void setAvisoCambioLote(boolean AvisoCambioLote) {this.AvisoCambioLote = AvisoCambioLote;}    
    
    public void editarSuministro() throws IOException{
        int idSuministro = -1;
        String msj ="";
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        Suministro suministro;
        switch(TipoSuministro){
            case "Reactivo Quimico":
                suministro = new ReactivoQuimico();
                break;
                
            case "Medio de Ensayo":
                suministro = new MedioEnsayo();
                break;
                
            default:
                suministro = new Material();
                break;
        }
        suministro.setCodigoSAPSuministro(CodigoSAPSuministro);
        suministro.setDescripcionSuministro(DescripcionSuministro);        
        suministro.setNombreSuministro(NombreSuministro);
        suministro.setIdSuministro(IdSuministro);
        suministro.setVigente(EstaVigente);
        suministro.setAvisoCambioLote(AvisoCambioLote);
        if(fSuministro.ActualizarSuministro(suministro, IdProveedor, IdUnidadSuministro, CantidadStockMinimo)!=-1){
            FacesContext.getCurrentInstance().getExternalContext().redirect(url+"/Views/Suministro/ListadoSuministros.xhtml");
        }else{
            msj = "No se pudo actualizar.";
        }
        FacesContext.getCurrentInstance().addMessage("frmEditSuministro:btnEditarSuministro", new FacesMessage(msj));
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
        ProveedoresSuministros = new TreeMap<>(ProveedoresSuministros);
        
        TiposSuministros = new String[]{"Reactivo Quimico", "Medio de Ensayo", "Material"};        
        
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            IdSuministro = Integer.parseInt(request.getParameter("id"));
            cargarDatosSuministros();
        }catch(NullPointerException | NumberFormatException ex){}
    }
    
    private void cargarDatosSuministros(){
        Suministro sum = fSuministro.BuscarSuministro(IdSuministro);
        NombreSuministro = sum.getNombreSuministro();
        DescripcionSuministro = sum.getDescripcionSuministro();
        CodigoSAPSuministro = sum.getCodigoSAPSuministro();
        EstaVigente = sum.isVigente();
        AvisoCambioLote = sum.isAvisoCambioLote();
        
        CantidadStockMinimo = sum.getStockMinimoSuministro().getCantidadStockMinimo();
        FechaVigenteStock = sum.getStockMinimoSuministro().getFechaVigenteStockMinimo();
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        strFechaVigenteStock = fDate.format(FechaVigenteStock);
        IdUnidadSuministro = sum.getUnidadSuministro().getIdUnidad();
        IdProveedor = sum.getProveedorSuministro().getIdProveedor();
        switch(sum.getClass().getSimpleName()){
            case "ReactivoQuimico":
                TipoSuministro = TiposSuministros[0];
                break;
                
            case "MedioEnsayo":
                TipoSuministro = TiposSuministros[1];
                break;
                
            case "Material":
                TipoSuministro = TiposSuministros[2];
        }
    }
    
}
