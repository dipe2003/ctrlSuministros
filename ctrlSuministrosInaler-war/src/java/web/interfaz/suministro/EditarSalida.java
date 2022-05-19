
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.FacadeLote;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import com.dperez.inalerlab.suministro.lote.Lote;
import com.dperez.inalerlab.suministro.lote.Salida;
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
public class EditarSalida implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;
    @EJB
    private FacadeLote fLote;
    
    private int IdLote;
    private int IdSuministro;
    private int IdSalida;
    
    private String NumeroLote;
    
    private float CantidadSalida;
    private Date FechaSalida;
    private String strFechaSalida;
    
    
    private String UnidadSuministro;
    
    private Lote LoteSuministro;
    
    private Suministro SuministroSeleccionado;
    
//  getters
    public int getIdSalida() {return IdSalida;}
    public String getNumeroLote() {return NumeroLote;}
    public String getUnidadSuministro() {return UnidadSuministro;}
    public float getCantidadSalida() {return CantidadSalida;}
    public Date getFechaSalida() {return FechaSalida;}
    public String getStrFechaSalida() {
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        if (FechaSalida == null) {
            return this.strFechaSalida;
        }else{
            return fDate.format(FechaSalida);
        }
    }
    
    //  setters
    public void setIdSalida(int IdSalida) {this.IdSalida = IdSalida;}
    public void setNumeroLote(String NumeroLote) {this.NumeroLote = NumeroLote;}
    public void setUnidadSuministro(String UnidadSuministro) {this.UnidadSuministro = UnidadSuministro;}
    public void setCantidadSalida(float CantidadSalida) {this.CantidadSalida = CantidadSalida;}
    public void setFechaSalida(Date FechaSalida) {this.FechaSalida = FechaSalida;}
    public void setStrFechaSalida(String strFechaSalida) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            cal.setTime(sdf.parse(strFechaSalida));
        }catch(ParseException ex){}
        this.strFechaSalida = strFechaSalida;
        this.FechaSalida = cal.getTime();
    }
    
    
    //  Metodos
    public void editarSalida() throws IOException{
        String msj ="No se pudo actualizar.";
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        
        if(CantidadSalida <= 0){
            msj = "La cantidad ingresada no es correcta.";
        }else{
            // actualizar el ingreso/lote luego actualizar buffer
            if(fLote.ActualizarLoteSalida(SuministroSeleccionado.getIdSuministro(), LoteSuministro.getIdLote(), IdSalida, CantidadSalida, FechaSalida)!=-1){
                FacesContext.getCurrentInstance().getExternalContext().redirect(url+"/Views/Suministro/InfoSuministro.xhtml?id="+SuministroSeleccionado.getIdSuministro());
                FacesContext.getCurrentInstance().renderResponse();
                FacesContext.getCurrentInstance().responseComplete();
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
            IdSalida = Integer.parseInt(request.getParameter("id"));
            IdLote = Integer.parseInt(request.getParameter("idlote"));
            IdSuministro = Integer.parseInt(request.getParameter("idSum"));
            cargarDatosIngreso();
        }catch(NullPointerException | NumberFormatException ex){}
    }
    
    
    private void cargarDatosIngreso(){
        SuministroSeleccionado = fSuministro.BuscarSuministro(IdSuministro);
        LoteSuministro = SuministroSeleccionado.FindLote(IdLote);
        Salida salida = LoteSuministro.FindSalida(IdSalida);
        
        NumeroLote = LoteSuministro.getNumeroLote();
        
        CantidadSalida = salida.getCantidadSalida();
        UnidadSuministro = SuministroSeleccionado.getUnidadSuministro().getNombreUnidad();
        
        FechaSalida = salida.getFechaSalida();
        SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
        strFechaSalida = fDate.format(FechaSalida);
        
    }
    
}
