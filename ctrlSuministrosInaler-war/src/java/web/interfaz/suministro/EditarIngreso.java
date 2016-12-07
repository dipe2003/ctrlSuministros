
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.FacadeLote;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import com.dperez.inalerlab.suministro.lote.Lote;
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
    private String NumeroLoteOriginal;
    
    private float CantidadIngreso;
    private Date FechaIngreso;
    private String strFechaIngreso;
    
    private Date FechaVencimiento;
    private String strFechaVencimiento;
    
    private String UnidadSuministro;
    
    private Lote LoteSuministro;
    
    private Suministro SuministroSeleccionado;
    
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
    public Date getFechaVencimiento() {return FechaVencimiento;}
    public String getStrFechaVencimiento() {
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        if (FechaVencimiento == null) {
            return this.strFechaVencimiento;
        }else{
            return fDate.format(FechaVencimiento);
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
    public void setFechaVencimiento(Date FechaVencimiento) {this.FechaVencimiento = FechaVencimiento;}
    public void setStrFechaVencimiento(String strFechaVencimiento) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            cal.setTime(sdf.parse(strFechaVencimiento));
        }catch(ParseException ex){}
        this.strFechaVencimiento = strFechaVencimiento;
        this.FechaVencimiento = cal.getTime();
    }
    
    
    //  Metodos
    public void editarIngreso() throws IOException{
        int idSuministro = -1;
        String msj ="";
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        
        /*
        TODO:
        1) Comprobar que el numero de lote ingresado no existe antes de actualizar o que no haya sido cambiado.
        2) Permitir que se utilice un numero de lote ingresado y realizar las actualizaciones correspondientes en la
        base de datos para que el ingreso pertenezca a ese lote. En este caso la fecha de vencimiento debe comprobarse por
        modificaciones ya que pasaria a ser la guardada del lote existente seleccionado y no la ingresada en la pantalla edicion.
        */
        
        if(SuministroSeleccionado.ExisteNumeroLote(NumeroLote) && !NumeroLoteOriginal.equalsIgnoreCase(NumeroLote)){
            msj = "El numero de lote ingresado ya existe";
        }else{
            // actualizar el ingreso/lote
            if(fLote.ActualizarLoteIngreso(SuministroSeleccionado.getIdSuministro(), LoteSuministro.getIdLote(), IdIngreso, NumeroLote, CantidadIngreso, FechaVencimiento, NumeroFactura)!=-1){
                FacesContext.getCurrentInstance().getExternalContext().redirect(url+"/Views/Suministro/InfoSuministro.xhtml?id="+SuministroSeleccionado.getIdSuministro());
                FacesContext.getCurrentInstance().renderResponse();
                FacesContext.getCurrentInstance().responseComplete();
            }else{
                msj = "No se pudo actualizar.";
            }
        }
        FacesContext.getCurrentInstance().addMessage("frmEditIngreso:btnEditarIngreso", new FacesMessage(msj));
        FacesContext.getCurrentInstance().renderResponse();
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
        LoteSuministro = ingreso.getLoteIngreso();
        NumeroFactura = ingreso.getNumeroFactura();
        NumeroLote = ingreso.getLoteIngreso().getNumeroLote();
        // El NumeroLoteOriginal es utilizado para comprobar si se cambio dentro de la edición.
        NumeroLoteOriginal = NumeroLote;
        CantidadIngreso = ingreso.getCantidadIngreso();
        FechaIngreso = ingreso.getFechaIngreso();
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        strFechaIngreso = fDate.format(FechaIngreso);
        FechaVencimiento = LoteSuministro.getVencimientoLote();
        SimpleDateFormat fVtoDate = new SimpleDateFormat("dd/MM/yyyy");
        strFechaVencimiento = fVtoDate.format(FechaVencimiento);
        UnidadSuministro =ingreso.getLoteIngreso().getSuministroLote().getUnidadSuministro().getNombreUnidad();
        
        SuministroSeleccionado = fSuministro.BuscarSuministro(LoteSuministro.getSuministroLote().getIdSuministro());
    }
    
}
