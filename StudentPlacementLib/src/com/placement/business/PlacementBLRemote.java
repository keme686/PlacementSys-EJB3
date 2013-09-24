/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Placement;
import com.placement.entity.Student;
import com.placement.exception.PlacementAppException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kemele
 */
@Remote
public interface PlacementBLRemote  extends BusinessRemote{
    public boolean save(Placement placement) throws PlacementAppException;
    public boolean update(Placement placement)  throws PlacementAppException;
    public boolean delete(int id);
    public Placement get(int id);
    public List<Placement> getAll();
    public List<Student> getAllStudents(int placementid);
    
}
