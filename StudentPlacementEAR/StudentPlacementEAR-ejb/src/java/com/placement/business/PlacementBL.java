/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Placement;
import com.placement.entity.Student;
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
public class PlacementBL implements PlacementBLRemote {

    @PersistenceContext(unitName = "StudentPlacementLibPU")
    private EntityManager em;

    @Override
    public boolean save(Placement placement) throws PlacementAppException {
        try {
            if (placement != null) {
                em.persist(placement);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Placement placement) throws PlacementAppException {
        try {
            if (placement != null) {
                Placement p = em.find(Placement.class, placement.getId());
                if (p != null) {
                    em.merge(placement);
                    return true;
                } else {
                    throw new PlacementAppException("Cannot update date, Placement information doesnt exist!");
                }
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
                Placement p = em.find(Placement.class, id);
                em.remove(p);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Placement get(int id) {
        try {
            if (id > 0) {
                Placement p = em.find(Placement.class, id);
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Placement> getAll() {
        try {
            String q = "SELECT p FROM " + Placement.class.getName() + " p";
            Query query = em.createQuery(q);
            List<Placement> Placements = query.getResultList();
            return Placements;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents(int placementid) {
        try {
            if (placementid > 0) {
                Placement p = em.find(Placement.class, placementid);
                String q = "SELECT p FROM " + Student.class.getName() + " p WHERE p.placementId=:placementId";
                Query query = em.createQuery(q).setParameter("placementId", p);
                List<Student> Students = query.getResultList();
                return Students;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
