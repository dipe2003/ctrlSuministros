package com.dperez.inalerlab.suministro.lote;


import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    private Date VencimientoLote;
    private String NumeroLote;
    @ManyToOne
    private Suministro SuministroLote;
    @OneToMany(mappedBy = "LoteIngreso", fetch = FetchType.EAGER)
    private List<Ingreso> IngresosLote;
    @OneToMany(mappedBy = "LoteSalida", fetch = FetchType.EAGER)
    private List<Salida> SalidasLote;
    
    //	Constructores
    public Lote(){
        SalidasLote = new ArrayList();
        IngresosLote = new ArrayList();
    }
    public Lote(Date VencimientoLote, String NumeroLote){
        this.VencimientoLote = VencimientoLote;
        this.NumeroLote = NumeroLote;
        SalidasLote = new ArrayList();
        IngresosLote = new ArrayList();
    }
    
    //	Getters
    public int getIdLote(){return this.IdLote;} 
    public Date getVencimientoLote(){return this.VencimientoLote;}
    public Suministro getSuministroLote(){return this.SuministroLote;}
    public List<Ingreso> getIngresosLote(){return this.IngresosLote;}
    public List<Salida> getSalidasLote(){return this.SalidasLote;}
    public String getNumeroLote() {return NumeroLote;}
    public String getStrFechaVencimientoLote(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(VencimientoLote==null) return "---";
        return sdf.format(VencimientoLote);        
    }
    
    //	Setters
    public void setIdLote(int IdLote){this.IdLote = IdLote;}   
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
        if (IngresoLote.getLoteIngreso()==null || !IngresoLote.getLoteIngreso().equals(this)) {
            IngresoLote.setLoteIngreso(this);
        }
    }
    
    public void addSalidaLote(Salida SalidaLote){
        this.SalidasLote.add(SalidaLote);
        if (SalidaLote.getLoteSalida() == null || !SalidaLote.getLoteSalida().equals(this)) {
            SalidaLote.setLoteSalida(this);
        }
    }
    
    public Ingreso getUltimoIngreso(){
        int max = 0;
        int index = 0;
        for (int i = 0; i < IngresosLote.size(); i++) {
            if(IngresosLote.get(i).getIdIngreso() > max){
                max = IngresosLote.get(i).getIdIngreso();
                index = i;
            }
        }
        return IngresosLote.get(index);
    }
    
    public boolean EstaVencido(){
        Date hoy = Calendar.getInstance().getTime();
        boolean vencido = false;
        try{
            vencido = VencimientoLote.before(hoy);
        }catch(NullPointerException ex){}
        return vencido;
    }
}