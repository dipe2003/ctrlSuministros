package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.suministro.stockminimo.StockMinimo;
import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.lote.Ingreso;
import com.dperez.inalerlab.suministro.lote.Lote;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
abstract public class Suministro implements Serializable, Comparable<Suministro>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int IdSuministro;
    private String NombreSuministro;
    private String DescripcionSuministro;
    private String CodigoSAPSuministro;
    @ManyToOne
    private Unidad UnidadSuministro;
    @OneToMany(mappedBy = "SuministroLote", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Lote> LotesSuministros;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<StockMinimo> StocksMinimosSuministro;
    @ManyToOne
    private Proveedor ProveedorSuministro;
    private boolean Vigente;
    private boolean AvisoCambioLote;
    
    //	Constructores
    public Suministro(){
        this.StocksMinimosSuministro = new ArrayList<>();
        this.LotesSuministros = new ArrayList<>();
        this.Vigente = true;
        this.AvisoCambioLote = false;
    }
    public Suministro(String NombreSuministro, String DescripcionSuministro, String CodigoSAPSuministro,
            Unidad UnidadSuministro, Proveedor ProveedorSuministro, boolean AvisoCambioLote){
        this.NombreSuministro = NombreSuministro;
        this.DescripcionSuministro = DescripcionSuministro;
        this.CodigoSAPSuministro = CodigoSAPSuministro;
        this.UnidadSuministro = UnidadSuministro;
        this.StocksMinimosSuministro = new ArrayList<>();
        this.LotesSuministros = new ArrayList<>();
        this.ProveedorSuministro = ProveedorSuministro;
        this.Vigente = true;
        this.AvisoCambioLote = AvisoCambioLote;
    }
    
    //	Getters
    public int getIdSuministro() {return IdSuministro;}
    public String getNombreSuministro() {return NombreSuministro;}
    public String getDescripcionSuministro() {return DescripcionSuministro;}
    public String getCodigoSAPSuministro() {return CodigoSAPSuministro;}
    public Unidad getUnidadSuministro() {return UnidadSuministro;}
    public List<Lote> getLotesSuministros() {return LotesSuministros;}
    public List<StockMinimo> getStocksMinimosSuministro() {return StocksMinimosSuministro;}
    public Proveedor getProveedorSuministro() {return ProveedorSuministro;}
    public boolean isVigente() {return Vigente;}
    public boolean isAvisoCambioLote() {return AvisoCambioLote;}
    
    //	Setters
    public void setIdSuministro(int IdSuministro) {this.IdSuministro = IdSuministro;}
    public void setNombreSuministro(String NombreSuministro) {this.NombreSuministro = NombreSuministro;}
    public void setDescripcionSuministro(String DescripcionSuministro) {this.DescripcionSuministro = DescripcionSuministro;}
    public void setCodigoSAPSuministro(String CodigoSAPSuministro) {this.CodigoSAPSuministro = CodigoSAPSuministro;}
    public void setUnidadSuministro(Unidad UnidadSuministro) {this.UnidadSuministro = UnidadSuministro;}
    public void setLotesSuministros(List<Lote> LotesSuministros) {this.LotesSuministros = LotesSuministros;}
    public void setStocksMinimosSuministro(List<StockMinimo> StocksMinimosSuministro) {this.StocksMinimosSuministro = StocksMinimosSuministro;}
    public void setProveedorSuministro(Proveedor ProveedorSuministro) {this.ProveedorSuministro = ProveedorSuministro;}
    public void setVigente(boolean Vigente) {this.Vigente = Vigente;}
    public void setAvisoCambioLote(boolean AvisoCambioLote) {this.AvisoCambioLote = AvisoCambioLote;}
    
    //	StocksMinimos
    public StockMinimo CrearStockMinimo(float cantidad, Date fechaVigente){
        StockMinimo stock = new StockMinimo(cantidad, fechaVigente, true);
        DarDeBajaTodosLosStocks();
        this.StocksMinimosSuministro.add(stock);
        return stock;
    }

    public StockMinimo getStockMinimoSuministro(){
        return StocksMinimosSuministro.stream()
                .filter(stock->stock.getVigenciaStockMinimo() == true)
                .findFirst()
                .orElse(null);
    }
    
    private void DarDeBajaTodosLosStocks(){
        StocksMinimosSuministro.stream()
                .filter((StockMinimo s)->s.getVigenciaStockMinimo() == true)
                .forEach((StockMinimo s)->s.setVigente(false));
    }
    
    //	Lotes
    public Lote CrearLote(Date vencimiento, String numeroLote){
        Lote lote = new Lote(vencimiento, numeroLote, this);
        this.LotesSuministros.add(lote);
        return lote;
    }
    
    public Lote FindLote(int idLote){
        return this.LotesSuministros.stream()
                .filter(l->l.getIdLote() == idLote)
                .findFirst()
                .orElse(null);
    }
    
    public float getStock(){
        return (float) LotesSuministros.stream()
                .mapToDouble((Lote l)->l.getCantidadStock())
                .sum();
    }
    
    public float getTotalIngreso(){
        return (float) LotesSuministros.stream()
                .mapToDouble((Lote l)->l.getCantidadIngresosLote())
                .sum();
    }
    
    public float getTotalSalida(){
        return (float) LotesSuministros.stream()
                .mapToDouble((Lote l)->l.getCantidadSalidasLote())
                .sum();
    }
    
    public boolean isDebajoStockMinimo(){
        return getStockMinimoSuministro().getCantidadStockMinimo() > getStock();
    }
    
    /**
     * Devuelve los lotes vencidos.
     * No se tiene en cuenta si existe en stock o se dio de baja.
     * @return
     */
    public List<Lote> getLotesVencidos(){
        return LotesSuministros.stream()
                .filter((Lote l)->l.EstaVencido())
                .collect(Collectors.toList());
    }
    /**
     * Devuelve los lotes que estan a un mes de su vencimiento.
     * No se tiene en cuenta si existe en stock o se dio de baja.
     * @return
     */
    public List<Lote> getLotesUnMesVigencia(){
        Calendar UnMes = Calendar.getInstance();
        UnMes.add(Calendar.MONTH, 1);
        Date UnMesVigencia = UnMes.getTime();
        Date Hoy = Calendar.getInstance().getTime();
        return LotesSuministros.stream()
                .filter((Lote l)->l.getVencimientoLote()!=null && l.getVencimientoLote().before(UnMesVigencia) &&
                        l.getVencimientoLote().after(Hoy))
                .collect(Collectors.toList());
    }
    /**
     * Devuelve los lotes vencidos.
     * Solo se devuelven los que existan en stock.
     * @return
     */
    public List<Lote> getLotesVencidosEnStock(){
        return LotesSuministros.stream()
                .filter((Lote l)->l.EstaVencido() && l.getCantidadStock()>0)
                .collect(Collectors.toList());
    }
    /**
     * Devuelve los lotes que estan a un mes de su vencimiento.
     * Solo se devuelven los que existan en stock.
     * @return
     */
    public List<Lote> getLotesUnMesVigenciaEnStock(){
        Calendar UnMes = Calendar.getInstance();
        UnMes.add(Calendar.MONTH, 1);
        Date UnMesVigencia = UnMes.getTime();
        Date Hoy = Calendar.getInstance().getTime();
        return LotesSuministros.stream()
                .filter((Lote l)-> l.getVencimientoLote()!=null && l.getVencimientoLote().before(UnMesVigencia) && 
                        l.getVencimientoLote().after(Hoy) && l.getCantidadStock() >0)
                .collect(Collectors.toList());
    }
    
    /**
     * Retorna el ultimo ingreso del suministro.
     * @return Retorna null si no hay ingresos.
     */
    public Ingreso getUltimoIngreso(){
        int index = 0;
        if(!LotesSuministros.isEmpty()){
            for (int i = 0; i < this.LotesSuministros.size(); i++) {
                Ingreso in = LotesSuministros.get(i).getUltimoIngreso();
                if(in!=null){
                    Date max = in.getFechaIngreso();
                    if(this.LotesSuministros.get(i).getUltimoIngreso().getFechaIngreso().after(max)){
                        max = this.LotesSuministros.get(i).getUltimoIngreso().getFechaIngreso();
                        index = i;
                    }
                }
            }
            return this.LotesSuministros.get(index).getUltimoIngreso();
        }
        return null;
    }
    
    /***
     * Devuelve una lista de lotes del suministro con stock.
     * @param incluirVencidos True para incluir todos los lotes, False para retornar solo los que estan vigentes.
     * @return
     */
    public List<Lote> getLotesConStock(boolean incluirVencidos){
        if(incluirVencidos){
            return LotesSuministros.stream()
                    .filter((Lote l)->l.getCantidadStock()>0)
                    .collect(Collectors.toList());
        }
        return LotesSuministros.stream()
                .filter((Lote l)->l.EstaVencido() && l.getCantidadStock()>0)
                .collect(Collectors.toList());
    }    
    
    /**
     * Comprueba la existencia del lote con el numero indicado.
     * @param NumeroLote
     * @return True: Si existe lote. False: Si no existe el lote.
     */
    public boolean ExisteNumeroLote(String NumeroLote){
        return LotesSuministros.stream()
                .anyMatch(lote->lote.getNumeroLote().equalsIgnoreCase(NumeroLote));
    }
    
    @Override
    public int compareTo(Suministro otroSuministro) {
        return this.getNombreSuministro().compareToIgnoreCase(otroSuministro.getNombreSuministro());
    }
    
}