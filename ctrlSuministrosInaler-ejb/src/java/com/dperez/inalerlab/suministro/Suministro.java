package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.suministro.stockminimo.StockMinimo;
import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.lote.Lote;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
abstract public class Suministro implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int IdSuministro;
    private String NombreSuministro;
    private String DescripcionSuministro;
    private String CodigoSAPSuministro;
    @ManyToOne
    private Unidad UnidadSuministro;
    @OneToMany(mappedBy = "SuministroLote")
    private List<Lote> LotesSuministros;
    @OneToMany
    private List<StockMinimo> StocksMinimosSuministro;
    @ManyToOne
    private Proveedor ProveedorSuministro;
    
    //	Constructores
    public Suministro(){}
    public Suministro(String NombreSuministro, String DescripcionSuministro, String CodigoSAPSuministro, 
            Unidad UnidadSuministro, Proveedor ProveedorSuministro){
        this.NombreSuministro = NombreSuministro;
        this.DescripcionSuministro = DescripcionSuministro;
        this.CodigoSAPSuministro = CodigoSAPSuministro;
        this.UnidadSuministro = UnidadSuministro;
        this.StocksMinimosSuministro = new ArrayList<>();
        this.LotesSuministros = new ArrayList<>();
        this.ProveedorSuministro = ProveedorSuministro;
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
    
    //	Setters
    public void setIdSuministro(int IdSuministro) {this.IdSuministro = IdSuministro;}
    public void setNombreSuministro(String NombreSuministro) {this.NombreSuministro = NombreSuministro;}
    public void setDescripcionSuministro(String DescripcionSuministro) {this.DescripcionSuministro = DescripcionSuministro;}
    public void setCodigoSAPSuministro(String CodigoSAPSuministro) {this.CodigoSAPSuministro = CodigoSAPSuministro;}
    public void setUnidadSuministro(Unidad UnidadSuministro) {this.UnidadSuministro = UnidadSuministro;}
    public void setLotesSuministros(List<Lote> LotesSuministros) {this.LotesSuministros = LotesSuministros;}
    public void setStocksMinimosSuministro(List<StockMinimo> StocksMinimosSuministro) {this.StocksMinimosSuministro = StocksMinimosSuministro;}
    public void setProveedorSuministro(Proveedor ProveedorSuministro) {this.ProveedorSuministro = ProveedorSuministro;}    
    
    //	StocksMinimos
    public void addStockMinimoSuministro(StockMinimo StockMinimoSuministro){
        for (StockMinimo stock : this.StocksMinimosSuministro) {
            stock.setVigente(Boolean.FALSE);
        }
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
        if(!LoteSuministro.getSuministroLote().equals(this)){
            LoteSuministro.setSuministroLote(this);
        }
    }
    
}