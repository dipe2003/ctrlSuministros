
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.FacadeLote;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ViewScoped
public class EditarIngreso implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;
    @EJB
    private FacadeLote fLote;
    
    private int IdIngreso;
    private String NumeroFactura;
    private String NumeroLote;
    
    private float CantidadIngreso;
    private Date FechaIngreso;
    private String strFechaIngreso;
    
    private String UnidadSuministro;
    
//  getters
    public int getIdIngreso() {return IdIngreso;}
    public String getNumeroFactura() {return NumeroFactura;}
    public String getNumeroLote() {return NumeroLote;}
    public String getUnidadSuministro() {return UnidadSuministro;}
    public float getCantidadIngreso() {return CantidadIngreso;}
    public Date getFechaIngreso() {return FechaIngreso;}
    public String getStrFechaIngreso() {
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        if (FechaIngreso == null) {
            return this.strFechaIngreso;
        }else{
            return fDate.format(FechaIngreso);
        }
    }
    
    //  setters
    public void setIdIngreso(int IdIngreso) {this.IdIngreso = IdIngreso;}
    public void setNumeroFactura(String NumeroFactura) {this.NumeroFactura = NumeroFactura;}
    public void setNumeroLote(String NumeroLote) {this.NumeroLote = NumeroLote;}
    public void setUnidadSuministro(String UnidadSuministro) {this.UnidadSuministro = UnidadSuministro;}
    public void setCantidadIngreso(float CantidadIngreso) {this.CantidadIngreso = CantidadIngreso;}
    public void setFechaIngreso(Date FechaIngreso) {this.FechaIngreso = FechaIngreso;}
    public void setStrFechaIngreso(String strFechaIngreso) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            cal.setTime(sdf.parse(strFechaIngreso));
        }catch(ParseException ex){}
        this.strFechaIngreso = strFechaIngreso;
        this.FechaIngreso = cal.getTime();
    }
    
    public void editarIngreso() throws IOException{
//        int idSuministro = -1;
//        String msj ="";
//        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
//        Suministro suministro;
//        
//        suministro.setDescripcionSuministro(NumeroLote);
//        suministro.setNombreSuministro(NumeroFactura);
//        suministro.setIdSuministro(IdIngreso);
//        
//        if(fSuministro.ActualizarSuministro(suministro, IdProveedor, UnidadSuministro, CantidadIngreso)!=-1){
//            FacesContext.getCurrentInstance().getExternalContext().redirect(url+"/Views/Suministro/ListadoSuministros.xhtml");
//        }else{
//            msj = "No se pudo actualizar.";
//        }
//        FacesContext.getCurrentInstance().addMessage("frmEditIngreso:btnEditarIngreso", new FacesMessage(msj));
    }
    
    @PostConstruct
    public void init(){

        try{
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            IdIngreso = Integer.parseInt(request.getParameter("id"));
            cargarDatosIngreso();
        }catch(NullPointerException | NumberFormatException ex){}
    }
    
    
    private void cargarDatosIngreso(){
        Ingreso ingreso = fLote.BuscarIngreso(IdIngreso);
        NumeroFactura = ingreso.getNumeroFactura();
        NumeroLote = ingreso.getLoteIngreso().getNumeroLote();
        CantidadIngreso = ingreso.getCantidadIngreso();
        FechaIngreso = ingreso.getFechaIngreso();
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        strFechaIngreso = fDate.format(FechaIngreso);
        UnidadSuministro =ingreso.getLoteIngreso().getSuministroLote().getUnidadSuministro().getNombreUnidad();
    }
    
}
