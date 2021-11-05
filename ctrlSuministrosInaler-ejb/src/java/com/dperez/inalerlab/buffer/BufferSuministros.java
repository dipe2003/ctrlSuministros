/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dperez.inalerlab.buffer;

import com.dperez.inalerlab.suministro.Suministro;

/**
 * Clase que extiende el BufferGenerico<<T>> para utilizarse como Singleton.
 * 05/1/2021
 * @author dipe2
 */
public class BufferSuministros extends BufferGenerico<Suministro> {
    private BufferSuministros() {
    }
    
    public static BufferSuministros getInstance() {
        return BufferSuministrosHolder.INSTANCE;
    }
    
    private static class BufferSuministrosHolder {
        private static final BufferSuministros INSTANCE = new BufferSuministros();
    }
}
