
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
    private Suministro SuministroSeleccionado;
    private List<Suministro> Suministros;
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
    private List<Lote> Lotes;
    private float CantidadStockLote;
    private String UnidadCantidad;
    private float StockSuministro;
    
    //<editor-fold desc="Getters">
    public int getIdProveedor() {return idProveedor;}
    public String getNombreProveedor() {return NombreProveedor;}
    public List<Proveedor> getProveedores() {return Proveedores;}
    public int getIdSuministro() {return IdSuministro;}
    public float getCantidadSalidaSuministro() {return CantidadSalidaSuministro;}
    public String getNumeroLoteSuministro() {return NumeroLoteSuministro;}
    public List<Suministro> getSuministros(){return this.Suministros;}
    
    public boolean isExisteLote() {return existeLote;}
    public String getNumeroFacturaSuministro() {return NumeroFacturaSuministro;}
    public String getObservacionesSalida() {return ObservacionesSalida;}
    
    public int getIdLoteSuministro() {return IdLoteSuministro;}
    public List<Lote> getLotes(){return this.Lotes;}
    
    public float getCantidadStockLote() {
        try{
           CantidadStockLote =  Lotes.stream()
                    .filter(lote->lote.getIdLote() == IdLoteSuministro)
                    .findFirst()
                    .orElse(null)
                    .getCantidadStock();           
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
    //</editor-fold>
    
    //<editor-fold desc="Setters">
    public void setIdProveedor(int idProveedor) {this.idProveedor = idProveedor;}
    public void setNombreProveedor(String NombreProveedor) {this.NombreProveedor = NombreProveedor;}
    public void setProveedores(List<Proveedor> Proveedores) {this.Proveedores = Proveedores;}
    public void setIdSuministro(int IdSuministro) {
        this.IdSuministro = IdSuministro;
        this.SuministroSeleccionado = Suministros.stream()
                .filter(s->s.getIdSuministro() == IdSuministro)
                .findFirst()
                .get();
    }
    public void setCantidadSalidaSuministro(float CantidadSalidaSuministro) {this.CantidadSalidaSuministro = CantidadSalidaSuministro;}
    public void setNumeroLoteSuministro(String NumeroLoteSuministro) {this.NumeroLoteSuministro = NumeroLoteSuministro;}
    public void setSuministros(List<Suministro> suministros){this.Suministros = suministros;}
    
    public void setExisteLote(boolean existeLote) {this.existeLote = existeLote;}
    public void setNumeroFacturaSuministro(String NumeroFacturaSuministro) {this.NumeroFacturaSuministro = NumeroFacturaSuministro;}
    public void setObservacionesSalida(String ObservacionesSalida) {this.ObservacionesSalida = ObservacionesSalida;}
    
    public void setIdLoteSuministro(int IdLoteSuministro) {this.IdLoteSuministro = IdLoteSuministro;}
    public void setLotes(List<Lote> lotes){this.Lotes = lotes;}
    public void setCantidadStockLote(float CantidadStockLote) {this.CantidadStockLote = CantidadStockLote;}
    public void setUnidadCantidad(String UnidadCantidad) {this.UnidadCantidad = UnidadCantidad;}
    public void setStockSuministro(float StockSuministro) {this.StockSuministro = StockSuministro;}
    //</editor-fold>
    /**
     * Registra la salida de un suministro.
     * @throws IOException
     */
    public void registrarSalidaSuministro() throws IOException{
        if(CantidadSalidaSuministro > 0 && CantidadSalidaSuministro <= CantidadStockLote){
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            int  IdOperario = ((Operario)request.getSession().getAttribute("Operario")).getIdOperario();
            String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            Date fechaHoy = Calendar.getInstance().getTime();
            if(fLote.CrearSalida(SuministroSeleccionado.getIdSuministro(), fechaHoy, CantidadSalidaSuministro, IdLoteSuministro, IdOperario, ObservacionesSalida)!=-1){
                context.getExternalContext().redirect(url+"/Views/index.xhtml");
                context.responseComplete();
            }
        }else{
            FacesContext.getCurrentInstance().addMessage("frmRegSalidaSuministro:inputCantidadSalidaSuministro",
                    new FacesMessage("La cantidad de salida no es correcta.", "La cantidad de salida no es correcta."));
            FacesContext.getCurrentInstance().renderResponse();
        }
    }
    
    /**
     * Inicializa las listas y maps
     */
    @PostConstruct
    public void init(){
        Suministros = fSuministro.ListarSuministros(true);
        existeLote = false;
        Proveedores = fProveedor.ListarProveedores();
    }
    
    /**
     * carga los numeros de lote del suministro seleccionado.
     * @param IdSuministro id de suministro seleccionado.
     */
    public void cargarNumerosLoteSuministro(int IdSuministro){
        Lotes = SuministroSeleccionado.getLotesConStock(true);
        UnidadCantidad = SuministroSeleccionado.getUnidadSuministro().getNombreUnidad();
        StockSuministro = SuministroSeleccionado.getStock();
    }
    
    /**
     * carga el proveedor del suministro seleccionado.
     * @param IdSuministro
     */
    public void cargarProveedorSuministro(int IdSuministro){
        Proveedor prov =  Proveedores.stream()
                .filter(proveedor->proveedor.esProveedorSuministro(IdSuministro))
                .findFirst()
                .get();
        
        NombreProveedor = prov.getNombreProveedor();
        idProveedor = prov.getIdProveedor();
    }
}
