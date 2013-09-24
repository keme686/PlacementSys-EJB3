/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Users;
import com.placement.exception.PlacementAppException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author kemele
 */
@Remote
public interface UsersBLRemote extends BusinessRemote {

    public boolean save(Users user) throws PlacementAppException;

    public boolean update(Users user) throws PlacementAppException;

    public boolean delete(String username);

    public Users get(String username);

    public List<Users> getAll();

    public boolean validate(String username, String password);

    public boolean updatePassword(Users user);

    public boolean generatePassword(Users user);
}
