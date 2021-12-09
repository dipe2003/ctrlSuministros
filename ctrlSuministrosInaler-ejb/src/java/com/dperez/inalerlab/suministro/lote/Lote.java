package com.dperez.inalerlab.suministro.lote;


import com.dperez.inalerlab.operario.Operario;
import com.dperez.inalerlab.suministro.Suministro;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Lote implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdLote;
    @Temporal(TemporalType.DATE)
    private Date VencimientoLote;
    private String NumeroLote;
    @ManyToOne
    private Suministro SuministroLote;
    @OneToMany(mappedBy = "LoteIngreso", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Ingreso> IngresosLote;
    @OneToMany(mappedBy = "LoteSalida", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Salida> SalidasLote;
    
    //	Constructores
    public Lote(){
        SalidasLote = new ArrayList<>();
        IngresosLote = new ArrayList<>();
    }
    public Lote(Date VencimientoLote, String NumeroLote, Suministro suministro){
        this.VencimientoLote = VencimientoLote;
        this.NumeroLote = NumeroLote;
        this.SuministroLote = suministro;
        SalidasLote = new ArrayList<>();
        IngresosLote = new ArrayList<>();
    }
    
    //	Getters
    public int getIdLote(){return this.IdLote;}
    public Date getVencimientoLote(){return this.VencimientoLote;}
    public Suministro getSuministroLote(){return this.SuministroLote;}
    public List<Ingreso> getIngresosLote(){return this.IngresosLote;}
    public List<Salida> getSalidasLote(){return this.SalidasLote;}
    public String getNumeroLote() {return NumeroLote;}
    public String getStrFechaVencimientoLote(){
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
     * @return
     */
    public float getCantidadIngresosLote(){
        return (float) IngresosLote.stream()
                .mapToDouble((Ingreso i) ->i.getCantidadIngreso())
                .sum();
    }
    
    /**
     *	Devuelve la cantidad egresada del Suministro con el lote.
     * @return
     */
    public float getCantidadSalidasLote(){
        return (float) SalidasLote.stream()
                .mapToDouble((Salida a) ->a.getCantidadSalida())
                .sum();
    }
    
    /**
     *	Devuelve la cantidad en stock del Suministro con el lote.
     * @return
     */
    public float getCantidadStock(){
        return this.getCantidadIngresosLote() - this.getCantidadSalidasLote();
    }
    
    public Ingreso CrearIngreso(Date FechaIngreso, float CantidadIngreso, String NumeroFactura, String Observaciones, Operario operario){
        Ingreso ingreso = new Ingreso(FechaIngreso, CantidadIngreso, NumeroFactura, Observaciones, this, operario);
        this.IngresosLote.add(ingreso);
        return ingreso;
    }
    
    public Salida CrearSalida(Date FechaSalida, float CantidadSalida, String Observaciones, Operario operario){
        Salida salida = new Salida(FechaSalida, CantidadSalida,Observaciones, this, operario);
        this.SalidasLote.add(salida);
        return salida;
    }
    
    /**
     * Comprueba cada ingreso y devuelve el ultimo registro.
     * @return
     */
    public Ingreso getUltimoIngreso(){        
        return IngresosLote.stream()
                .max(Comparator.comparing(i->i.getFechaIngreso()))
                .orElse(null);
        
    }
    
    public Ingreso FindIngreso(int IdIngreso){
        return this.IngresosLote.stream()
                .filter((Ingreso ingreso)->ingreso.getIdIngreso() == IdIngreso)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Comprueba cada salida y devuelve el ultimo registro.
     * @return
     */
    public Salida getUltimaSalida(){
        return SalidasLote.stream()
                .max(Comparator.comparing((Salida salida)->salida.getFechaSalida()))
                .orElse(null);
    }
    
    public Salida FindSalida(int IdSalida){
        return this.SalidasLote.stream()
                .filter(s->s.getIdSalida() == IdSalida)
                .findFirst()
                .orElse(null);
    }
    
    public boolean EstaVencido(){
        Date hoy = Calendar.getInstance().getTime();
        boolean vencido = false;
        try{
            vencido = VencimientoLote.before(hoy);
        }catch(NullPointerException ex){}
        return vencido;
    }
    public boolean isVencido(){
        return EstaVencido();
    }
}