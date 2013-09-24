/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.DepartmentPlacement;
import com.placement.entity.Placement;
import com.placement.exception.PlacementAppException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kemele
 */
@Stateless
public class DepartmentPlacementBL implements DepartmentPlacementBLRemote {

    @PersistenceContext(unitName = "StudentPlacementLibPU")
    private EntityManager em;

    @Override
    public boolean save(DepartmentPlacement dp) throws PlacementAppException {
        try {
            if (dp != null) {
                for(DepartmentPlacement d: this.getAll(dp.getPlacementId().getId())){
                    if(d.getDepartmentId()==dp.getDepartmentId()){
                        throw new PlacementAppException("Invalid Information! Department dupliced exception!");
                    }
                }                
                em.persist(dp);
                return true;
            }
        } catch (Exception e) {
            if(e instanceof PlacementAppException)
                throw (PlacementAppException)e;
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(DepartmentPlacement dp) throws PlacementAppException {
        try {
            if (dp != null) {
                for(DepartmentPlacement d: this.getAll(dp.getPlacementId().getId())){
                    if(d!=dp && d.getDepartmentId()==dp.getDepartmentId()){
                        throw new PlacementAppException("Invalid Information! Department dupliced exception!");
                    }
                }        
                em.merge(dp);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            if (id > 0) {
                DepartmentPlacement dp = em.find(DepartmentPlacement.class, id);
                em.remove(dp);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public DepartmentPlacement get(int id) {
        try {
            if (id > 0) {
                DepartmentPlacement dp = em.find(DepartmentPlacement.class, id);
                return dp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DepartmentPlacement> getAll() {
        try {
            String q = "SELECT p FROM " + DepartmentPlacement.class.getName() + " p";
            Query query = em.createQuery(q);
            List<DepartmentPlacement> DepartmentPlacements = query.getResultList();
            return DepartmentPlacements;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DepartmentPlacement> getAll(int placementId) {
        try {
            if (placementId > 0) {
                Placement p = em.find(Placement.class, placementId);
                if (p != null) {
                    String q = "SELECT p FROM " + DepartmentPlacement.class.getName() + " p WHERE p.placementId=:placementId";
                    Query query = em.createQuery(q).setParameter("placementId", p);
                    List<DepartmentPlacement> DepartmentPlacements = query.getResultList();
                    return DepartmentPlacements;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
