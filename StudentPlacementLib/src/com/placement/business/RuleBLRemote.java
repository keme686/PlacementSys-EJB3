/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Rule;
import com.placement.exception.PlacementAppException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kemele
 */
@Remote
public interface RuleBLRemote extends BusinessRemote{
    public boolean save(Rule rule)  throws PlacementAppException;
    public boolean update(Rule rule) throws PlacementAppException;
    public boolean delete(int id);
    public Rule get(int id);
    public List<Rule> getAll();
}
