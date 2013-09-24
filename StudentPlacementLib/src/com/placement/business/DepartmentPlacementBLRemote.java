/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.DepartmentPlacement;
import com.placement.exception.PlacementAppException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kemele
 */
@Remote
public interface DepartmentPlacementBLRemote  extends BusinessRemote{
    public boolean save(DepartmentPlacement dp) throws PlacementAppException;
    public boolean update(DepartmentPlacement dp)  throws PlacementAppException;
    public boolean delete(int id);
    public DepartmentPlacement get(int id);
    public List<DepartmentPlacement> getAll();
     public List<DepartmentPlacement> getAll(int placementId);
}
