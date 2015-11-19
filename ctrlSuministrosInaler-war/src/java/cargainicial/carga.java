
package cargainicial;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import java.io.Serializable;
import java.util.Date;
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
        
        Date FechaVigenteStockMinimo = new Date();
        int IdSuministro = 0;
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Agar cromogénico O157:H7","", "", 19,1);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Agar Verde Brillante Modificado CM 329","", "", 19,2);
        fSuministro.RegistrarStockMinimo(25f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Anaerogen Compact Clostridium","", "", 32,2);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Pruebas bioquimicas  Identification System Enteric/nonfermenter ID kit","", "", 33,3);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Bolsas Whirl-Pac de 3637 ml","", "", 35,10);
        fSuministro.RegistrarStockMinimo(120f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Bolsas Whirl-Pac pedir multiplo de 100","", "", 35,5);
        fSuministro.RegistrarStockMinimo(120f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("BPD  Fosfato de Potasio Monobásico MALLINCKRODT  500 g  (1800226)","", "", 19,6);
        fSuministro.RegistrarStockMinimo(34f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Buffer Listeria enrichment broth Reveal Fraser (BLEB)(23,5g /500 mL)","", "", 19,1);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Buffer pH 4,00 calibrado, coloreado","", "", 22,2);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Buffer pH 7,00 calibrado, coloreado","", "", 22,2);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Caldo MRS para bacterias acido lácticas","", "", 39,1);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Cinta para Autoclave","", "", 27,6);
        fSuministro.RegistrarStockMinimo(25f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("E Coli O157:H7 Latex test","", "", 32,2);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("E Coli O157-H7 test sistem Reveal ","", "", 32,1);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Half fraser caldo para listeria 55,2g/1 L","", "", 19,2);
        fSuministro.RegistrarStockMinimo(400f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Hoja de bisturí Nº22","", "", 36,6);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Hoja de bisturí Nº22","", "", 36,4);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Indicador biologico para est por vapor por 25 unidades","", "", 32,7);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Listeria Environmental Sampling System 20 por caja Reveal","", "", 32,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("magnetics beads E Coli O157-H7 para 50 test","", "", 32,1);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio agar sulfito hierro","", "", 19,2);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio azida (pad)","", "", 37,2);
        fSuministro.RegistrarStockMinimo(4f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio E Coli O157-H7 20 hs","", "", 19,1);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio endo marca sartorius  (pad) x 10 test","", "", 37,2);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Muller Kauffman tetrationato novobiocina (ISO) MKTTn","", "", 19,2);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio Oxford para listeria Oxoid","", "", 19,2);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio peptona water (Merck)","", "", 19,7);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio peptona water (Oxoid)","", "", 19,2);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Perfringens agar base (TSC ) ","", "", 19,7);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Rappaport-Vassiliadis","", "", 19,2);
        fSuministro.RegistrarStockMinimo(50f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio sorbitol Maconkey agar","", "", 19,2);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Triptona","", "", 39,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Novobiocina","", "", 20,2);
        fSuministro.RegistrarStockMinimo(270f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("PBS tween","", "", 30,1);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Perfringens (TSC) Selective Supplement para clostridium","", "", 26,2);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Petrifilm e coli","", "", 37,2);
        fSuministro.RegistrarStockMinimo(200f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("petrifilm enterobacterias","", "", 37,2);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("petrifilm listeria(placas ) MARCA 3M","", "", 37,2);
        fSuministro.RegistrarStockMinimo(20f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("petrifilm mesofilos (recuento aerobios)","", "", 37,2);
        fSuministro.RegistrarStockMinimo(150f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("petrifilm staphilos EXPRESS","", "", 37,2);
        fSuministro.RegistrarStockMinimo(150f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("pipetas Pasteur de 3 mL cambiaron codigo (pedir x 500)","", "", 24,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Placa petri esteril 90 x 15","", "", 37,2);
        fSuministro.RegistrarStockMinimo(50f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Plate Count","", "", 19,1);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Reveal for Listeria .","", "", 32,1);
        fSuministro.RegistrarStockMinimo(20f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Salmonella latex test","", "", 32,2);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Salmonella Rapid Test Medio para","", "", 39,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Salmonella Rapid Test (Oxoid) ","", "", 32,2);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Staphilasa","", "", 32,2);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Suplemento SR 172 Cefixime telurito (Mc Conkey)","", "", 26,2);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Suplemento sulfamandelato(para agar verde brillante modificado)  SR 0087E","", "", 26,2);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("tiosulfato de sodio 0,1 N","", "", 22,4);
        fSuministro.RegistrarStockMinimo(50f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Tira reactiva para Oxidasa","", "", 31,7);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Tiras reactivas nitrato/nitrito","", "", 31,2);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("TRYPTIC SOY BROTH, MODIFIED with NOVOBIOCIN 20 mg con CASEIN, ACID HIDROLYSATE   2 Kg  (incorporada)","", "", 19,1);
        fSuministro.RegistrarStockMinimo(5000f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("TRYPTIC SOY BROTH, MODIFIED with NOVOBIOCIN 8 mg con CASEIN, ACID HIDROLYSATE   2 Kg  (incorporada)","", "", 19,1);
        fSuministro.RegistrarStockMinimo(500f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("vial listeria suplemento selectivo formula Oxford SR 140","", "", 26,7);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("VRBGA violet red bile glucosa agar","", "", 19,2);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("XLD","", "", 19,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Mechero Bunsen","", "", 24,4);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Mango para Bisturi","", "", 24,4);
        fSuministro.RegistrarStockMinimo(20f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Punteros para macropipeta 1 mL","", "", 39,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Alcohol Isopropilico","", "", 22,4);
        fSuministro.RegistrarStockMinimo(200f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Guantes de latex","", "", 28,12);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Guantes de Latex Esteriles","", "", 28,13);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Alcohol Iodado","", "", 22,13);
        fSuministro.RegistrarStockMinimo(200f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Algodón 400 gramos / bolsa de 4 KG","", "", 39,10);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("recipientes para urocultivo (vasos estériles)","", "", 38,8);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Suplemento sulfamandelato (para agar verde brillante modificado) SR 0087E","", "", 26,2);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Alcohol Isopropilico","", "", 25,4);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Erlenmeyer","", "", 24,12);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio M-FC","", "", 37,2);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Pruebas bioquimicas  Identification System Listeria","", "", 36,3);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Punta de ansa","", "", 24,8);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("CHROmagar Ident. Lsiteria base","", "", 22,1);
        fSuministro.RegistrarStockMinimo(250f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("CHROmagar Ident. Listeria supplement","", "", 22,1);
        fSuministro.RegistrarStockMinimo(250f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("CHROmagar Listeria base","", "", 22,1);
        fSuministro.RegistrarStockMinimo(1000f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("CHROmagar Listeria supplement","", "", 22,1);
        fSuministro.RegistrarStockMinimo(1000f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Listeria  kit de muestreo 20 por caja","", "", 24,1);
        fSuministro.RegistrarStockMinimo(20f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Listeria Environmental Sampling System 20 por caja Medio Less","", "", 34,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Listeria Environmental Sampling System 20 por caja esponjas","", "", 34,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Listeria Environmental Sampling System 20 por caja","", "", 29,1);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio Oxford para Listeria (Neogen)","", "", 19,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Reactivos para pruebas bioquimicas 20 E","", "", 25,3);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("RIM E coli O157:H7 latex test","", "", 25,2);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Papel sulfito","", "", 24,14);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Frascos de 1 litro","", "", 24,8);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Frascos de 1/2 litro","", "", 24,8);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Frascos de 1/2 litro","", "", 24,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Frasco de 1/4 litro","", "", 24,8);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Mango de ansa","", "", 24,8);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Less","", "", 19,1);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("pisetas","", "", 24,4);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("probeta de 500 ml de plastico autoclavables","", "", 24,4);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Varilla de vidrio","", "", 24,7);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio agar Rambach","", "", 19,1);
        fSuministro.RegistrarStockMinimo(30.7f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Bolsas Stomacher","", "", 24,10);
        fSuministro.RegistrarStockMinimo(50f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Salmonella Reveal","", "", 19,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("escala de Mc Farland","", "", 24,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("punteros para macropipetas de 10 mL","", "", 24,2);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("punteros para micropipeta con filtro","", "", 25,8);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("ANSR 2 medio para salmonella","", "", 19,1);
        fSuministro.RegistrarStockMinimo(500f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("ANSR 1 medio para listeria","", "", 19,1);
        fSuministro.RegistrarStockMinimo(500f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("KIT ANSR  para listeria","", "", 24,1);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("KIT ANSR listeria mono","", "", 24,1);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Less Plus","", "", 19,1);
        fSuministro.RegistrarStockMinimo(50f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("KIT ANSR para salmonella","", "", 24,1);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("PBS sin tween","", "", 24,1);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("HCl","", "", 22,4);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        
        
    }
    
    
}
