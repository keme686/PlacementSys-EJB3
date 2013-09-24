/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.College;
import com.placement.entity.Department;
import com.placement.exception.PlacementAppException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author kemele
 */
@Stateless
public class DepartmentBL implements DepartmentBLRemote {

    @PersistenceContext(unitName = "StudentPlacementLibPU")
    private EntityManager em;

    @Override
    public boolean save(Department department) throws PlacementAppException {
        try{
            if(validateDepartment(department)){
                em.persist(department);
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Department department) throws PlacementAppException {
        try{
            if(!validateDepartment(department)){                
                em.merge(department);
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try{
            if(id>0){
                Department d= em.find(Department.class, id);
                em.remove(d);
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Department get(int id) {
        try{
        if(id>0){
            Department d = em.find(Department.class, id);
            return d;
        }
        }catch(Exception e){
            e.printStackTrace();                    
        }
        return null;
    }

    @Override
    public List<Department> getAll() {
        try {
            String q = "SELECT p FROM " + Department.class.getName() + " p";
            TypedQuery<Department> query = em.createQuery(q, Department.class);
            List<Department> departments = query.getResultList();
            return departments;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean validateDepartment(Department department) throws PlacementAppException{        
        if (department != null && department.getCollegeId()!=null) {
             College c= em.find(College.class, department.getCollegeId().getId());
             if(c==null){
                 throw new PlacementAppException("Invalid College information found. College shouldnt be null!");
             }
            String q = "SELECT p FROM " + Department.class.getName() + " p WHERE p.code = :code";
            Query query = em.createQuery(q).setParameter("code", department.getCode());
            List<Department> departments = query.getResultList();
            if (departments != null && departments.size() > 0) {
                return false;
            } else {
                return true;
            }
        } else {
            throw new PlacementAppException("Invalid Department/College information found. Department/College shouldnt be null!");
        }
    }
}
