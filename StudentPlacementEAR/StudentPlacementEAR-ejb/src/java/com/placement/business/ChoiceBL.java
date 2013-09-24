/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Choice;
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
public class ChoiceBL implements ChoiceBLRemote {

    @PersistenceContext(unitName = "StudentPlacementLibPU")
    private EntityManager em;

    @Override
    public boolean save(Choice choice) throws PlacementAppException {
        try {
            if (choice != null) {
                Student s = em.find(Student.class, choice.getIdNumber().getIdNumber());
                if (s.getChoiceList() != null && s.getChoiceList().size() > 0) {
                    String q = "SELECT p FROM " + Choice.class.getName() + " p WHERE p.idNumber=:idNumber AND p.departmentPlacementId=:departmentPlacementId";
                    Query query = em.createQuery(q).setParameter("idNumber", s).setParameter("departmentPlacementId", choice.getDepartmentPlacementId());
                    Choice c = (Choice) query.getSingleResult();
                    for (Choice ch : this.getChoices(s.getIdNumber())) {
                        if (ch != c && ch.getRank() == choice.getRank()) {
                            return false; //duplicated ranking
                        }
                    }
                    c.setRank(choice.getRank());
                    c.setLastModified(choice.getLastModified());
                    em.merge(c);

                } else {
                    for (Choice ch : this.getChoices(s.getIdNumber())) {
                        if (ch.getRank() == choice.getRank()) {
                            return false; //duplicated ranking
                        }
                    }
                    em.persist(choice);
                }
                return true;
            } else {
                throw new PlacementAppException("Invalid choice information. Choice object should not be null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Choice choice) throws PlacementAppException {
        try {
            if (choice != null) {
                for (Choice ch : this.getChoices(choice.getIdNumber().getIdNumber())) {
                        if (ch != choice && ch.getRank() == choice.getRank()) {
                            throw new PlacementAppException("Invalid Choice information. Choice ranking must be unique!");//duplicated ranking
                        }
                    }
                em.merge(choice);
                return true;
            } else {
                throw new PlacementAppException("Invalid Choice information. Choice object should not be null!");
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
                Choice c = em.find(Choice.class, id);
                em.remove(c);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Choice get(int id) {
        try {
            if (id > 0) {
                Choice c = em.find(Choice.class, id);
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Choice> getAll() {
        try {
            String q = "SELECT p FROM " + Choice.class.getName() + " p";
            Query query = em.createQuery(q);
            List<Choice> Choices = query.getResultList();
            return Choices;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Choice> getChoices(String idnum) {
        try {
            String q = "SELECT p FROM " + Choice.class.getName() + " p WHERE p.idNumber=:idNumber";
            Student s = em.find(Student.class, idnum);
            Query query = em.createQuery(q).setParameter("idNumber", s);
            List<Choice> Choices = query.getResultList();
            return Choices;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
