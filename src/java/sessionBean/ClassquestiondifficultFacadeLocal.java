/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Classquestiondifficult;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface ClassquestiondifficultFacadeLocal {

    void create(Classquestiondifficult classquestiondifficult);

    void edit(Classquestiondifficult classquestiondifficult);

    void remove(Classquestiondifficult classquestiondifficult);

    Classquestiondifficult find(Object id);

    List<Classquestiondifficult> findAll();

    List<Classquestiondifficult> findRange(int[] range);

    int count();
    
}
