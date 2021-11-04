package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.buffer.BufferGenerico;
import com.dperez.inalerlab.buffer.FabricaBuffer;
import com.dperez.inalerlab.email.SendMail;
import com.dperez.inalerlab.operario.ControladorOperario;
import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.suministro.stockminimo.StockMinimo;
import com.dperez.inalerlab.proveedor.ControladorProveedor;
import com.dperez.inalerlab.suministro.stockminimo.ControladorStockMinimo;
import com.dperez.inalerlab.suministro.unidad.ControladorUnidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Stateless
public class ControladorSuministro implements Serializable {
    
    @Inject
    private ManejadorSuministro mSuministro;
    @Inject
    private ControladorUnidad cUnidad;
    @Inject
    private ControladorProveedor cProveedor;
    @Inject
    private ControladorStockMinimo cStock;
    @Inject
    private ControladorOperario cOps;
    @Inject
    private SendMail mail;
    @Inject
    private FabricaBuffer fBuffer;
    
    private BufferGenerico<Suministro> buffer;
    
    @PostConstruct
    public void init(){
        buffer = fBuffer.getBufferSuministro(mSuministro.ListarSuministros());
    }
    
    
    /**
     * Crea un Crea un Reactivo Quimico en la base de datos.
     *
     * @param NombreSuministro
     * @param DescripcionSuministro
     * @param CodigoSAPSuministro
     * @param IdUnidadSuministro
     * @param IdProveedorSuministro
     * @param TipoSuministro
     * @param AvisoCambioLote
     * @return Devuelve el Id del suministro creado. Devuelve -1 cuando no se
     * guardo el suministro.
     */
    public int CrearSuministro(String NombreSuministro, String DescripcionSuministro,
            String CodigoSAPSuministro, int IdUnidadSuministro, int IdProveedorSuministro, EnumSuministro TipoSuministro, boolean AvisoCambioLote) {
        Suministro suministro = null;
        switch (TipoSuministro.toString()) {
            case "Material":
                suministro = new Material(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro,
                        cUnidad.BuscarUnidad(IdUnidadSuministro), cProveedor.BuscarProveedor(IdProveedorSuministro), AvisoCambioLote);
                break;
                
            case "MedioEnsayo":
                suministro = new MedioEnsayo(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro,
                        cUnidad.BuscarUnidad(IdUnidadSuministro), cProveedor.BuscarProveedor(IdProveedorSuministro), AvisoCambioLote);
                break;
                
            case "ReactivoQuimico":
                suministro = new ReactivoQuimico(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro,
                        cUnidad.BuscarUnidad(IdUnidadSuministro), cProveedor.BuscarProveedor(IdProveedorSuministro), AvisoCambioLote);
                break;
        }
        int id = mSuministro.CrearSuministro(suministro);
        if(id>0){
            buffer.putEntidad(suministro, id);
        }
        return id;
    }
    
    /**
     * Busca un suministro segun el id especificado.
     *
     * @param IdSuministro
     * @return
     */
    public Suministro BuscarSuministro(int IdSuministro) {
        return buffer.getEntidad(IdSuministro);
    }
    
    /**
     * Devuelve todos los suministros registrados en la base de datos según su
     * vigencia. Si no hay suministros devuelve una lista vacia.
     *
     * @param Vigente True: indica si solo se devuelven los suministros en uso,
     * de lo contrario False.
     * @return
     */
    public List<Suministro> ListarSuministros(boolean Vigente) {
        if(Vigente){
            return buffer.getListaEntidades().stream()
                    .filter((Suministro s)->s.isVigente())
                    .sorted()
                    .collect(Collectors.toList());
        }
        return buffer.getListaEntidades();
    }
    
    /**
     * Envia notificaciones a los usuarios que reciben alertas cuando ingresa un
     * nuevo lote de suministro.
     *
     * @param IdSuministro
     * @param NumeroLoteSuministro
     */
    public void EnviarNotificacionCambioLote(int IdSuministro, String NumeroLoteSuministro) {
        final String asunto = "Control de Suministros: Cambio Lote";
        final String mensaje = GetMensajeCambioDeLote(IdSuministro, NumeroLoteSuministro);
        List<Operario> operarios = cOps.ListarOperarios();
        operarios.stream()
                .filter(operario -> operario.isRecibeAlertas() && !operario.getCorreoOperario().isEmpty())
                .forEach(operario -> {
                    mail.enviarMail(operario.getCorreoOperario(), mensaje, asunto);
                });
    }
    
