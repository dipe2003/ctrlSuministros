package web.interfaz.suministro;

import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class RegistrarUnidad implements Serializable {

    @EJB
    private FacadeManejoSuministros fSuministro;

    private String NombreUnidad;

    private List<Unidad> unidadesRegistradas;

    //  getters
    public String getNombreUnidad() {
        return NombreUnidad;
    }

    //  setters
    public void setNombreUnidad(String NombreUnidad) {
        this.NombreUnidad = NombreUnidad;
    }

    public void registrarUnidad() throws IOException {
        if (NombreUnidad != null && !NombreUnidad.isEmpty()) {
            if (unidadesRegistradas.stream()
                    .anyMatch(u -> u.getNombreUnidad().equalsIgnoreCase(NombreUnidad)) == true) {
                mostrarMensaje("No se pudo registrar. Esta unidad ya esta registrada.");
            } else {
                if (fSuministro.RegistrarUnidadSuministro(NombreUnidad) != -1) {
                    redirigir();
                } else {
                    mostrarMensaje("No se pudo registrar.");
                }
            }
        }
    }

    private void redirigir() throws IOException {
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(url + "/Views/Suministro/ListadoUnidades.xhtml");
    }

    private void mostrarMensaje(String msj) {
        FacesContext.getCurrentInstance().addMessage("frmRegUnidad:btnRegistrarUnidad", new FacesMessage(msj));
    }

    @PostConstruct
    public void init() {
        unidadesRegistradas = fSuministro.ListarUnidades();
    }

}
