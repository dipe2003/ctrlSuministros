
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
public class RegistrarIngresoSuministro implements Serializable{
    @EJB
    private FacadeManejoProveedor fProveedor;
    @EJB
    private FacadeManejoSuministros fSuministro;
    @EJB
    private FacadeLote fLote;
    
    private Date FechaIngresoSuministro;
    private String strFechaIngresoSuministro;
    private int IdSuministro;
    private List<Suministro> ListaSuministros;
    private float CantidadIngresoSuministro;
    private String NumeroLoteSuministro;
    private String NumeroFacturaSuministro;
    private String ObservacionesIngreso;
    private String UnidadCantidad;
    private boolean AvisoCambioLote;
    
    //  Proveedor
    private int idProveedor;
    private String NombreProveedor;
    private List<Proveedor> Proveedores;
    
    //  Lote
    private Date FechaVencimientoSuministro;
    private String strFechaVencimientoSuministro;
    private boolean existeLote;
    private int IdLoteSeleccionado;
    private Map<Integer, Lote> LotesSuministro;
    private Map<Integer, Lote> SetLotesSuministro;
    
    //  getters
    public Date getFechaIngresoSuministro() {return FechaIngresoSuministro;}
    public String getStrFechaIngresoSuministro() {
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        if (FechaIngresoSuministro == null) {
            return this.strFechaIngresoSuministro;
        }else{
            return fDate.format(FechaIngresoSuministro);
        }
    }
    public int getIdProveedor() {return idProveedor;}
    public String getNombreProveedor() {return NombreProveedor;}
    public List<Proveedor> getProveedores() {return Proveedores;}
    public int getIdSuministro() {return IdSuministro;}
    public List<Suministro> getListaSuministros(){return this.ListaSuministros;}
    public float getCantidadIngresoSuministro() {return CantidadIngresoSuministro;}
    public String getNumeroLoteSuministro() {return NumeroLoteSuministro;}
    public Date getFechaVencimientoSuministro() {return FechaVencimientoSuministro;}
    public String getStrFechaVencimientoSuministro() {
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        if (FechaVencimientoSuministro == null) {
            return this.strFechaVencimientoSuministro;
        }else{
            return fDate.format(FechaVencimientoSuministro);
        }
    }
    public boolean isExisteLote() {return existeLote;}
    public String getNumeroFacturaSuministro() {return NumeroFacturaSuministro;}
    public String getObservacionesIngreso() {return ObservacionesIngreso;}
    public String getUnidadCantidad() {return UnidadCantidad;}
    public int getIdLoteSeleccionado() {return IdLoteSeleccionado;}
    public Map<Integer, Lote> getLotesSuministro() {return LotesSuministro;}
    public Map<Integer, Lote> getSetLotesSuministro() {return SetLotesSuministro;}
    
