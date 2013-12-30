/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Questiontypeinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface QuestiontypeinfoFacadeLocal {

    void create(Questiontypeinfo questiontypeinfo);

    void edit(Questiontypeinfo questiontypeinfo);

    void remove(Questiontypeinfo questiontypeinfo);

    Questiontypeinfo find(Object id);

    List<Questiontypeinfo> findAll();

    List<Questiontypeinfo> findRange(int[] range);

    int count();
    
}
