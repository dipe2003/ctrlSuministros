package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import javax.persistence.Entity;

@Entity
public class ReactivoQuimico extends Suministro{
    //	Constructores
    public ReactivoQuimico(){}
    public ReactivoQuimico(String NombreSuministro, String DescripcionSuministro, String CodigoSAPSuministro, 
            Unidad UnidadSuministro, Proveedor ProveedorSuministro, boolean AvisoCambioLote){
        super(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, 
                UnidadSuministro, ProveedorSuministro, AvisoCambioLote);
    }
}