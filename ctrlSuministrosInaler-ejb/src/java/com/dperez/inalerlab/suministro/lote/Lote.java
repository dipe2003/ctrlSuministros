package com.dperez.inalerlab.suministro.lote;


import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
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
    private Calendar VencimientoLote;
    @ManyToOne
    private Suministro SuministroLote;
    @OneToMany(mappedBy = "LoteIngreso")
    private List<Ingreso> IngresosLote;
    @OneToMany(mappedBy = "LoteSalida")
    private List<Salida> SalidasLote;
    
    //	Constructores
    public Lote(){}
    public Lote(Calendar VencimientoLote){this.VencimientoLote = VencimientoLote;}
    
    //	Getters
    public int getIdLote(){return this.IdLote;}
    public Calendar getVencimientoLote(){return this.VencimientoLote;}
    public Suministro getSuministroLote(){return this.SuministroLote;}
    public List<Ingreso> getIngresosLote(){return this.IngresosLote;}
    public List<Salida> getSalidasLote(){return this.SalidasLote;}
    
    //	Setters
    public void setIdLote(int IdLote){this.IdLote = IdLote;}
    public void setVencimientoLote(Calendar VencimientoLote){this.VencimientoLote = VencimientoLote;}
    public void setSuministroLote(Suministro SuministroLote){
        this.SuministroLote = SuministroLote;
        if(!SuministroLote.getLotesSuministros().contains(this)){
            SuministroLote.getLotesSuministros().add(this);
        }
    }
    public void setIngresosLote(List<Ingreso> IngresosLote){this.IngresosLote = IngresosLote;}
    public void setSalidasLotes(List<Salida> SalidasLote){this.SalidasLote = SalidasLote;}
    
    
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
}