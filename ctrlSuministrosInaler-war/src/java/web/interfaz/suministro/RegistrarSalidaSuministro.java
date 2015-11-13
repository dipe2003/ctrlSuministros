
package web.interfaz.suministro;

import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.FacadeLote;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    
    private Date FechaSalidaSuministro;
    private String strFechaSalidaSuministro;
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
    
    //  getters
    public Date getFechaSalidaSuministro() {return FechaSalidaSuministro;}
    public String getStrFechaSalidaSuministro() {
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        if (FechaSalidaSuministro == null) {
            return this.strFechaSalidaSuministro;
        }else{
            return fDate.format(FechaSalidaSuministro);
        }
    }
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
    
    //  setters
    public void setFechaSalidaSuministro(Date FechaSalidaSuministro) {this.FechaSalidaSuministro = FechaSalidaSuministro;}
    public void setStrFechaSalidaSuministro(String strFechaSalidaSuministro) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            cal.setTime(sdf.parse(strFechaSalidaSuministro));
        }catch(ParseException ex){}
        this.strFechaSalidaSuministro = strFechaSalidaSuministro;
        this.FechaSalidaSuministro = cal.getTime();
    }
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
    
    public void registrarSalidaSuministro() throws IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        int  IdOperario = ((Operario)request.getSession().getAttribute("Usuario")).getIdOperario();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        
    }
    
    @PostConstruct
    public void init(){
        ProveedoresSuministros = new HashMap<>();
        List<Proveedor> Proveedores = fProveedor.ListarProveedores();
        for(Proveedor proveedor: Proveedores){
            ProveedoresSuministros.put(proveedor.getNombreProveedor(), proveedor.getIdProveedor());
        }
        
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
     * Comprueba la existencia del numero de lote para el suministro.
     * @param NumeroLote
     * @return
     */
    public void comprobarLote(String NumeroLote){
        existeLote = false;
        try{
            if(fLote.ExisteLoteSuministro(NumeroLote, IdSuministro)>0){
                existeLote = true;
            }
        }catch(NullPointerException ex){}
    }
    
}
