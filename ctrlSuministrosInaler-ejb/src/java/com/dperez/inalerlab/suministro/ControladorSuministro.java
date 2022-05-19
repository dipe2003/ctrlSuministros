package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.buffer.BufferGenerico;
import com.dperez.inalerlab.buffer.FabricaBuffer;
import com.dperez.inalerlab.email.SendMail;
import com.dperez.inalerlab.operario.ControladorOperario;
import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.proveedor.ControladorProveedor;
import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import com.dperez.inalerlab.suministro.lote.Lote;
import com.dperez.inalerlab.suministro.lote.Salida;
import com.dperez.inalerlab.suministro.unidad.ControladorUnidad;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.Serializable;
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
    private ControladorOperario cOps;
    @Inject
    private SendMail mail;
    @Inject
    private FabricaBuffer fBuffer;
    
    private BufferGenerico<Suministro> buffer;
    
    private BufferGenerico<Proveedor> bufferProveedores;
    
    @PostConstruct
    public void init(){
        buffer = fBuffer.getBufferSuministro(mSuministro.ListarSuministros());
        bufferProveedores = fBuffer.getBufferProveedor(cProveedor.ListarProveedores());
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
        Proveedor proveedor = cProveedor.BuscarProveedor(IdProveedorSuministro);
        Unidad unidad = cUnidad.BuscarUnidad(IdUnidadSuministro);
        
        Suministro suministro = proveedor.CrearSuministro(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, unidad, TipoSuministro, AvisoCambioLote);
        int id = mSuministro.CrearSuministro(suministro);
        if(id>0){
            buffer.putEntidad(suministro, id);
            bufferProveedores.putEntidad(proveedor, proveedor.getIdProveedor());
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
        String mensaje = "<p style='font-family: sans-serif;'><h1 style='color: blue;'> Control Suministros </h1><br></br>"
                + "<h3>Ingreso de Nuevo Lote: "
                + suministro.getNombreSuministro()
                + " (" + suministro.getProveedorSuministro().getNombreProveedor() + ")"
                + "</h3><br></br>"
                + "Lote: " + NumeroLoteSuministro;
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
        Suministro suministro = mSuministro.ObtenerSuministro(IdSuministro);
        suministro.CrearStockMinimo(CantidadStockMinimo, FechaVigenteStockMinimo);
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
        return buffer.getListaEntidades().stream()
                .filter((Suministro s)->s.isVigente())
                .filter((Suministro s)->s.getStock()<s.getStockMinimoSuministro().getCantidadStockMinimo() &&
                        s.getStockMinimoSuministro().getCantidadStockMinimo()>0)
                .mapToInt(Suministro::getIdSuministro)
                .boxed()
                .collect(Collectors.toList());
    }
    
    
    /**
     * Devuelve los suministros con lotes vencidos y que estén vigentes.
     *
     * @param ConStock <b>True:</b> para obtener solo los que tengan stock.
     * <b>False:</b> para obtener todos.
     * @return Retorna una lista con los ids de los suministros.
     */
    public List<Integer> getIdsSuministrosConLotesVencidos(boolean ConStock) {
        if (ConStock) {
            return buffer.getListaEntidades().stream()
                    .filter((Suministro s)->s.isVigente() && s.getLotesVencidosEnStock().size()>0)
                    .mapToInt(Suministro::getIdSuministro)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return buffer.getListaEntidades().stream()
                .filter((Suministro s)->s.isVigente() && s.getLotesVencidos().size()>0)
                .mapToInt(Suministro::getIdSuministro)
                .boxed()
                .collect(Collectors.toList());
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
                sumBD.CrearStockMinimo(StockMinimoSuministro, Calendar.getInstance().getTime());
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
    
    /*
    Lote
    */
    
    /**
     * Crea un lote en la base de datos.
     * @param VencimientoLote
     * @param NumeroLote
     * @param IdSuministro
     * @return Retorna el id del lote creado. Retorna -1 si no se creo.
     */
    public int AgregarLote(Date VencimientoLote, String NumeroLote, int IdSuministro){
        Suministro suministro = mSuministro.ObtenerSuministro(IdSuministro);
        Lote lote = suministro.CrearLote(VencimientoLote, NumeroLote);
        mSuministro.ActualizarSuministro(suministro);
        if(suministro.ExisteNumeroLote(NumeroLote)){
            lote.setIdLote(suministro.getLotesSuministros().stream()
                    .filter(l->l.getNumeroLote().equalsIgnoreCase(NumeroLote))
                    .findFirst()
                    .get()
                    .getIdLote());
        }
        if(lote.getIdLote()>0){
            buffer.updateEntidad(suministro, suministro.getIdSuministro());
        }
        return lote.getIdLote();
    }
    /**
     * Crea un ingreso en la base de datos.
     * @param FechaIngreso
     * @param CantidadIngreso
     * @param NumeroFactura
     * @param IdLoteIngreso
     * @param IdOperarioIngreso
     * @param ObservacionesIngreso
     * @param IdSuministro
     * @return Retorna el id del ingreso creada. Retorna -1 si no se crea.
     */
    public int CrearIngreso(Date FechaIngreso, float CantidadIngreso, String NumeroFactura, int IdLoteIngreso, int IdOperarioIngreso, String ObservacionesIngreso, int IdSuministro){
        int id = -1;
        try{
            Operario operario = cOps.BuscarOperario(IdOperarioIngreso);
            Suministro suministro = mSuministro.ObtenerSuministro(IdSuministro);
            if(suministro.FindLote(IdLoteIngreso)!=null){
                suministro.FindLote(IdLoteIngreso).CrearIngreso(FechaIngreso, CantidadIngreso, NumeroFactura, ObservacionesIngreso, operario);
                mSuministro.ActualizarSuministro(suministro);
                Ingreso ingreso = suministro.FindLote(IdLoteIngreso).getUltimoIngreso();
                id = ingreso.getIdIngreso();
                if(id>0){
                    buffer.updateEntidad(suministro, suministro.getIdSuministro());
                }
            }
        }catch(NullPointerException ex){}
        return id;
    }
    /*
    * Crea un salida en la base de datos.
    * @param FechaSalida
    * @param CantidadSalida
    * @param IdLoteSalida
    * @param IdOperarioSalida
    * @param ObservacionesSalida
    * @return Retorna el id de la salida creada. Retorna -1 si no se crea.
    */
    public int CrearSalida(int IdSuministro, Date FechaSalida, float CantidadSalida, int IdLoteSalida, int IdOperarioSalida, String ObservacionesSalida){
        int id = -1;
        try{
            Operario operario = cOps.BuscarOperario(IdOperarioSalida);
            Suministro suministro = mSuministro.ObtenerSuministro(IdSuministro);
            if(suministro.FindLote(IdLoteSalida)!=null){
                suministro.FindLote(IdLoteSalida).CrearSalida(FechaSalida, CantidadSalida, ObservacionesSalida, operario);
                mSuministro.ActualizarSuministro(suministro);
                Salida salida = suministro.FindLote(IdLoteSalida).getUltimaSalida();
                id = salida.getIdSalida();
                if(id>0){
                    buffer.updateEntidad(suministro, suministro.getIdSuministro());
                }
            }
        }catch(NullPointerException ex){}
        return id;
    }
    
    /**
     * Actualiza los datos del ingreso especificado y del lote relacionado.
     * @param idSuministro
     * @param IdLote
     * @param IdIngreso
     * @param NumeroLote
     * @param CantidadIngreso
     * @param FechaVencimientoLote
     * @param NumeroFactura
     * @return -1 si no se actualizo. IdLote si se actualizo.
     */
    public int ActualizarLoteIngreso(int idSuministro, int IdLote, int IdIngreso, String NumeroLote, float CantidadIngreso,
            Date FechaVencimientoLote, String NumeroFactura){
        Suministro suministro = mSuministro.ObtenerSuministro(idSuministro);
        Lote lot = suministro.FindLote(IdLote);
        try{
            if(lot != null){
                if(!lot.getNumeroLote().equalsIgnoreCase(NumeroLote))
                    lot.setNumeroLote(NumeroLote);
                
                if(lot.getVencimientoLote()!=null){
                    if(lot.getVencimientoLote().compareTo(FechaVencimientoLote)!=0){
                        lot.setVencimientoLote(FechaVencimientoLote);
                    }
                }else{
                    lot.setVencimientoLote(FechaVencimientoLote);
                }
                Ingreso ingreso = lot.FindIngreso(IdIngreso);
                if(ingreso.getCantidadIngreso() != CantidadIngreso)
                    ingreso.setCantidadIngreso(CantidadIngreso);
                
                if(!ingreso.getNumeroFactura().equalsIgnoreCase(NumeroFactura))
                    ingreso.setNumeroFactura(NumeroFactura);
            }
            buffer.updateEntidad(suministro, suministro.getIdSuministro());
            return mSuministro.ActualizarSuministro(suministro);
        }catch(Exception ex){}
        return -1;
    }
    
    /**
     * Actualiza los datos de la salida especificada y del lote relacionado. 
     * @param idSuministro
     * @param IdLote
     * @param IdSalida
     * @param CantidadSalida
     * @param FechaSalida
     * @return -1 si no se actualizo. IdLote si se actualizo.
     */
    public int ActualizarLoteSalida(int idSuministro, int IdLote, int IdSalida, float CantidadSalida, Date FechaSalida){
        Suministro suministro = mSuministro.ObtenerSuministro(idSuministro);
        Lote lot = suministro.FindLote(IdLote);
        try{
            if(lot != null){
                Salida salida = lot.FindSalida(IdSalida);
                
                if(salida.getFechaSalida().compareTo(FechaSalida)!=0){
                    salida.setFechaSalida(FechaSalida);
                }
                if(salida.getCantidadSalida() != CantidadSalida){
                    salida.setCantidadSalida(CantidadSalida);
                }
            }
            buffer.updateEntidad(suministro, suministro.getIdSuministro());
            return mSuministro.ActualizarSuministro(suministro);
        }catch(Exception ex){}
        return -1;
    }
}
