package com.dperez.inalerlab.suministro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
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
    
    public List<Suministro> ListarSuministros(boolean Vigente){
        List<Suministro> suministros = new ArrayList<>();
        TypedQuery<Suministro> query = em.createQuery("SELECT s FROM Suministro s WHERE s.Vigente= :vigente", Suministro.class);
        try{
            query.setParameter("vigente", Vigente);
            suministros = query.getResultList();
        }catch(Exception ex){}
        return suministros.stream()
                .sorted()
                .collect(Collectors.toList());
    }
    
    public Map<String, Integer> ListarSuministrosProveedor(int IdProveedor){
        Map<String, Integer> suministros = new HashMap<>();
        if(IdProveedor>0){
            TypedQuery<Suministro> query = em.createQuery("SELECT s FROM Suministro s, Proveedor p WHERE s.ProveedorSuministro.IdProveedor= :idProveedor", Suministro.class);
            query.setParameter("idProveedor", IdProveedor);
            try{
                List<Suministro> list = query.getResultList();
                suministros = list.stream()
                        .sorted()
                        .collect(Collectors.toMap(Suministro::getNombreSuministro, suministro->suministro.getIdSuministro()));
            }catch(Exception ex){
                System.out.println("Error: " + ex.getMessage());
            }
        }
        return new TreeMap<>(suministros);
    }
    
    public Map<String, Integer> ListarMapSuministros(boolean Vigente){
        Map<String, Integer> suministros = new HashMap<>();
        TypedQuery<Suministro> query = em.createQuery("SELECT s FROM Suministro s WHERE s.Vigente= :vigente ", Suministro.class);
        try{
            query.setParameter("vigente", Vigente);
            List<Suministro> list = query.getResultList();
            list.stream()
                    .sorted()
                    .forEachOrdered(suministro-> {
                        suministros.put(suministro.getNombreSuministro() + " (" + suministro.getProveedorSuministro().getNombreProveedor() + ")", suministro.getIdSuministro());
                    });
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return suministros;
    }
    
    public Map<Integer, Suministro> ListarMapSuministrosFull(){
        Map<Integer, Suministro> suministros = new HashMap<>();
        TypedQuery<Suministro> query = em.createQuery("SELECT s FROM Suministro s ", Suministro.class);
        try{
            List<Suministro> lista = query.getResultList();
            suministros = lista.stream()
                    .sorted()
                    .collect(Collectors.toMap(Suministro::getIdSuministro, suministro->suministro));
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return new TreeMap<>(suministros);
    }
}