    /**
     * Genera el mensaje en html con la información del suministro y el numero
     * de lote.
     *
     * @param IdSuministro
     * @param NumeroLoteSuministro
     * @return
     */
    private String GetMensajeCambioDeLote(int IdSuministro, String NumeroLoteSuministro) {
        Suministro suministro = buffer.getEntidad(IdSuministro);
        String mensaje = "<p style='font-family: sans-serif;'><h1 style='color: blue;'> Control Suministros </h1><br></br>";
        mensaje += "<h3>Ingreso de Nuevo Lote: ";
        mensaje += suministro.getNombreSuministro();
        mensaje += " (" + suministro.getProveedorSuministro().getNombreProveedor() + ")";
        mensaje += "</h3><br></br>";
        mensaje += "Lote: " + NumeroLoteSuministro;
        return mensaje;
    }
    
    /**
     * STOCK MINIMO
     */
    /**
     * Registrar Stock Minimo de Suministro
     *
     * @param CantidadStockMinimo cantidad de suministro
     * @param FechaVigenteStockMinimo fecha de entrada en vigencia
     * @param IdSuministro id del suministro
     * @return
     */
    public int RegistrarStockMinimoSuministro(float CantidadStockMinimo, Date FechaVigenteStockMinimo, int IdSuministro) {
        StockMinimo stockMinimo = cStock.CrearStockMinimo(CantidadStockMinimo, FechaVigenteStockMinimo);
        Suministro suministro = mSuministro.ObtenerSuministro(IdSuministro);
        suministro.addStockMinimoSuministro(stockMinimo);
        int id = mSuministro.ActualizarSuministro(suministro);
        if(id>0){
            buffer.updateEntidad(suministro, id);
        }
        return id;
    }
    
    /**
     * Calcula la cantidad de suministros que están por debajo de su stock
     * minimo. Solo se toman en cuenta aquellos suministros que tengan un stock
     * minimo definido (cantidad mayor a 0) y esten vigentes.
     *
     * @return Retorna array[0] = total de suministros y array[1]= total de
     * suministros debajo de stock minimo
     */
    public int[] GetTotalSuministrosDebajoStockMinimo() {
        List<Suministro> suministros = buffer.getListaEntidades().stream()
                .filter((Suministro s)->s.isVigente())
                .collect(Collectors.toList());
        
        int cantidad = suministros.size();
        return new int[]{cantidad, suministros.stream()
                .filter((Suministro s)->s.getStock()<s.getStockMinimoSuministro().getCantidadStockMinimo() &&
                        s.getStockMinimoSuministro().getCantidadStockMinimo()>0)
                .collect(Collectors.toList())
                .size()};
    }
    
    
    /**
     * Devuelve los suministros que están por debajo de su stock minimo. Solo se
     * toman en cuenta aquellos suministros que tengan un stock minimo definido
     * (cantidad mayor a 0) y esten vigentes.
     *
     * @return Retorna una lista con los ids de suministros debajo de stock
     * minimo, retorna una lista vacia si no los hay.
     */
    public List<Integer> GetIdsSuministrosDebajoStockMinimo() {
        List<Integer> lista = new ArrayList<>();
        buffer.getListaEntidades().stream()
                .filter((Suministro s)->s.isVigente())
                .filter((Suministro s)->s.getStock()<s.getStockMinimoSuministro().getCantidadStockMinimo() &&
                        s.getStockMinimoSuministro().getCantidadStockMinimo()>0)
                .forEach((Suministro s)->{
                    try {
                        lista.add(s.getIdSuministro());
                    } catch (NullPointerException ex) {
                        System.out.println("Error: " + ex.getMessage() + " en " + s.getNombreSuministro() + s.getIdSuministro());
                    }
                });
        return lista;
    }
    
    
    /**
     * Devuelve los suministros con lotes vencidos y que estén vigentes.
     *
     * @param ConStock <b>True:</b> para obtener solo los que tengan stock.
     * <b>False:</b> para obtener todos.
     * @return Retorna una lista con los ids de los suministros.
     */
    public List<Integer> getIdsSuministrosConLotesVencidos(boolean ConStock) {
        List<Integer> lista = new ArrayList<>();
        if (ConStock) {
            buffer.getListaEntidades().stream()
                    .filter((Suministro s)->s.isVigente() && s.getLotesVencidosEnStock().size()>0)
                    .forEach((Suministro s)->{
                        lista.add(s.getIdSuministro());
                    });
        }else{
            buffer.getListaEntidades().stream()
                    .filter((Suministro s)->s.isVigente() && s.getLotesVencidos().size()>0)
                    .forEach((Suministro s)->{
                        lista.add(s.getIdSuministro());
                    });
        }
        return lista;
    }
    
