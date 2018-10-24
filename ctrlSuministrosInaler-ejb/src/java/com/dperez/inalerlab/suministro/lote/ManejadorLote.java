package com.dperez.inalerlab.suministro.lote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
        try{
            TypedQuery<Lote> query = em.createQuery("SELECT l FROM Lote l WHERE l.IdLote= :idLote", Lote.class);
            query.setParameter("idLote", IdLote);
            return (Lote) query.getSingleResult();
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }
    
    public Lote ObtenerLote(String NumeroLote){
        try{
            TypedQuery<Lote> query = em.createQuery("SELECT l FROM Lote l WHERE l.NumeroLote= :numeroLote", Lote.class);
            query.setParameter("numeroLote", NumeroLote);
            return (Lote) query.getSingleResult();
        }catch(Exception ex){
            System.out.println("Error: "+ ex.getMessage());
        }
        return null;
    }
    
    public List<Lote> ListarLotes(){
        List<Lote> suministros = new ArrayList<>();
        try{
            TypedQuery<Lote> query = em.createQuery("SELECT l FROM Lote l", Lote.class);
            suministros = query.getResultList();
        }catch(Exception ex){}
        return suministros;
    }
    
    public int ExisteNumeroLoteSuministro(String NumeroLote, int IdSuministro){
        try{
            Query query = em.createQuery("SELECT lot.IdLote FROM Suministro s, Lote lot WHERE lot MEMBER OF s.LotesSuministros AND lot.NumeroLote= :numeroLote AND s.IdSuministro= :idSuministro");
            query.setParameter("idSuministro", IdSuministro);
            query.setParameter("numeroLote", NumeroLote);
            return (int) query.getSingleResult();
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return 0;
    }
    
    public Map<String, Integer> ListarMapLotes(int IdSuministro){
        Map<String, Integer> map = new HashMap<>();        
        try{
            Query query = em.createQuery("SELECT lot.NumeroLote, lot.IdLote FROM Lote lot WHERE lot.SuministroLote.IdSuministro= :idSuministro");
            query.setParameter("idSuministro", IdSuministro);
            List<Object[]> resultado = query.getResultList();
            for(Object[] res: resultado){
                map.put((String) res[0], (int) res[1]);
            }
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return map;
    }
    
    public Map<Integer, Lote> ListarMapLotesFull(int IdSuministro){
        Map<Integer, Lote> map = new HashMap<>();        
        try{
            Query query = em.createQuery("SELECT lot FROM Lote lot WHERE lot.SuministroLote.IdSuministro= :idSuministro");
            query.setParameter("idSuministro", IdSuministro);
            List<Lote> resultado = query.getResultList();
            map = resultado.stream()
                    .collect(Collectors.toMap(Lote::getIdLote, lote->lote));
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return map;
    }
}