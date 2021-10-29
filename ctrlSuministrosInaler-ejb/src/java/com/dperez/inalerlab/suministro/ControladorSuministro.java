package com.dperez.inalerlab.suministro;

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
    private BufferSuministros buffer;
    @Inject
    private ControladorOperario cOps;
    @Inject
    private SendMail mail;
    
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
        if (id != -1) {
            buffer.putSuministro(suministro);
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
        if (buffer.containsSuministro(IdSuministro)) {
            return buffer.getSuministro(IdSuministro);
        }
        return mSuministro.ObtenerSuministro(IdSuministro);
    }
    
    /**
     * Devuelve todos los suministros registrados en la base de datos según su
     * vigencia. Si no hay suministros devuelve una lista vacia.
     *
     * @param Vigente True: indica si solo se devuelven los suministros en uso,
     * de lo contrario False.
     * @param UsarBuffer True: indica si se utiliza el buffer (False para
     * solucionar error de contexto en Timer singleton).
     * @return
     */
    public List<Suministro> ListarSuministros(boolean Vigente, boolean UsarBuffer) {
        if (UsarBuffer) {
            if (buffer.bufferSize() > 0) {
                return buffer.getListaSuministros(Vigente);
            }
        }
        if(Vigente){
            return mSuministro.ListarSuministros(true);
        }
        return mSuministro.ListarSuministros();
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
        Suministro suministro = buffer.containsSuministro(IdSuministro) ? buffer.getSuministro(IdSuministro) : mSuministro.ObtenerSuministro(IdSuministro);
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
        if (id != -1) {
            buffer.updateSuministro(suministro);
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
        List<Suministro> suministros = buffer.bufferSize() > 0 ? buffer.getListaSuministros(true) : mSuministro.ListarSuministros(true);
        int cantidad = 0;
        for (Suministro suministro : suministros) {
            if (suministro.getStock() < suministro.getStockMinimoSuministro().getCantidadStockMinimo()
                    && suministro.getStockMinimoSuministro().getCantidadStockMinimo() > 0) {
                cantidad++;
            }
        }
        return new int[]{suministros.size(), cantidad};
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
        List<Suministro> suministros = buffer.bufferSize() > 0 ? buffer.getListaSuministros(true) : mSuministro.ListarSuministros(true);
        List<Integer> lista = new ArrayList<>();
        for (Suministro suministro : suministros) {
            try {
                if (suministro.getStock() < suministro.getStockMinimoSuministro().getCantidadStockMinimo()
                        && suministro.getStockMinimoSuministro().getCantidadStockMinimo() > 0) {
                    lista.add(suministro.getIdSuministro());
                }
            } catch (NullPointerException ex) {
                System.out.println("Error: " + ex.getMessage() + " en " + suministro.getNombreSuministro() + suministro.getIdSuministro());
            }
        }
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
        List<Suministro> suministros = buffer.bufferSize() > 0 ? buffer.getListaSuministros(true) : mSuministro.ListarSuministros(true);
        List<Integer> lista = new ArrayList<>();
        if (ConStock) {
            suministros.forEach(s->{
                if(s.getLotesVencidosEnStock().size()>0)
                    lista.add(s.getIdSuministro());
            });
        }else{
            suministros.forEach(s->{
                if(s.getLotesVencidos().size()>0)
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
        List<Suministro> suministros = buffer.bufferSize() > 0 ? buffer.getListaSuministros(true) : mSuministro.ListarSuministros(true);
        
        if (ConStock) {
            return suministros.stream()
                    .filter(s->s.getLotesVencidosEnStock().size()>0)
                    .collect(Collectors.toList());
        }
        return suministros.stream()
                .filter(s->s.getLotesVencidos().size()>0)
                .collect(Collectors.toList());
    }
    
    /**
     * Devuelve los suministros con lotes a un mes de su vencimiento, que esten
     * vigentes y con stock,
     *
     * @return Lista de Suminstros.
     */
    public List<Suministro> getSuministrosUnMesVigencia() {
        List<Suministro> suministros = buffer.bufferSize() > 0 ? buffer.getListaSuministros(true) : mSuministro.ListarSuministros(true);
        return suministros.stream()
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
        if (buffer.containsSuministro(suministro.getIdSuministro())) {
            sumBD = buffer.getSuministro(suministro.getIdSuministro());
        } else {
            sumBD = mSuministro.ObtenerSuministro(suministro.getIdSuministro());
        }
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
            if (id != -1) {
                buffer.updateSuministro(sumBD);
            }
        } catch (NullPointerException ex) {
        }
        return id;
    }
    
    /**
     * Actualiza el buffer de suministros con el suministro especificado.
     *
     * @param IdSuministro
     */
    public void ActualizarSuministroBuffer(int IdSuministro) {
        buffer.updateSuministro(mSuministro.ObtenerSuministro(IdSuministro));
    }
}