    //  setters
    public void setFechaIngresoSuministro(Date FechaIngresoSuministro) {this.FechaIngresoSuministro = FechaIngresoSuministro;}
    public void setStrFechaIngresoSuministro(String strFechaIngresoSuministro) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            cal.setTime(sdf.parse(strFechaIngresoSuministro));
        }catch(ParseException ex){}
        this.strFechaIngresoSuministro = strFechaIngresoSuministro;
        this.FechaIngresoSuministro = cal.getTime();
    }
    public void setIdProveedor(int idProveedor) {this.idProveedor = idProveedor;}
    public void setNombreProveedor(String NombreProveedor) {this.NombreProveedor = NombreProveedor;}
    public void setProveedores(List<Proveedor> Proveedores) {this.Proveedores = Proveedores;}
    public void setIdSuministro(int IdSuministro) {this.IdSuministro = IdSuministro;}
    public void setListaSuministros(List<Suministro> listaSuministros){this.ListaSuministros = listaSuministros;}
    public void setCantidadIngresoSuministro(float CantidadIngresoSuministro) {this.CantidadIngresoSuministro = CantidadIngresoSuministro;}
    public void setNumeroLoteSuministro(String NumeroLoteSuministro) {this.NumeroLoteSuministro = NumeroLoteSuministro;}
    public void setFechaVencimientoSuministro(Date FechaVencimientoSuministro) {this.FechaVencimientoSuministro = FechaVencimientoSuministro;}
    public void setStrFechaVencimientoSuministro(String strFechaVencimientoSuministro) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            cal.setTime(sdf.parse(strFechaVencimientoSuministro));
            this.strFechaVencimientoSuministro = strFechaVencimientoSuministro;
            this.FechaVencimientoSuministro = cal.getTime();
        }catch(ParseException ex){
            this.FechaVencimientoSuministro = null;
        }
        
    }
    public void setExisteLote(boolean existeLote) {this.existeLote = existeLote;}
    public void setNumeroFacturaSuministro(String NumeroFacturaSuministro) {this.NumeroFacturaSuministro = NumeroFacturaSuministro;}
    public void setObservacionesIngreso(String ObservacionesIngreso) {this.ObservacionesIngreso = ObservacionesIngreso;}
    public void setUnidadCantidad(String UnidadCantidad) {this.UnidadCantidad = UnidadCantidad;}
    public void setIdLoteSeleccionado(int IdLoteSeleccionado) {this.IdLoteSeleccionado = IdLoteSeleccionado;}
    public void setLotesSuministro(Map<Integer, Lote> LotesSuministro) {this.LotesSuministro = LotesSuministro;}
    public void setSetLotesSuministro(Map<Integer, Lote> SetLotesSuministro) {this.SetLotesSuministro = SetLotesSuministro;}
    
    /**
     * Registra un nuevo ingreso de suministro.
     * Comprueba que la cantidad a ingresar sea correcta (mayor a 0)
     * Si no existe el lote lo crea y agrega el nuevo ingreso.
     * Si existe agrega el nuevo ingreso.
     * @throws IOException
     */
    public void registrarIngresoSuministro() throws IOException{
        int idLote;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if(CantidadIngresoSuministro <= 0){
            context.addMessage("frmRegIngresoSuministro:inputCantidadIngresoSuministro", new FacesMessage("La cantidad ingresada no es valida.", "La cantidad ingresada no es valida."));
            context.renderResponse();
        }else{
            int  IdOperario = ((Operario)request.getSession().getAttribute("Operario")).getIdOperario();
            String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            if(IdLoteSeleccionado!=0){ // nuevo ingreso de un lote existente
                NumeroLoteSuministro = LotesSuministro.get(IdLoteSeleccionado).getNumeroLote();
                idLote = IdLoteSeleccionado;
            }else{ // lote nuevo, se crea lote
                idLote = fLote.CrearLote(FechaVencimientoSuministro, NumeroLoteSuministro, IdSuministro);
                if(idLote != -1){
                    // enviar mail de aviso de cambio de lote cuando corresponde
                    if(AvisoCambioLote){
                        fSuministro.EnviarNotificacionCambioLote(IdSuministro, NumeroLoteSuministro);
                    }
                }
            }
            if(idLote!=-1){  // ingreso con nuevo lote
                Date fechaHoy =Calendar.getInstance().getTime();
                if((fLote.CrearIngreso(fechaHoy, CantidadIngresoSuministro, NumeroFacturaSuministro, idLote, IdOperario, ObservacionesIngreso, IdSuministro))!=-1){
                    context.getExternalContext().redirect(url+"/Views/index.xhtml");
                    context.responseComplete();
                }
            }
        }
    }
    
    /**
     * Carga los lotes del suministro seleccionado.
     */
    public void cargarLotes(){
        LotesSuministro = new HashMap<>();
        SetLotesSuministro = new HashMap<>();
        Suministro sum = fSuministro.BuscarSuministro(IdSuministro);
        AvisoCambioLote = sum.isAvisoCambioLote();
        sum.getLotesSuministros().stream()
                .forEachOrdered(lot->{
                    LotesSuministro.put(lot.getIdLote(), lot);
                    SetLotesSuministro.put(lot.getIdLote(), lot);
                });
        SetLotesSuministro = new TreeMap<>(SetLotesSuministro);
    }
    
    @PostConstruct
    public void init(){
        Proveedores = fProveedor.ListarProveedores();
        ListaSuministros = fSuministro.ListarSuministros(true, true);
        existeLote = false;
    }
    
    /**
     * Carga la unidad del suministro.
     */
    public void cargarUnidad(){
        UnidadCantidad = fSuministro.BuscarUnidadSuministro(IdSuministro).getNombreUnidad();
    }
    
    /**
     * Carga los proveedores del suministro seleccionado.
     * @param IdSuministro
     */
    public void cargarProveedoresSuministro(int IdSuministro){
        Proveedores.stream()
                .filter(proveedor->proveedor.esProveedorSuministro(IdSuministro))
                .forEachOrdered(proveedor->{
                    NombreProveedor = proveedor.getNombreProveedor();
                    idProveedor = proveedor.getIdProveedor();
                });
    }
    
}
