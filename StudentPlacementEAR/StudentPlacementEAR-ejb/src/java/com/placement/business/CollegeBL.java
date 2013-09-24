/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.College;
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
public class CollegeBL implements CollegeBLRemote {

    @PersistenceContext(unitName = "StudentPlacementLibPU")
    private EntityManager em;

    @Override
    public boolean save(College college) throws PlacementAppException {
        try {
            if (validateCollege(college)) {                
                em.persist(college);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(College college) throws PlacementAppException {
        try {
            if (!validateCollege(college)) {
                em.merge(college);
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
                College c = em.find(College.class, id);
                em.remove(c);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public College get(int id) {
        try {
            if (id > 0) {
                College c = em.find(College.class, id);
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<College> getAll() {
        try {
            String q = "SELECT p FROM " + College.class.getName() + " p";
            Query query = em.createQuery(q);
            List<College> colleges = query.getResultList();
            return colleges;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean validateCollege(College college) throws PlacementAppException {
        if (college != null) {
            String q = "SELECT p FROM " + College.class.getName() + " p WHERE p.code = :code";
            Query query = em.createQuery(q).setParameter("code", college.getCode());
            List<College> colleges = query.getResultList();
            if (colleges != null && colleges.size() > 0) {
                return false;
            } else {
                return true;
            }
        } else {
            throw new PlacementAppException("Invalid College information found. College shouldnt be null!");
        }
    }
}
