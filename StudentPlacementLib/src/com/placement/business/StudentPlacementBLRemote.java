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
public interface StudentPlacementBLRemote  extends BusinessRemote{
    public List<Student> placeOncePreview(int placementId);
    public List<Student> placeOnceSave();
    public List<Student> unplaceAll();
    public List<Student> getStudents(int placementId);
}
