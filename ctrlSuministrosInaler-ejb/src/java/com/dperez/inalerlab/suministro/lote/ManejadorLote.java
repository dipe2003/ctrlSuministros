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
public class ManejadorLote {
    @PersistenceContext(unitName = "CtrlSuministros_PU")
    private EntityManager em;
    
    public int CrearLote(Lote Lote){
        try{
            em.persist(Lote);
            return Lote.getIdLote();
        }catch(Exception ex){}
        return -1;
    }
    
    public int ActualizarLote(Lote Lote){
        try{
            em.merge(Lote);
            return Lote.getIdLote();
        }catch(Exception ex){}
        return -1;
    }
    
    public int BorrarLote(Lote Lote){
        try{
            em.remove(Lote);
            return Lote.getIdLote();
        }catch(Exception ex){}
        return -1;
    }
    
    public Lote ObtenerLote(int IdLote){
        TypedQuery<Lote> query = em.createQuery("SELECT l FROM Lote l WHERE l.IdLote= :idLote", Lote.class);
        query.setParameter("idLote", IdLote);
        try{
            return (Lote) query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
    public Lote ObtenerLote(String NumeroLote){
        TypedQuery<Lote> query = em.createQuery("SELECT l FROM Lote l WHERE l.NumeroLote= :numeroLote", Lote.class);
        query.setParameter("numeroLote", NumeroLote);
        try{
            return (Lote) query.getSingleResult();
        }catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
        }
        return null;
    }
    
    public List<Lote> ListarLotes(){
        List<Lote> suministros = new ArrayList<>();
        TypedQuery<Lote> query = em.createQuery("SELECT l FROM Lote l", Lote.class);
        try{
            suministros = query.getResultList();
        }catch(Exception ex){}
        return suministros;
    }
    
    
}