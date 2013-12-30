/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Rolesinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface RolesinfoFacadeLocal {

    void create(Rolesinfo rolesinfo);

    void edit(Rolesinfo rolesinfo);

    void remove(Rolesinfo rolesinfo);

    Rolesinfo find(Object id);

    List<Rolesinfo> findAll();

    List<Rolesinfo> findRange(int[] range);

    int count();
    
}
