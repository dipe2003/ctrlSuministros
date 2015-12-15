
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    private Map<String, Integer> listaSuministros;
    private float CantidadIngresoSuministro;
    private String NumeroLoteSuministro;
    private String NumeroFacturaSuministro;
    private String ObservacionesIngreso;
    private String UnidadCantidad;
    
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
    private Set<Lote> SetLotesSuministro;
    
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
    public Map<String, Integer> getListaSuministros() {return listaSuministros;}
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
    public Set<Lote> getSetLotesSuministro() {return SetLotesSuministro;}
    
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
    public void setListaSuministros(Map<String, Integer> listaSuministros) {this.listaSuministros = listaSuministros;}
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
    public void setSetLotesSuministro(Set<Lote> SetLotesSuministro) {this.SetLotesSuministro = SetLotesSuministro;}
    
    /**
     * Registra un nuevo ingreso de suministro.
     * Si no existe el lote lo crea y agrega el nuevo ingreso.
     * Si existe agrega el nuevo ingreso.
     * @throws IOException 
     */
    public void registrarIngresoSuministro() throws IOException{
        int idLote;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        int  IdOperario = ((Operario)request.getSession().getAttribute("Operario")).getIdOperario();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if(IdLoteSeleccionado!=0){
            NumeroLoteSuministro = LotesSuministro.get(IdLoteSeleccionado).getNumeroLote();
            idLote = IdLoteSeleccionado;
        }else{
            idLote = fLote.CrearLote(FechaVencimientoSuministro, NumeroLoteSuministro, IdSuministro);
        }
        if(idLote!=-1){
            Date fechaHoy =Calendar.getInstance().getTime();
            if((fLote.CrearIngreso(fechaHoy, CantidadIngresoSuministro, NumeroFacturaSuministro, idLote, IdOperario, ObservacionesIngreso, IdSuministro))!=-1){
                context.getExternalContext().redirect(url+"/Views/index.xhtml");
            }
        }
    }
    
    /**
     * Carga los lotes del suministro seleccionado.
     */
    public void cargarLotes(){
        LotesSuministro = new HashMap<>();
        SetLotesSuministro = new HashSet<>();
        Suministro sum = fSuministro.BuscarSuministro(IdSuministro);
        for(Lote  lot: sum.getLotesSuministros()){
            LotesSuministro.put(lot.getIdLote(), lot);
            SetLotesSuministro.add(lot);
        }
    }
    
    @PostConstruct
    public void init(){
        Proveedores = fProveedor.ListarProveedores();
        listaSuministros = fSuministro.ListarMapSuministros();
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
        for(Proveedor proveedor: Proveedores){
            if(proveedor.esProveedorSuministro(IdSuministro)) {
                NombreProveedor = proveedor.getNombreProveedor();
                idProveedor = proveedor.getIdProveedor();
            }
        }
    }
    
}
