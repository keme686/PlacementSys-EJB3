/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Choice;
import com.placement.entity.Student;
import com.placement.exception.PlacementAppException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kemele
 */
@Remote
public interface StudentBLRemote  extends BusinessRemote{
    public boolean save(Student student) throws PlacementAppException;
    public boolean update(Student student)  throws PlacementAppException;
    public boolean delete(String idnum);
    public Student get(String idnum);
    public List<Student> getAll();
    public int saveAll(List<Student> cellDataList);
    public List<Student> search(Student student, int cgpabound);
    public List<Student> search(String idnum);
    public List<Choice> getChoices(String idnum);
}
