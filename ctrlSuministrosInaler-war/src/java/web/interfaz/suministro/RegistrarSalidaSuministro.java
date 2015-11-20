
package web.interfaz.suministro;

import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.FacadeLote;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ViewScoped
public class RegistrarSalidaSuministro implements Serializable{
    @EJB
    private FacadeManejoProveedor fProveedor;
    @EJB
    private FacadeManejoSuministros fSuministro;
    @EJB
    private FacadeLote fLote;

    private int IdSuministro;
    private Map<String, Integer> listaSuministros;
    private List<Suministro> lstSuministros;
    private float CantidadSalidaSuministro;
    private String NumeroLoteSuministro;
    private String NumeroFacturaSuministro;
    private String ObservacionesSalida;    
    
    //  Proveedor
    private int idProveedor;
    private Map<String, Integer> ProveedoresSuministros;
    
    //  Lote
    private boolean existeLote;
    private int IdLoteSuministro;
    private Map<String, Integer> listaNumerosLoteSuministro;
    private float CantidadStockLote;
    private String UnidadCantidad;
    private float StockSuministro;
    
    //  getters
    public int getIdProveedor() {return idProveedor;}
    public Map<String, Integer> getProveedoresSuministros() {return ProveedoresSuministros;}
    public int getIdSuministro() {return IdSuministro;}
    public Map<String, Integer> getListaSuministros() {return listaSuministros;}
    public float getCantidadSalidaSuministro() {return CantidadSalidaSuministro;}
    public String getNumeroLoteSuministro() {return NumeroLoteSuministro;}
    
    public List<Suministro> getLstSuministros() {return lstSuministros;}
    public boolean isExisteLote() {return existeLote;}
    public String getNumeroFacturaSuministro() {return NumeroFacturaSuministro;}
    public String getObservacionesSalida() {return ObservacionesSalida;}

    public int getIdLoteSuministro() {return IdLoteSuministro;}
    public Map<String, Integer> getListaNumerosLoteSuministro() {return listaNumerosLoteSuministro;}
    public float getCantidadStockLote() {
        try{
            CantidadStockLote  = fLote.BuscarLotePorIdLote(IdLoteSuministro).getCantidadStock();
        }catch(NullPointerException ex){
            CantidadStockLote = 0f;
        }
        return CantidadStockLote;
    }
    public String getUnidadCantidad() {return UnidadCantidad;}
    public float getNuevoStock(){
        try{
            return CantidadStockLote - CantidadSalidaSuministro;
        }catch(Exception ex){}
        return CantidadStockLote;
    }    
    public float getStockSuministro() {return StockSuministro;}
    
    //  setters
    public void setIdProveedor(int idProveedor) {this.idProveedor = idProveedor;}
    public void setProveedoresSuministros(Map<String, Integer> ProveedoresSuministros) {this.ProveedoresSuministros = ProveedoresSuministros;}
    public void setIdSuministro(int IdSuministro) {this.IdSuministro = IdSuministro;}
    public void setListaSuministros(Map<String, Integer> listaSuministros) {this.listaSuministros = listaSuministros;}
    public void setLstSuministros(List<Suministro> lstSuministros) {this.lstSuministros = lstSuministros;}
    public void setCantidadSalidaSuministro(float CantidadSalidaSuministro) {this.CantidadSalidaSuministro = CantidadSalidaSuministro;}
    public void setNumeroLoteSuministro(String NumeroLoteSuministro) {this.NumeroLoteSuministro = NumeroLoteSuministro;}
    
    public void setExisteLote(boolean existeLote) {this.existeLote = existeLote;}
    public void setNumeroFacturaSuministro(String NumeroFacturaSuministro) {this.NumeroFacturaSuministro = NumeroFacturaSuministro;}
    public void setObservacionesSalida(String ObservacionesSalida) {this.ObservacionesSalida = ObservacionesSalida;}

    public void setIdLoteSuministro(int IdLoteSuministro) {this.IdLoteSuministro = IdLoteSuministro;}
    public void setListaNumerosLoteSuministro(Map<String, Integer> listaNumerosLoteSuministro) {this.listaNumerosLoteSuministro = listaNumerosLoteSuministro;}
    public void setCantidadStockLote(float CantidadStockLote) {this.CantidadStockLote = CantidadStockLote;}
    public void setUnidadCantidad(String UnidadCantidad) {this.UnidadCantidad = UnidadCantidad;}
    public void setStockSuministro(float StockSuministro) {this.StockSuministro = StockSuministro;}
    
    public void registrarSalidaSuministro() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        int  IdOperario = ((Operario)request.getSession().getAttribute("Operario")).getIdOperario();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if(fLote.CrearSalida(Calendar.getInstance().getTime(), CantidadSalidaSuministro, IdLoteSuministro, IdOperario, ObservacionesSalida)!=-1){
            context.getExternalContext().redirect(url+"/Views/index.xhtml");
        }        
    }
    
    @PostConstruct
    public void init(){
        ProveedoresSuministros = fProveedor.ListarMapProveedores();               
        listaSuministros = fSuministro.ListarSuministrosProveedor(idProveedor);
        existeLote = false;
    }
    
    /**
     * Carga la lista de suministros pertenecientes al proveedor especificado.
     * @param IdProveedor
     */
    public void cargarSuministros(int IdProveedor){
        listaSuministros = fSuministro.ListarSuministrosProveedor(IdProveedor);
    }
        
    /**
     * 
     * @param IdSuministro 
     */
    public void cargarNumerosLoteSuministro(int IdSuministro){
        listaNumerosLoteSuministro = fLote.ListarMapLotes(IdSuministro);
        UnidadCantidad = fSuministro.BuscarUnidadSuministro(IdSuministro).getNombreUnidad();
        StockSuministro = fSuministro.BuscarSuministro(IdSuministro).getStock();
    }
}
