package com.dperez.inalerlab.suministro.lote;

import com.dperez.inalerlab.operario.Operario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Salida implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdSalida;
    @Temporal(TemporalType.DATE)
    private Date FechaSalida;
    private float CantidadSalida;
    @ManyToOne
    private Lote LoteSalida;
    @ManyToOne
    private Operario OperarioSalidaSuministro;
    private String ObservacionesSalida;
    
    //	Constructores
    public Salida(){}
    public Salida(Date FechaSalida, float CantidadSalida,String ObservacionesSalida, Lote LoteSalida, Operario OperarioSalida){
        this.FechaSalida = FechaSalida;
        this.CantidadSalida = CantidadSalida;
        this.ObservacionesSalida = ObservacionesSalida;
        this.LoteSalida = LoteSalida;
        this.OperarioSalidaSuministro = OperarioSalida;
    }
    
    //	Getters
    public int getIdSalida(){return this.IdSalida;}
    public Date getFechaSalida(){return this.FechaSalida;}
    public float getCantidadSalida(){return this.CantidadSalida;}
    public Operario getOperarioSalidaSuministro(){return this.OperarioSalidaSuministro;}
    public Lote getLoteSalida() {return LoteSalida;}
    public String getObservacionesSalida() {return ObservacionesSalida;}
    public String getStrFechaSalida(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(FechaSalida==null) return "---";
        return sdf.format(FechaSalida);    
    }
    //	Setters
    public void setIdSalida(int IdSalida){this.IdSalida = IdSalida;}
    public void setFechaSalida(Date FechaSalida){this.FechaSalida = FechaSalida;}
    public void setCantidadSalida(float CantidadSalida){this.CantidadSalida = CantidadSalida;}
    public void setOperarioSalidaSuministro(Operario OperarioSalidaSuministro){        
        if(!OperarioSalidaSuministro.getSalidasSuministrosOperario().contains(this)){
            OperarioSalidaSuministro.addSalidaSuministroOperario(this);
        }
        this.OperarioSalidaSuministro = OperarioSalidaSuministro;
    }
    public void setLoteSalida(Lote LoteSalida) {
        this.LoteSalida = LoteSalida;
    }
    public void setObservacionesSalida(String ObservacionesSalida) {this.ObservacionesSalida = ObservacionesSalida;}
    
}