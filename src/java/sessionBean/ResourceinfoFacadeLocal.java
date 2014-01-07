/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Resourceinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface ResourceinfoFacadeLocal {

    void create(Resourceinfo resourceinfo);

    void edit(Resourceinfo resourceinfo);

    void remove(Resourceinfo resourceinfo);

    Resourceinfo find(Object id);

    List<Resourceinfo> findAll();

    List<Resourceinfo> findRange(int[] range);

    int count();
    
    List<Resourceinfo> findByResourceinfo(String sql);
    
}
