/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.dperez.inalerlab.buffer;

import com.dperez.inalerlab.proveedor.Proveedor;
import com.dperez.inalerlab.suministro.Suministro;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;

/**
 *
 * @author dipe2
 */
@Stateless
public class FabricaBuffer {
    public BufferGenerico<Suministro> getBufferSuministro(List<Suministro> suministros){
        BufferGenerico<Suministro> buffer = BufferSuministros.getInstance();
        if(buffer.bufferSize()==0){
            buffer.setEntidades(suministros.stream()
                    .sorted()
                    .collect(Collectors.toMap((Suministro s)->s.getIdSuministro(), (Suministro s)->s)));
        }
        return buffer;
    }
    
    public BufferGenerico<Proveedor> getBufferProveedor(List<Proveedor> proveedores){
        BufferGenerico<Proveedor> buffer = BufferProveedores.getInstance();
        if(buffer.bufferSize()==0){
            buffer.setEntidades(proveedores.stream()
                    .sorted(Comparator.comparing((Proveedor p)->p.getNombreProveedor().toLowerCase()))
                    .collect(Collectors.toMap((Proveedor p)->p.getIdProveedor(), (Proveedor p)->p)));
        }
        return buffer;
    }
}
