/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Student;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kemele
 */
@Remote
public interface UploadStudentBLRemote extends BusinessRemote{

    public List<Student> getStudents();

    public void setStudentsList(List<Student> students);

    public boolean saveAll();

    public Student get(String idnum);

    public boolean update(Student student);

    public boolean remove(Student student);
}
