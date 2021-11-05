/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dperez.inalerlab.buffer;

import com.dperez.inalerlab.proveedor.Proveedor;

/**
 * Clase que extiende el BufferGenerico<<T>> para utilizarse como Singleton.
 * 05/11/2021
 * @author dipe2
 */
public class BufferProveedores extends BufferGenerico<Proveedor> {
    private BufferProveedores() {
    }
    
    public static BufferProveedores getInstance() {
        return BufferSuministrosHolder.INSTANCE;
    }
    
    private static class BufferSuministrosHolder {
        private static final BufferProveedores INSTANCE = new BufferProveedores();
    }
}
