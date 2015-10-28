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
        TypedQuery<Lote> query = em.createQuery("FROM Lote s WHERE s.IdLote= :idLote", Lote.class);
        query.setParameter("idLote", IdLote);
        try{
            return query.getSingleResult();
        }catch(Exception ex){}
        return null;
    }
    
    public List<Lote> ListarLotes(){
        List<Lote> suministros = new ArrayList<>();
        TypedQuery<Lote> query = em.createQuery("FROM Lote s", Lote.class);
        try{
            suministros = query.getResultList();
        }catch(Exception ex){}
        return suministros;
    }
}