    /**
     * Devuelve los suministros con lotes vencidos y que estén vigentes.
     *
     * @param ConStock <b>True:</b> para obtener solo los que tengan stock.
     * <b>False:</b> para obtener todos.
     * @return Retorna una lista con los suministros. Retorna una lista vacia si
     * no existen lotes vencidos en stock
     */
    public List<Suministro> getSuministrosConLotesVencidos(boolean ConStock) {
        if (ConStock) {
            return buffer.getListaEntidades().stream()
                    .filter((Suministro s)->s.isVigente() && s.getLotesVencidosEnStock().size()>0)
                    .collect(Collectors.toList());
        }
        return buffer.getListaEntidades().stream()
                .filter((Suministro s)->s.isVigente() && s.getLotesVencidos().size()>0)
                .collect(Collectors.toList());
    }
    
    /**
     * Devuelve los suministros con lotes a un mes de su vencimiento, que esten
     * vigentes y con stock,
     *
     * @return Lista de Suminstros.
     */
    public List<Suministro> getSuministrosUnMesVigencia() {
        return buffer.getListaEntidades().stream()
                .filter(s->s.isVigente() && s.getLotesUnMesVigenciaEnStock().size()>0)
                .collect(Collectors.toList());
    }
    
    /**
     * Actualiza un suministro en la base de datos. Compara unidad y proveedor y
     * actualiza si es necesario. Compara el stockminimo y si es diferente se
     * crea con la fecha actual del sistema como vigencia.
     *
     * @param suministro objeto con la informacion basica del suministro
     * (nombre, id, codigoSAP, Descripcion)
     * @param IdUnidad id de unidad de medida
     * @param IdProveedor id de proveedor
     * @param StockMinimoSuministro cantidad de stock minimo.
     * @return Retorna el id de suministro actualizado. Si no se creo retorna
     * -1.
     */
    public int ActualizarSuministro(Suministro suministro, int IdProveedor, int IdUnidad, float StockMinimoSuministro) {
        Suministro sumBD;
        int id = -1;
        sumBD = mSuministro.ObtenerSuministro(suministro.getIdSuministro());
        try {
            sumBD.setCodigoSAPSuministro(suministro.getCodigoSAPSuministro());
            sumBD.setDescripcionSuministro(suministro.getDescripcionSuministro());
            sumBD.setNombreSuministro(suministro.getNombreSuministro());
            sumBD.setVigente(suministro.isVigente());
            sumBD.setAvisoCambioLote(suministro.isAvisoCambioLote());
            if (sumBD.getUnidadSuministro().getIdUnidad() != IdUnidad) {
                sumBD.setUnidadSuministro(cUnidad.BuscarUnidad(IdUnidad));
            }
            if (sumBD.getProveedorSuministro().getIdProveedor() != IdProveedor) {
                sumBD.setProveedorSuministro(cProveedor.BuscarProveedor(IdProveedor));
            }
            if (sumBD.getStockMinimoSuministro().getCantidadStockMinimo() != StockMinimoSuministro) {
                StockMinimo stock = cStock.CrearStockMinimo(StockMinimoSuministro, Calendar.getInstance().getTime());
                sumBD.addStockMinimoSuministro(stock);
            }
            
            id = mSuministro.ActualizarSuministro(sumBD);
            if(id>0){
                buffer.updateEntidad(sumBD, id);
            }
        } catch (NullPointerException ex) {
            System.out.println("Error al actualizar suministro: " + ex.getMessage());
        }
        return id;
    }
}
