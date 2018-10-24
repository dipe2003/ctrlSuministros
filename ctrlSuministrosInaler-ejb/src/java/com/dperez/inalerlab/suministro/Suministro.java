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
    @OneToMany(mappedBy = "SuministroLote")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Lote> LotesSuministros;
    @OneToMany(cascade = CascadeType.MERGE)
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
    public void addStockMinimoSuministro(StockMinimo StockMinimoSuministro){
        this.StocksMinimosSuministro.forEach(stock -> {
            stock.setVigente(false);
        });
        this.StocksMinimosSuministro.add(StockMinimoSuministro);
    }
    public StockMinimo getStockMinimoSuministro(){
        for(int i = 0; i < StocksMinimosSuministro.size(); i++){
            if(StocksMinimosSuministro.get(i).getVigenciaStockMinimo()){
                return StocksMinimosSuministro.get(i);
            }
        }
        return null;
    }
    
    //	Lotes
    public void addLote(Lote LoteSuministro){
        this.LotesSuministros.add(LoteSuministro);
        if(LoteSuministro.getSuministroLote()==null || !LoteSuministro.getSuministroLote().equals(this)){
            LoteSuministro.setSuministroLote(this);
        }
    }
    
    public float getStock(){
        float stock = 0f;
        for(Lote lote: this.LotesSuministros){
            stock += lote.getCantidadStock();
        }
        return stock;
    }
    
    public float getTotalIngreso(){
        float total =0f;
        for(Lote lote: this.LotesSuministros){
            total += lote.getCantidadIngresosLote();
        }
        return total;
    }
    
    public float getTotalSalida(){
        float total =0f;
        for(Lote lote: this.LotesSuministros){
            total += lote.getCantidadSalidasLote();
        }
        return total;
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
        List<Lote> lotes = new ArrayList<>();
        Date hoy = Calendar.getInstance().getTime();
        for(Lote lote: this.LotesSuministros){
            try{
                if(lote.getVencimientoLote().before(hoy)) lotes.add(lote);
            }catch(NullPointerException ex){}
        }
        return lotes;
    }
    /**
     * Devuelve los lotes que estan a un mes de su vencimiento.
     * No se tiene en cuenta si existe en stock o se dio de baja.
     * @return
     */
    public List<Lote> getLotesUnMesVigencia(){
        List<Lote> lotes = new ArrayList<>();
        Calendar UnMes = Calendar.getInstance();
        UnMes.add(Calendar.MONTH, 1);
        Date UnMesVigencia = UnMes.getTime();
        Date Hoy = Calendar.getInstance().getTime();
        for(Lote lote: this.LotesSuministros){
            try{
                if(lote.getVencimientoLote().before(UnMesVigencia) && lote.getVencimientoLote().after(Hoy)) lotes.add(lote);
            }catch(NullPointerException ex){}
        }
        return lotes;
    }
    /**
     * Devuelve los lotes vencidos.
     * Solo se devuelven los que existan en stock.
     * @return
     */
    public List<Lote> getLotesVencidosEnStock(){
        List<Lote> lotes = new ArrayList<>();
        Date hoy = Calendar.getInstance().getTime();
        for(Lote lote: this.LotesSuministros){
            try{
                if(lote.getVencimientoLote().before(hoy) && lote.getCantidadStock()>0) lotes.add(lote);
            }catch(NullPointerException ex){}
        }
        return lotes;
    }
    /**
     * Devuelve los lotes que estan a un mes de su vencimiento.
     * Solo se devuelven los que existan en stock.
     * @return
     */
    public List<Lote> getLotesUnMesVigenciaEnStock(){
        List<Lote> lotes = new ArrayList<>();
        Calendar UnMes = Calendar.getInstance();
        UnMes.add(Calendar.MONTH, 1);
        Date UnMesVigencia = UnMes.getTime();
        Date Hoy = Calendar.getInstance().getTime();
        for(Lote lote: this.LotesSuministros){
            try{
                if((lote.getVencimientoLote().before(UnMesVigencia) && lote.getVencimientoLote().after(Hoy))  && lote.getCantidadStock()>0) lotes.add(lote);
            }catch(NullPointerException ex){}
        }
        return lotes;
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