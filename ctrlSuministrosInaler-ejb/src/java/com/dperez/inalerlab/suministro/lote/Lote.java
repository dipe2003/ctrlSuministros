package com.dperez.inalerlab.suministro.lote;


import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Lote implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdLote;
    @Temporal(TemporalType.DATE)
    private Date ProduccionLote;
    @Temporal(TemporalType.DATE)
    private Date VencimientoLote;
    @Column(unique = true)
    private String NumeroLote;
    @ManyToOne
    private Suministro SuministroLote;
    @OneToMany(mappedBy = "LoteIngreso")
    private List<Ingreso> IngresosLote;
    @OneToMany(mappedBy = "LoteSalida")
    private List<Salida> SalidasLote;
    
    //	Constructores
    public Lote(){}
    public Lote(Date ProduccionLote, Date VencimientoLote, String NumeroLote){
        this.ProduccionLote = ProduccionLote;
        this.VencimientoLote = VencimientoLote;
        this.NumeroLote = NumeroLote;
    }
    
    //	Getters
    public int getIdLote(){return this.IdLote;}
    public Date getProduccionLote() {return ProduccionLote;}    
    public Date getVencimientoLote(){return this.VencimientoLote;}
    public Suministro getSuministroLote(){return this.SuministroLote;}
    public List<Ingreso> getIngresosLote(){return this.IngresosLote;}
    public List<Salida> getSalidasLote(){return this.SalidasLote;}
    public String getNumeroLote() {return NumeroLote;}
    
    //	Setters
    public void setIdLote(int IdLote){this.IdLote = IdLote;}
    public void setProduccionLote(Date ProduccionLote) {this.ProduccionLote = ProduccionLote;}    
    public void setVencimientoLote(Date VencimientoLote){this.VencimientoLote = VencimientoLote;}
    public void setSuministroLote(Suministro SuministroLote){
        this.SuministroLote = SuministroLote;
        if(!SuministroLote.getLotesSuministros().contains(this)){
            SuministroLote.getLotesSuministros().add(this);
        }
    }
    public void setIngresosLote(List<Ingreso> IngresosLote){this.IngresosLote = IngresosLote;}
    public void setNumeroLote(String NumeroLote) {this.NumeroLote = NumeroLote;}
    public void setSalidasLote(List<Salida> SalidasLote) {this.SalidasLote = SalidasLote;}    
    
    //	Ingresos - Salidas
    
    /**
     *	Devuelve la cantidad ingresada del Suministro con el lote.
     */
    public float getCantidadIngresosLote(){
        float stock = 0f;
        for(int i = 0; i < this.IngresosLote.size(); i++){
            stock += this.IngresosLote.get(i).getCantidadIngreso();
        }
        return stock;
    }
    
    /**
     *	Devuelve la cantidad egresada del Suministro con el lote.
     */
    public float getCantidadSalidasLote(){
        float stock = 0f;
        for(int i = 0; i < this.SalidasLote.size(); i++){
            stock += this.SalidasLote.get(i).getCantidadSalida();
        }
        return stock;
    }
    
    /**
     *	Devuelve la cantidad en stock del Suministro con el lote.
     */
    public float getCantidadStock(){
        return this.getCantidadIngresosLote() - this.getCantidadSalidasLote();
    }
    
    public void addIngresoLote(Ingreso IngresoLote){
        this.IngresosLote.add(IngresoLote);
        if (!IngresoLote.getLoteIngreso().equals(this)) {
            IngresoLote.setLoteIngreso(this);
        }
    }
    
    public void addSalidaLote(Salida SalidaLote){
        this.SalidasLote.add(SalidaLote);
        if (!SalidaLote.getLoteSalida().equals(this)) {
            SalidaLote.setLoteSalida(this);
        }
    }
}