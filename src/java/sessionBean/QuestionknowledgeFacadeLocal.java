/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Questionknowledge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface QuestionknowledgeFacadeLocal {

    void create(Questionknowledge questionknowledge);

    void edit(Questionknowledge questionknowledge);

    void remove(Questionknowledge questionknowledge);

    Questionknowledge find(Object id);

    List<Questionknowledge> findAll();

    int count();
      
    List<Questionknowledge> findRange(int typeid, int knowid);
    
}
