package com.dperez.inalerlab.suministro;

import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.Suministro;
import com.dperez.inalerlab.suministro.unidad.Unidad;
import javax.persistence.Entity;

@Entity
public class Material extends Suministro{
	//	Constructores
	public Material(){}
	public Material(String NombreSuministro, String DescripcionSuministro, String CodigoSAPSuministro, 
            Unidad UnidadSuministro, Proveedor ProveedorSuministro){
		super(NombreSuministro, DescripcionSuministro, CodigoSAPSuministro, 
            UnidadSuministro, ProveedorSuministro);
	}
}