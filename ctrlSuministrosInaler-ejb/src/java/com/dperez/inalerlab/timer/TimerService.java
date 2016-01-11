
package com.dperez.inalerlab.timer;

import com.dperez.inalerlab.email.SendMail;
import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.Suministro;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 *
 * @author dperez
 */
@Singleton
public class TimerService {
    @Inject
    private SendMail mail;
    
    @EJB
    private FacadeManejoSuministros fSum;
    
    @EJB
    private FacadeManejoOperario fOp;
    
    // todos los domingos de todas las semanas, todos los años, a la hora 4:00 0s
    @Schedule(dayOfWeek = "Sun", month = "*", hour = "4", dayOfMonth = "*", year = "*", minute = "0", second = "0")
    
    // solo para testing envio cada 2 minutos:
    //@Schedule(month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "0/2", second = "0")
    
    public void myTimer() {
        List<Suministro> suministros = fSum.ListarSuministros(true);
        List<String> sumVencidos = new ArrayList<>();
        List<String> sumStockMinimo = new ArrayList<>();
        for (Suministro sum: suministros) {
            if(sum.isDebajoStockMinimo()) sumStockMinimo.add(sum.getNombreSuministro());
            if(!sum.getLotesVencidosEnStock().isEmpty()) sumVencidos.add(sum.getNombreSuministro());
        }
        if(!sumVencidos.isEmpty() || !sumStockMinimo.isEmpty()){
            try {                
                String asunto = "Control de Suministros";
                String mensaje = getMensaje(sumVencidos, sumStockMinimo);
                List<Operario> operarios = fOp.ListarOperarios();
                
                for(Operario op: operarios){
                    if(op.isRecibeAlertas() && !op.getCorreoOperario().isEmpty()) mail.enviarMail(op.getCorreoOperario(), mensaje, asunto);
                }
            }catch(NullPointerException ex){
                System.out.println("Error: No se pudo enviar correos:" + ex.getMessage());
            }
        }
    }
    
    private String getMensaje(List<String> SuministrosVencidosStock, List<String> SuministrosDebajoStock){
        String msj = "<p style='font-family: sans-serif;'><h1 style='color: blue;'> Control Suministros </h1><br></br>";
        if(!SuministrosVencidosStock.isEmpty()){
            msj += "<h3>Los siguientes suministros se encuentran vencidos y con stock: </h3><br></br>";
            msj += "<ul>";
            for (String str: SuministrosVencidosStock) {
                msj += "<li>" + str + "</li>";
            }
            msj += "</ul><br></br>";
        }
        if(!SuministrosDebajoStock.isEmpty()){
            msj += "<h3>Los siguientes suministros se encuentran por debajo del stock minimo: </h3><br></br>";
            msj += "<ul>";
            for (String str: SuministrosDebajoStock) {
                msj += "<li>" + str + "</li>";
            }
            msj += "</ul><br></br>";
        }
        msj += "</p>";
        return msj;
    }
    
}
