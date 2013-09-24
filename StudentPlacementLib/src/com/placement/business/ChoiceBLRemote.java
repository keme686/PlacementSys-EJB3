/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Choice;
import com.placement.exception.PlacementAppException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kemele
 */
@Remote
public interface ChoiceBLRemote  extends BusinessRemote {
    public boolean save(Choice choice) throws PlacementAppException;
    public boolean update(Choice choice)  throws PlacementAppException;
    public boolean delete(int id);
    public Choice get(int id);
    public List<Choice> getAll();
    public List<Choice> getChoices(String idnum);
}
