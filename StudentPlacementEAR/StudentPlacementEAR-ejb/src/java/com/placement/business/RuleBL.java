/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Rule;
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
public class RuleBL implements RuleBLRemote {
    
    @PersistenceContext(unitName = "StudentPlacementLibPU")
    private EntityManager em;
    
    @Override
    public boolean save(Rule rule) throws PlacementAppException {
        try {
            if (validatePercentage(rule)) {
                em.persist(rule);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean update(Rule rule) throws PlacementAppException {
        try {
            if (validatePercentage(rule) && rule.getId() > 0) {
                Rule r = em.find(Rule.class, rule.getId());
                if (r != null) {
                    em.merge(rule);
                    return true;
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
                Rule r = em.find(Rule.class, id);
                if (r != null) {
                    em.remove(r);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Rule get(int id) {
        try {
            if (id > 0) {
                Rule r = em.find(Rule.class, id);
                return r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Rule> getAll() {
        try {
            String q = "SELECT p FROM " + Rule.class.getName() + " p";
            Query query = em.createQuery(q);
            List<Rule> rules = query.getResultList();
            return rules;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private boolean validatePercentage(Rule rule) throws PlacementAppException {
        if (rule != null) {
            double percent = rule.getTopPercentage() + rule.getFemalePercentage() + rule.getRegionPercentage();
            if (rule.getDisabilityPercentage() == null || rule.getDisabilityPercentage() == 0.0) {
                rule.setDisabilityPercentage(100.0);
            }
            if (rule.getCutPoint() == null) {
                rule.setCutPoint(0.0);
            } 
            if (percent == 100.0) {
                return true;
            } else {
                throw new PlacementAppException("Invalid percentage information provided.The sum of the three percentages must be 100");
            }
        } else {
            throw new PlacementAppException("Rule should not be null!");
        }
    }
}
