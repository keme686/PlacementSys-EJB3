/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Placement;
import com.placement.entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author kemele
 */
@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class UploadStudentBL implements UploadStudentBLRemote {

    @PersistenceContext(unitName = "StudentPlacementLibPU")
    private EntityManager em;
    List<Student> students = null;
    Placement placement = null;
    UserTransaction tx = null;
    @Resource
    EJBContext context;

    @Override
    public List<Student> getStudents() {
        if(students == null)
            return new ArrayList<Student>();
        return students;
    }

    @Override
    public boolean saveAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Student get(String idnum) {
        if (students == null) {
            return null;
        }
        for (Student s : students) {
            if (s.getIdNumber().equals(idnum)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean update(Student student) {
        if (student == null) {
            return false;
        }
        Student s = this.get(student.getIdNumber());
        if (s != null || students.remove(s)) {
            students.add(student);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(Student student) {
        if (student != null && students.remove(student)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setStudentsList(List<Student> students) {
        
        if (students != null) {
            this.students = students;
            System.out.println("students list contains "+  students.size());
        }
        else
            System.out.println("students list is null");
    }
}
