
package web.interfaz.suministro;

import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

@Named
@ViewScoped
public class RegistrarIngresoSuministro implements Serializable{
    @EJB
    private FacadeManejoProveedor fProveedor;
    @EJB
    private FacadeManejoSuministros fSuministro;
    
    private Date FechaIngresoSuministro;
    private String strFechaIngresoSuministro;
    private int IdSuministro;
    private Map<String, Integer> listaSuministros;
    private List<Suministro> lstSuministros;
    private float CantidadIngresoSuministro;
    private String NumeroLoteSuministro;
    private Date FechaVencimientoSuministro;
    private String strFechaVencimientoSuministro;
    
    //  Proveedor
    private int idProveedor;
    private Map<String, Integer> ProveedoresSuministros;
    
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
    public Map<String, Integer> getProveedoresSuministros() {return ProveedoresSuministros;}
    public int getIdSuministro() {return IdSuministro;}
    public Map<String, Integer> getListaSuministros() {return listaSuministros;}
    public float getCantidadIngresoSuministro() {return CantidadIngresoSuministro;}
    public String getNumeroLoteSuministro() {return NumeroLoteSuministro;}
    public Date getFechaVencimientoSuministro() {return FechaVencimientoSuministro;}
    public String getStrFechaVencimientoSuministro() {return strFechaVencimientoSuministro;}
    public List<Suministro> getLstSuministros() {return lstSuministros;}
    
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
    public void setProveedoresSuministros(Map<String, Integer> ProveedoresSuministros) {this.ProveedoresSuministros = ProveedoresSuministros;}
    public void setIdSuministro(int IdSuministro) {this.IdSuministro = IdSuministro;}
    public void setListaSuministros(Map<String, Integer> listaSuministros) {this.listaSuministros = listaSuministros;}
    public void setLstSuministros(List<Suministro> lstSuministros) {this.lstSuministros = lstSuministros;}
    public void setCantidadIngresoSuministro(float CantidadIngresoSuministro) {this.CantidadIngresoSuministro = CantidadIngresoSuministro;}
    public void setNumeroLoteSuministro(String NumeroLoteSuministro) {this.NumeroLoteSuministro = NumeroLoteSuministro;}
    public void setFechaVencimientoSuministro(Date FechaVencimientoSuministro) {this.FechaVencimientoSuministro = FechaVencimientoSuministro;}
    public void setStrFechaVencimientoSuministro(String strFechaVencimientoSuministro) {this.strFechaVencimientoSuministro = strFechaVencimientoSuministro;}
    
    public void registrarIngresoSuministro() throws IOException{
        int idSuministro = -1;
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

    }
    
    public void cargarSuministros(int IdProveedor){
        listaSuministros = fSuministro.ListarSuministrosProveedor(IdProveedor);
    }
    
}
