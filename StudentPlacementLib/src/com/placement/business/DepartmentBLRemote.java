/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Department;
import com.placement.exception.PlacementAppException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kemele
 */
@Remote
public interface DepartmentBLRemote  extends BusinessRemote{
    public boolean save(Department department) throws PlacementAppException;
    public boolean update(Department department)  throws PlacementAppException;
    public boolean delete(int id);
    public Department get(int id);
    public List<Department> getAll();
}
