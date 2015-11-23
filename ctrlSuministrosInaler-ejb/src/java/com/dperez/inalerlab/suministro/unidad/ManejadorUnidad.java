package com.dperez.inalerlab.suministro.unidad;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Named
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManejadorUnidad {
    @PersistenceContext(unitName = "CtrlSuministros_PU")
    private EntityManager em;
    
    public int CrearUnidad(Unidad Unidad){
        try{
            em.persist(Unidad);
            return Unidad.getIdUnidad();
        }catch(Exception ex){}
        return -1;
    }
    
    public int ActualizarUnidad(Unidad Unidad){
        try{
            em.merge(Unidad);
            return Unidad.getIdUnidad();
        }catch(Exception ex){}
        return -1;
    }
    
    public int BorrarUnidad(Unidad Unidad){
        try{
            em.remove(Unidad);
            return Unidad.getIdUnidad();
        }catch(Exception ex){}
        return -1;
    }
    
    public Unidad ObtenerUnidad(int IdUnidad){        
        try{
            TypedQuery<Unidad> query = em.createQuery("FROM Unidad u WHERE u.IdUnidad= :idUnidad", Unidad.class);
            query.setParameter("idUnidad", IdUnidad);
            return query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
    public List<Unidad> ListarUnidades(){
        List<Unidad> Unidades = new ArrayList<>();
        try{
            TypedQuery<Unidad> query = em.createQuery("FROM Unidad u", Unidad.class);
            Unidades = query.getResultList();
        }catch(Exception ex){}
        return Unidades;
    }
    
    public Unidad ObtenerUnidadSuministro(int IdSuministro){
        try{
            TypedQuery<Unidad> query = em.createQuery("SELECT u FROM Unidad u, Suministro s WHERE s.IdSuministro= :idSuministro AND s.UnidadSuministro.IdUnidad = u.IdUnidad", Unidad.class);
            query.setParameter("idSuministro", IdSuministro);
            return query.getSingleResult();
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }
    
    
}