package com.dperez.inalerlab.suministro;

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
public class ManejadorSuministro {
    @PersistenceContext(unitName = "CtrlSuministros_PU")
    private EntityManager em;
    
    public int CrearSuministro(Suministro Suministro){
        try{
            em.persist(Suministro);
            return Suministro.getIdSuministro();
        }catch(Exception ex){}
        return -1;
    }
    
    public int ActualizarSuministro(Suministro Suministro){
        try{
            em.merge(Suministro);
            return Suministro.getIdSuministro();
        }catch(Exception ex){}
        return -1;
    }
    
    public int BorrarSuministro(Suministro Suministro){
        try{
            em.remove(Suministro);
            return Suministro.getIdSuministro();
        }catch(Exception ex){}
        return -1;
    }
    
    public Suministro ObtenerSuministro(int IdSuministro){
        TypedQuery<Suministro> query = em.createQuery("FROM Suministro s WHERE s.IdSuministro= :idSuministro", Suministro.class);
        query.setParameter("idSuministro", IdSuministro);
        try{
            return query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
    public List<Suministro> ListarSuministros(){
        List<Suministro> suministros = new ArrayList<>();
        TypedQuery<Suministro> query = em.createQuery("FROM Suministro s", Suministro.class);
        try{
            suministros = query.getResultList();
        }catch(Exception ex){}
        return suministros;
    }
}