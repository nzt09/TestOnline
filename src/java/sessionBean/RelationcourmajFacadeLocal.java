/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Relationcourmaj;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface RelationcourmajFacadeLocal {

    void create(Relationcourmaj relationcourmaj);

    void edit(Relationcourmaj relationcourmaj);

    void remove(Relationcourmaj relationcourmaj);

    Relationcourmaj find(Object id);

    List<Relationcourmaj> findAll();

    List<Relationcourmaj> findRange(int[] range);

    int count();
    
}
