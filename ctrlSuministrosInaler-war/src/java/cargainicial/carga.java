
package cargainicial;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class carga implements Serializable{    
    @EJB
    private FacadeManejoProveedor fProv;
    @EJB
    private FacadeManejoOperario fOp;
    @EJB
    private ControladorPermiso cPermiso;
    @EJB
    private FacadeManejoSuministros fSuministro;
    
    public carga() {}
    public void cargar(){
        /*
        *  Proveedores
        */
        String contacto ="";
        fProv.RegistrarProveedor("Lakenor S.A. / Magiar Uruguay", contacto);
        fProv.RegistrarProveedor("Eleco", contacto);
        fProv.RegistrarProveedor("Lakenor S.A. / Magiar Uruguay", contacto);
        fProv.RegistrarProveedor("Biomereux / Tresul", contacto);
        fProv.RegistrarProveedor("DIU / Droguería Industrial Uruguaya", contacto);
        fProv.RegistrarProveedor("Bouruchovas", contacto);
        fProv.RegistrarProveedor("Emilio Benzo", contacto);
        fProv.RegistrarProveedor("Técnica del Plata", contacto);
        fProv.RegistrarProveedor("Biologistica / Netidal", contacto);
        fProv.RegistrarProveedor("Bionova", contacto);
        fProv.RegistrarProveedor("Farmacia Centro / Promed", contacto);
        fProv.RegistrarProveedor("Droguería Paysandú", contacto);
        fProv.RegistrarProveedor("Farmacia Luján", contacto);
        fProv.RegistrarProveedor("Districomp S.A.", contacto);
        fProv.RegistrarProveedor("Medilcalkit", contacto);
        
        /*
        *  Permisos / Operarios
        */
        int IdOperario;
        int IdPermiso;
        
        IdPermiso = cPermiso.CrearPermiso("Administrador");
        IdOperario = fOp.RegistrarOperario(3002425, "Diego", "Perez", "2017.Calidad");
        fOp.AgregarPermiso(IdOperario, IdPermiso);
        
        IdPermiso = cPermiso.CrearPermiso("Analista");
        IdOperario = fOp.RegistrarOperario(3000914, "Bruno", "Bracco", "ft");
        fOp.AgregarPermiso(IdOperario, IdPermiso);
        
        IdOperario = fOp.RegistrarOperario(3000952, "Lorena", "Peña", "ft");
        fOp.AgregarPermiso(IdOperario, IdPermiso);
        
        IdOperario = fOp.RegistrarOperario(3000964, "Noelia", "Taborda", "ft");
        fOp.AgregarPermiso(IdOperario, IdPermiso);
        
        IdPermiso = cPermiso.CrearPermiso("Verificador");
        IdOperario = fOp.RegistrarOperario(3000040, "Karin", "Muszwic", "karin.1");
        fOp.AgregarPermiso(IdOperario, IdPermiso);
        
        IdOperario = fOp.RegistrarOperario(3000042, "Cristina", "Caputi", "cristina.1");
        fOp.AgregarPermiso(IdOperario, IdPermiso);

        cPermiso.CrearPermiso("Lectura");
        
        /*
        *   Unidades
        */
        fSuministro.RegistrarUnidadSuministro("g");
        fSuministro.RegistrarUnidadSuministro("mg");
        fSuministro.RegistrarUnidadSuministro("Kg");
        fSuministro.RegistrarUnidadSuministro("mL");
        fSuministro.RegistrarUnidadSuministro("L");
        fSuministro.RegistrarUnidadSuministro("unidades");
        fSuministro.RegistrarUnidadSuministro("analisis");
        fSuministro.RegistrarUnidadSuministro("vial");
        fSuministro.RegistrarUnidadSuministro("cm");
        fSuministro.RegistrarUnidadSuministro("pares");
        fSuministro.RegistrarUnidadSuministro("kit");
        fSuministro.RegistrarUnidadSuministro("sobre");
        fSuministro.RegistrarUnidadSuministro("tiras");
        fSuministro.RegistrarUnidadSuministro("test");
        fSuministro.RegistrarUnidadSuministro("tiras");
        fSuministro.RegistrarUnidadSuministro("test");
        fSuministro.RegistrarUnidadSuministro("bolsas");
        fSuministro.RegistrarUnidadSuministro("hojas");
        fSuministro.RegistrarUnidadSuministro("placas");
        fSuministro.RegistrarUnidadSuministro("vasos");
        fSuministro.RegistrarUnidadSuministro("---");
        
        
        
    }
    
    
}
