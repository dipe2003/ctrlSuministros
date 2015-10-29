package com.dperez.inalerlab.suministro.lote;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Named
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManejadorIngresoSalida {
    @PersistenceContext(unitName = "CtrlSuministros_PU")
    private EntityManager em;
    
    /*
     *  INGRESOS
     */
    
    public int CrearIngreso(Ingreso Ingreso){
        try{
            em.persist(Ingreso);
            return Ingreso.getIdIngreso();
        }catch(Exception ex){}
        return -1;
    }
    
    public int ActualizarIngreso(Ingreso Ingreso){
        try{
            em.merge(Ingreso);
            return Ingreso.getIdIngreso();
        }catch(Exception ex){}
        return -1;
    }
    
    public int BorrarIngreso(Ingreso Ingreso){
        try{
            em.remove(Ingreso);
            return Ingreso.getIdIngreso();
        }catch(Exception ex){}
        return -1;
    }
    
    public Ingreso ObtenerIngreso(int IdIngreso){
        TypedQuery<Ingreso> query = em.createQuery("FROM Ingreso s WHERE s.IdIngreso= :idIngreso", Ingreso.class);
        query.setParameter("idIngreso", IdIngreso);
        try{
            return query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
    public List<Ingreso> ListarIngresos(){
        List<Ingreso> ingresos = new ArrayList<>();
        TypedQuery<Ingreso> query = em.createQuery("FROM Ingreso s", Ingreso.class);
        try{
            ingresos = query.getResultList();
        }catch(Exception ex){}
        return ingresos;
    }
    
    /*
     *  SALIDAS
     */
    
    public int CrearSalida(Salida Salida){
        try{
            em.persist(Salida);
            return Salida.getIdSalida();
        }catch(Exception ex){}
        return -1;
    }
    
    public int ActualizarSalida(Salida Salida){
        try{
            em.merge(Salida);
            return Salida.getIdSalida();
        }catch(Exception ex){}
        return -1;
    }
    
    public int BorrarSalida(Salida Salida){
        try{
            em.remove(Salida);
            return Salida.getIdSalida();
        }catch(Exception ex){}
        return -1;
    }
    
    public Salida ObtenerSalida(int IdSalida){
        TypedQuery<Salida> query = em.createQuery("FROM Salida s WHERE s.IdSalida= :idSalida", Salida.class);
        query.setParameter("idSalida", IdSalida);
        try{
            return query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
    public List<Salida> ListarSalidas(){
        List<Salida> salidas = new ArrayList<>();
        TypedQuery<Salida> query = em.createQuery("FROM Salida s", Salida.class);
        try{
            salidas = query.getResultList();
        }catch(Exception ex){}
        return salidas;
    }
}