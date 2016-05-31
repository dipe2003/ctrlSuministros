
package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import com.dperez.inalerlab.suministro.lote.Lote;
import com.dperez.inalerlab.suministro.lote.Salida;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import web.interfaz.pdf.PdfBox;

@Named
@ViewScoped
public class InfoSuministro implements Serializable{
    @EJB
    private FacadeManejoSuministros fSuministro;
    @EJB
    private PdfBox pdf;
    
    private Suministro SuministroSeleccionado;
    private List<Lote> LotesSuministro;
    private Map<Integer, List<Ingreso>> IngresosSuministro;
    private Map<Integer, List<Salida>> SalidasSuministro;
    private Map<Integer, Boolean> LotesVencido;
    
    //  Getters
    public List<Lote> getLotesSuministro() {return LotesSuministro;}
    public Map<Integer, List<Ingreso>> getIngresosSuministro() {return IngresosSuministro;}
    public Map<Integer, List<Salida>> getSalidasSuministro() {return SalidasSuministro;}
    public Suministro getSuministroSeleccionado() {return SuministroSeleccionado;}
    public Map<Integer, Boolean> getLotesVencido() {return LotesVencido;}
    
    //  Setters
    public void setLotesSuministro(List<Lote> LotesSuministro) {this.LotesSuministro = LotesSuministro;}
    public void setIngresosSuministro(Map<Integer, List<Ingreso>> IngresosSuministro) {this.IngresosSuministro = IngresosSuministro;}
    public void setSalidasSuministro(Map<Integer, List<Salida>> SalidasSuministro) {this.SalidasSuministro = SalidasSuministro;}
    public void setSuministroSeleccionado(Suministro SuministroSeleccionado) {this.SuministroSeleccionado = SuministroSeleccionado;}
    public void setLotesVencido(Map<Integer, Boolean> LotesVencido) {this.LotesVencido = LotesVencido;}
    
    //PDF
    public void exportarPdf() throws IOException{
        ByteArrayOutputStream documento =  new ByteArrayOutputStream();
        try{
            documento = pdf.CrearPdf(SuministroSeleccionado.getNombreSuministro());
        }catch(IOException | COSVisitorException ex){
            System.out.println("Error al crear pdf: " + ex.getMessage());
        }
        
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        
        response.reset(); // Limpiar el buffer por contenido dejado en otras llamadas.
        response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types.
        response.setContentLength(documento.size()); // Es opcional pero si no se especifica no se calcula el tiempo de descarga.
        response.setHeader("Content-Disposition", "attachment; filename=\"" + "Info de Suministro " + String.valueOf(SuministroSeleccionado.getIdSuministro())+".pdf" + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.
        
        OutputStream output = response.getOutputStream();
        output.write(documento.toByteArray());
        
        fc.responseComplete(); // Importante! sino se intentara renderizar la respuesta y se genera una excepcion porque el archivo ya se cerro.
    }
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        int id = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("id"));
        SuministroSeleccionado = fSuministro.BuscarSuministro(id);
        LotesSuministro = SuministroSeleccionado.getLotesSuministros();
        IngresosSuministro = new HashMap<>();
        SalidasSuministro = new HashMap<>();
        LotesVencido = new HashMap<>();
        for(Lote lote: LotesSuministro){
            IngresosSuministro.put(lote.getIdLote(), lote.getIngresosLote());
            SalidasSuministro.put(lote.getIdLote(), lote.getSalidasLote());
            LotesVencido.put(lote.getIdLote(), lote.EstaVencido());
        }
    }
}
