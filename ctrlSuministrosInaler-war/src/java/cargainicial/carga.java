
package cargainicial;

import com.dperez.inalerlab.operario.FacadeManejoOperario;
import com.dperez.inalerlab.operario.permiso.ControladorPermiso;
import com.dperez.inalerlab.proveedor.FacadeManejoProveedor;
import com.dperez.inalerlab.suministro.FacadeManejoSuministros;
import com.dperez.inalerlab.suministro.lote.FacadeLote;
import java.io.Serializable;
import java.util.Calendar;
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
    @EJB
    private FacadeLote fLote;
    
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
        
        IdOperario = fOp.RegistrarOperario(3000898, "Analuz", "Medina", "ft");
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
        
        Date FechaVigenteStockMinimo = Calendar.getInstance().getTime();
        int IdSuministro = 0;
        int IdLote = 0;
        Calendar cal = Calendar.getInstance();
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Agar cromogénico O157:H7","", "", 20,1);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Agar Verde Brillante Modificado CM 329","", "", 20,2);
        fSuministro.RegistrarStockMinimo(25f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Anaerogen Compact Clostridium","", "", 20,2);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Pruebas bioquimicas  Identification System Enteric/nonfermenter ID kit","", "", 34,3);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMaterial("Bolsas Whirl-Pac de 3637 ml","", "", 36,10);
        fSuministro.RegistrarStockMinimo(120f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"8095");
        cal.set(2015,9,21);
        fLote.CrearIngreso(cal.getTime(),100.0f,"2163",IdLote,3000964,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000964,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("Bolsas Whirl-Pac pedir multiplo de 100","", "", 36,5);
        fSuministro.RegistrarStockMinimo(120f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("BPD  Fosfato de Potasio Monobásico MALLINCKRODT  500 g  (1800226)","", "", 20,6);
        fSuministro.RegistrarStockMinimo(34f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Buffer Listeria enrichment broth Reveal Fraser (BLEB)(23,5g /500 mL)","", "", 20,1);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Buffer pH 4,00 calibrado, coloreado","", "", 21,2);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Buffer pH 7,00 calibrado, coloreado","", "", 21,2);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Caldo MRS para bacterias acido lácticas","", "", 40,1);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2012,10,1);
        IdLote = fLote.CrearLote(cal.getTime(),"102377");
        cal.set(2009,7,4);
        fLote.CrearIngreso(cal.getTime(),500.0f,"3079",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),500.0f,IdLote,3000042,"Desechado por vencimiento, no ha ingresado nuevo ya que no se esta realizando el ensayo, no ventas ");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("Cinta para Autoclave","", "", 28,6);
        fSuministro.RegistrarStockMinimo(25f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("E Coli O157:H7 Latex test","", "", 33,2);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("E Coli O157-H7 test sistem Reveal ","", "", 33,1);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Half fraser caldo para listeria 55,2g/1 L","", "", 20,2);
        fSuministro.RegistrarStockMinimo(400f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Hoja de bisturí Nº22","", "", 37,6);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Hoja de bisturí Nº22","", "", 37,4);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Indicador biologico para est por vapor por 25 unidades","", "", 33,7);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Listeria Environmental Sampling System 20 por caja Reveal","", "", 33,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("magnetics beads E Coli O157-H7 para 50 test","", "", 33,1);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio agar sulfito hierro","", "", 20,2);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio azida (pad)","", "", 38,2);
        fSuministro.RegistrarStockMinimo(4f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio E Coli O157-H7 20 hs","", "", 20,1);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio endo marca sartorius  (pad) x 10 test","", "", 38,2);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Muller Kauffman tetrationato novobiocina (ISO) MKTTn","", "", 20,2);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio Oxford para listeria Oxoid","", "", 20,2);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2013,6,1);
        IdLote = fLote.CrearLote(cal.getTime(),"672041");
        cal.set(2009,6,10);
        fLote.CrearIngreso(cal.getTime(),500.0f,"139422",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),500.0f,IdLote,3000042,"se compra otra marca.");fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio peptona water (Merck)","", "", 20,7);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio peptona water (Oxoid)","", "", 20,2);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Perfringens agar base (TSC ) ","", "", 20,7);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Rappaport-Vassiliadis","", "", 20,2);
        fSuministro.RegistrarStockMinimo(50f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio sorbitol Maconkey agar","", "", 20,2);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2015,8,31);
        IdLote = fLote.CrearLote(cal.getTime(),"932335");
        cal.set(2011,2,4);
        fLote.CrearIngreso(cal.getTime(),500.0f,"167862",IdLote,3000042,"");fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Triptona","", "", 40,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Novobiocina","", "", 21,2);
        fSuministro.RegistrarStockMinimo(270f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarReactivoQuimico("PBS tween","", "", 31,1);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"090M8212");
        cal.set(2012,4,12);
        fLote.CrearIngreso(cal.getTime(),20.0f,"5226",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Perfringens (TSC) Selective Supplement para clostridium","", "", 27,2);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Petrifilm e coli","", "", 38,2);
        fSuministro.RegistrarStockMinimo(200f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("petrifilm enterobacterias","", "", 38,2);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("petrifilm listeria(placas ) MARCA 3M","", "", 38,2);
        fSuministro.RegistrarStockMinimo(20f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("petrifilm mesofilos (recuento aerobios)","", "", 38,2);
        fSuministro.RegistrarStockMinimo(150f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("petrifilm staphilos EXPRESS","", "", 38,2);
        fSuministro.RegistrarStockMinimo(150f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("pipetas Pasteur de 3 mL cambiaron codigo (pedir x 500)","", "", 25,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Placa petri esteril 90 x 15","", "", 38,2);
        fSuministro.RegistrarStockMinimo(50f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Plate Count","", "", 20,1);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Reveal for Listeria .","", "", 33,1);
        fSuministro.RegistrarStockMinimo(20f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Salmonella latex test","", "", 33,2);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Salmonella Rapid Test Medio para","", "", 40,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2016,9,30);
        IdLote = fLote.CrearLote(cal.getTime(),"1093144");
        cal.set(2013,3,1);
        fLote.CrearIngreso(cal.getTime(),630.0f,"205098",IdLote,3000898,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000898,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Salmonella Rapid Test (Oxoid) ","", "", 33,2);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Staphilasa","", "", 32,2);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Suplemento SR 172 Cefixime telurito (Mc Conkey)","", "", 27,2);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Suplemento sulfamandelato(para agar verde brillante modificado)  SR 0087E","", "", 27,2);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2013,12,31);
        IdLote = fLote.CrearLote(cal.getTime(),"1129314");
        cal.set(2012,11,5);
        fLote.CrearIngreso(cal.getTime(),12.0f,"199268",IdLote,3000898,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),12.0f,IdLote,3000898,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarReactivoQuimico("tiosulfato de sodio 0,1 N","", "", 23,4);
        fSuministro.RegistrarStockMinimo(50f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Tira reactiva para Oxidasa","", "", 32,7);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Tiras reactivas nitrato/nitrito","", "", 32,2);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2014,2,1);
        IdLote = fLote.CrearLote(cal.getTime(),"313130");
        cal.set(2012,6,7);
        fLote.CrearIngreso(cal.getTime(),100.0f,"191907",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),100.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("TRYPTIC SOY BROTH, MODIFIED with NOVOBIOCIN 20 mg con CASEIN, ACID HIDROLYSATE   2 Kg  (incorporada)","", "", 20,1);
        fSuministro.RegistrarStockMinimo(5000f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("TRYPTIC SOY BROTH, MODIFIED with NOVOBIOCIN 8 mg con CASEIN, ACID HIDROLYSATE   2 Kg  (incorporada)","", "", 20,1);
        fSuministro.RegistrarStockMinimo(500f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("vial listeria suplemento selectivo formula Oxford SR 140","", "", 27,7);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("VRBGA violet red bile glucosa agar","", "", 20,2);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("XLD","", "", 20,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMaterial("Mechero Bunsen","", "", 25,4);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"n.a.");
        cal.set(2013,5,11);
        fLote.CrearIngreso(cal.getTime(),2.0f,"123888",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("Mango para Bisturi","", "", 25,4);
        fSuministro.RegistrarStockMinimo(20f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"n.a.");
        cal.set(2013,5,11);
        fLote.CrearIngreso(cal.getTime(),20.0f,"123887",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("Punteros para macropipeta 1 mL","", "", 40,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2020,8,19);
        IdLote = fLote.CrearLote(cal.getTime(),"430233");
        cal.set(2015,8,19);
        fLote.CrearIngreso(cal.getTime(),500.0f,"6876",IdLote,3000964,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000964,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        
        
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Alcohol Isopropilico","", "", 23,4);
        fSuministro.RegistrarStockMinimo(200f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Guantes de latex","", "", 29,12);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Guantes de Latex Esteriles","", "", 29,13);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Alcohol Iodado","", "", 23,13);
        fSuministro.RegistrarStockMinimo(200f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMaterial("Algodón 400 gramos / bolsa de 4 KG","", "", 40,10);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2012,10,4);
        IdLote = fLote.CrearLote(null,"n.a.");
        cal.set(2012,10,4);
        fLote.CrearIngreso(cal.getTime(),4.50f,"9571",IdLote,3000898,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000898,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("recipientes para urocultivo (vasos estériles)","", "", 39,8);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2017,3,1);
        IdLote = fLote.CrearLote(cal.getTime(),"31302082");
        cal.set(2014,5,23);
        fLote.CrearIngreso(cal.getTime(),300.0f,"17238",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Suplemento sulfamandelato (para agar verde brillante modificado) SR 0087E","", "", 27,2);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Alcohol Isopropilico","", "", 26,4);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"2219");
        cal.set(2013,9,2);
        fLote.CrearIngreso(cal.getTime(),2000.0f,"934041",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),2000.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("Erlenmeyer","", "", 25,12);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"n.a.");
        cal.set(2012,6,21);
        fLote.CrearIngreso(cal.getTime(),3.0f,"163",IdLote,3000898,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000898,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio M-FC","", "", 38,2);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Pruebas bioquimicas  Identification System Listeria","", "", 37,3);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMaterial("Punta de ansa","", "", 25,8);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"2055");
        cal.set(2013,1,9);
        fLote.CrearIngreso(cal.getTime(),26.0f,"14731",IdLote,3000898,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000898,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("CHROmagar Ident. Lsiteria base","", "", 23,1);
        fSuministro.RegistrarStockMinimo(250f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("CHROmagar Ident. Listeria supplement","", "", 23,1);
        fSuministro.RegistrarStockMinimo(250f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("CHROmagar Listeria base","", "", 23,1);
        fSuministro.RegistrarStockMinimo(1000f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("CHROmagar Listeria supplement","", "", 23,1);
        fSuministro.RegistrarStockMinimo(1000f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Listeria  kit de muestreo 20 por caja","", "", 26,1);
        fSuministro.RegistrarStockMinimo(20f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Listeria Environmental Sampling System 20 por caja Medio Less","", "", 35,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Listeria Environmental Sampling System 20 por caja esponjas","", "", 35,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Listeria Environmental Sampling System 20 por caja","", "", 30,1);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio Oxford para Listeria (Neogen)","", "", 20,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarReactivoQuimico("Reactivos para pruebas bioquimicas 20 E","", "", 26,3);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("RIM E coli O157:H7 latex test","", "", 26,2);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2014,3,12);
        IdLote = fLote.CrearLote(cal.getTime(),"169226");
        cal.set(2013,5,23);
        fLote.CrearIngreso(cal.getTime(),50.0f,"209552",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),50.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("Papel sulfito","", "", 25,14);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"n.a.");
        cal.set(2013,5,24);
        fLote.CrearIngreso(cal.getTime(),10.0f,"198956",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),10.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("Frascos de 1 litro","", "", 25,8);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"n.a.");
        cal.set(2013,6,5);
        fLote.CrearIngreso(cal.getTime(),5.0f,"15791",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("Frascos de 1/2 litro","", "", 25,8);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("Frascos de 1/2 litro","", "", 25,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMaterial("Frasco de 1/4 litro","", "", 25,8);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"n.a.");
        cal.set(2013,6,5);
        fLote.CrearIngreso(cal.getTime(),3.0f,"15791",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("Mango de ansa","", "", 25,8);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"C3WJ - D8GN");
        cal.set(2013,6,5);
        fLote.CrearIngreso(cal.getTime(),2.0f,"15791",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Less","", "", 20,1);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMaterial("pisetas","", "", 24,4);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"n.a.");
        cal.set(2014,5,19);
        fLote.CrearIngreso(cal.getTime(),10.0f,"143333",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("probeta de 500 ml de plastico autoclavables","", "", 25,4);
        fSuministro.RegistrarStockMinimo(5f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"n.a.");
        cal.set(2014,5,19);
        fLote.CrearIngreso(cal.getTime(),4.0f,"143333",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("Varilla de vidrio","", "", 25,7);
        fSuministro.RegistrarStockMinimo(2f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"n.a.");
        cal.set(2014,8,27);
        fLote.CrearIngreso(cal.getTime(),20.0f,"280030",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("medio agar Rambach","", "", 20,1);
        fSuministro.RegistrarStockMinimo(30.7f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMaterial("Bolsas Stomacher","", "", 25,10);
        fSuministro.RegistrarStockMinimo(50f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2039,3,1);
        IdLote = fLote.CrearLote(cal.getTime(),"981");
        cal.set(2014,10,23);
        fLote.CrearIngreso(cal.getTime(),500.0f,"1375",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Salmonella Reveal","", "", 20,1);
        fSuministro.RegistrarStockMinimo(30f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMaterial("escala de Mc Farland","", "", 25,2);
        fSuministro.RegistrarStockMinimo(0f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2015,10,9);
        IdLote = fLote.CrearLote(cal.getTime(),"520690");
        cal.set(2014,11,13);
        fLote.CrearIngreso(cal.getTime(),1.0f,"236593",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("punteros para macropipetas de 10 mL","", "", 25,2);
        fSuministro.RegistrarStockMinimo(10f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("punteros para micropipeta con filtro","", "", 26,8);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("ANSR 2 medio para salmonella","", "", 20,1);
        fSuministro.RegistrarStockMinimo(500f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMedioEnsayo("ANSR 1 medio para listeria","", "", 20,1);
        fSuministro.RegistrarStockMinimo(500f, FechaVigenteStockMinimo, IdSuministro);
        IdSuministro = fSuministro.RegistrarMaterial("KIT ANSR  para listeria","", "",25,1);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMaterial("KIT ANSR listeria mono","", "", 25,1);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2015,11,4);
        IdLote = fLote.CrearLote(cal.getTime(),"216759");
        cal.set(2015,8,13);
        fLote.CrearIngreso(cal.getTime(),96.0f,"8389",IdLote,3000964,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000964,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("Medio Less Plus","", "", 20,1);
        fSuministro.RegistrarStockMinimo(50f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2019,5,31);
        IdLote = fLote.CrearLote(cal.getTime(),"107712 A");
        cal.set(2015,8,17);
        fLote.CrearIngreso(cal.getTime(),500.0f,"8389",IdLote,3000964,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000964,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarMaterial("KIT ANSR para salmonella","", "", 25,1);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        
        IdSuministro = fSuministro.RegistrarMedioEnsayo("PBS sin tween","", "", 25,1);
        fSuministro.RegistrarStockMinimo(1f, FechaVigenteStockMinimo, IdSuministro);
        IdLote = fLote.CrearLote(null,"8425");
        cal.set(2015,1,20);
        fLote.CrearIngreso(cal.getTime(),5.0f,"8065",IdLote,3000042,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),1.0f,IdLote,3000042,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);
        
        IdSuministro = fSuministro.RegistrarReactivoQuimico("HCl","", "", 23,4);
        fSuministro.RegistrarStockMinimo(100f, FechaVigenteStockMinimo, IdSuministro);
        cal.set(2017,4,1);
        IdLote = fLote.CrearLote(cal.getTime(),"L150422-01");
        cal.set(2015,6,9);
        fLote.CrearIngreso(cal.getTime(),1000.0f,"12248",IdLote,3000964,"");
        fLote.CrearSalida(Calendar.getInstance().getTime(),0.0f,IdLote,3000964,"");
        fLote.AgregarLoteSuministro(IdSuministro,IdLote);        
        
    }
    
    
}
