package com.dperez.inalerlab.proveedor;

import com.dperez.inalerlab.suministro.EnumSuministro;
import com.dperez.inalerlab.suministro.Material;
import com.dperez.inalerlab.suministro.MedioEnsayo;
import com.dperez.inalerlab.suministro.ReactivoQuimico;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Proveedor implements Serializable{
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int IdProveedor;
    private String NombreProveedor;
    private String ContactoProveedor;
    @OneToMany(mappedBy = "ProveedorSuministro", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Suministro> SuministrosProveedor;
    
    //	Constructores
    public Proveedor() {
        this.SuministrosProveedor = new ArrayList<>();
    }
    public Proveedor(String NombreProveedor, String ContactoProveedor){
        this.NombreProveedor = NombreProveedor;
        this.ContactoProveedor = ContactoProveedor;
        this.SuministrosProveedor = new ArrayList<>();
    }
    
    //	Getters
    public int getIdProveedor(){return this.IdProveedor;}
    public String getNombreProveedor(){return this.NombreProveedor;}
    public String getContactoProveedor(){return this.ContactoProveedor;}
    public List<Suministro> getSuministrosProveedor(){return this.SuministrosProveedor;}
    
    //	Setters
    public void setIdProveedor(int IdProveedor){this.IdProveedor = IdProveedor;}
    public void setNombreProveedor(String NombreProveedor){this.NombreProveedor = NombreProveedor;}
    public void setContactoProveedor(String ContactoProveedor){this.ContactoProveedor = ContactoProveedor;}
    public void setSuministrosProveedor(List<Suministro> SuministrosProveedor){this.SuministrosProveedor = SuministrosProveedor;}
    
    //	SuministrosProveedor
    public void addSuministroProveedor(Suministro SuministroProveedor){this.SuministrosProveedor.add(SuministroProveedor);}
    public void removeSuministroProveedor(Suministro SuministroProveedor){this.SuministrosProveedor.remove(SuministroProveedor);}
    public boolean esProveedorSuministro(int IdSuministro){
        return SuministrosProveedor.stream()
                .anyMatch(suministro->suministro.getIdSuministro()==IdSuministro);
    }
    public Suministro CrearSuministro(String Nombre, String Descripcion, String CodigoSAP, Unidad UnidadSuministro, 
            EnumSuministro TipoSuministro, boolean AvisoCambioLote){
        Suministro suministro = null;
        switch (TipoSuministro) {
            case MATERIAL-> suministro = new Material(Nombre, Descripcion, CodigoSAP, UnidadSuministro, this, AvisoCambioLote);                
            case MEDIO_ENSAYO-> suministro = new MedioEnsayo(Nombre, Descripcion, CodigoSAP, UnidadSuministro, this, AvisoCambioLote);
            case REACTIVO_QUIMICO-> suministro = new ReactivoQuimico(Nombre, Descripcion, CodigoSAP, UnidadSuministro, this, AvisoCambioLote);
        }
        this.SuministrosProveedor.add(suministro);
        return suministro;
    }
}