
package web.interfaz.suministro;

import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.FacadeLote;
import com.dperez.inalerlab.suministro.lote.Lote;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class RegistrarSalidaSuministro implements Serializable{
    @EJB
    private FacadeManejoProveedor fProveedor;
    @EJB
    private FacadeManejoSuministros fSuministro;
    @EJB
    private FacadeLote fLote;
    
    private int IdSuministro;
    private Map<String, Integer> listaSuministros;
    private Map<Integer, Suministro> lstSuministros;
    private float CantidadSalidaSuministro;
    private String NumeroLoteSuministro;
    private String NumeroFacturaSuministro;
    private String ObservacionesSalida;
    
    //  Proveedor
    private int idProveedor;
    private String NombreProveedor;
    List<Proveedor> Proveedores;
    
    //  Lote
    private boolean existeLote;
    private int IdLoteSuministro;
    private Map<String, Integer> listaNumerosLoteSuministro;
    private float CantidadStockLote;
    private String UnidadCantidad;
    private float StockSuministro;
    
    //  getters
    public int getIdProveedor() {return idProveedor;}
    public String getNombreProveedor() {return NombreProveedor;}
    public List<Proveedor> getProveedores() {return Proveedores;}
    public int getIdSuministro() {return IdSuministro;}
    public Map<String, Integer> getListaSuministros() {return listaSuministros;}
    public float getCantidadSalidaSuministro() {return CantidadSalidaSuministro;}
    public String getNumeroLoteSuministro() {return NumeroLoteSuministro;}

    public Map<Integer, Suministro> getLstSuministros() {return lstSuministros;}   
    public boolean isExisteLote() {return existeLote;}
    public String getNumeroFacturaSuministro() {return NumeroFacturaSuministro;}
    public String getObservacionesSalida() {return ObservacionesSalida;}
    
    public int getIdLoteSuministro() {return IdLoteSuministro;}
    public Map<String, Integer> getListaNumerosLoteSuministro() {return listaNumerosLoteSuministro;}
    public float getCantidadStockLote() {
        try{
            List<Lote> lotes = lstSuministros.get(IdSuministro).getLotesSuministros();
            for(Lote lote: lotes) if(lote.getIdLote()==IdLoteSuministro) CantidadStockLote = lote.getCantidadStock();            
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
    public void setNombreProveedor(String NombreProveedor) {this.NombreProveedor = NombreProveedor;}
    public void setProveedores(List<Proveedor> Proveedores) {this.Proveedores = Proveedores;}
    public void setIdSuministro(int IdSuministro) {this.IdSuministro = IdSuministro;}
    public void setListaSuministros(Map<String, Integer> listaSuministros) {this.listaSuministros = listaSuministros;}
    public void setLstSuministros(Map<Integer, Suministro> lstSuministros) {this.lstSuministros = lstSuministros;}    
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
        if(CantidadSalidaSuministro <= CantidadStockLote){
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            int  IdOperario = ((Operario)request.getSession().getAttribute("Operario")).getIdOperario();
            String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            Date fechaHoy = Calendar.getInstance().getTime();
            if(fLote.CrearSalida(fechaHoy, CantidadSalidaSuministro, IdLoteSuministro, IdOperario, ObservacionesSalida)!=-1){
                context.getExternalContext().redirect(url+"/Views/index.xhtml");
            }
        }else{
            FacesContext.getCurrentInstance().addMessage("frmRegSalidaSuministro:inputCantidadSalidaSuministro", 
                    new FacesMessage("La cantidad de salida no puede ser mayor que la disponible del lote", "La cantidad de salida no puede ser mayor que la disponible del lote"));
        }
    }
    
    @PostConstruct
    public void init(){
        listaSuministros = fSuministro.ListarMapSuministros(true);
        lstSuministros = fSuministro.ListarMapSuministrosFull();
        existeLote = false;
        Proveedores = fProveedor.ListarProveedores();
    }
    
    /**
     * carga los numeros de lote del suministro seleccionado.
     * @param IdSuministro id de suministro seleccionado.
     */
    public void cargarNumerosLoteSuministro(int IdSuministro){
        listaNumerosLoteSuministro = fLote.ListarMapLotesConStock(IdSuministro);
        UnidadCantidad = fSuministro.BuscarUnidadSuministro(IdSuministro).getNombreUnidad();
        StockSuministro = fSuministro.BuscarSuministro(IdSuministro).getStock();
    }
    
    /**
     * carga el proveedor del suministro seleccionado.
     * @param IdSuministro
     */
    public void cargarProveedorSuministro(int IdSuministro){
        for(Proveedor proveedor: Proveedores){
            if(proveedor.esProveedorSuministro(IdSuministro)) {
                NombreProveedor = proveedor.getNombreProveedor();
                idProveedor = proveedor.getIdProveedor();
            }
        }
    }
}
