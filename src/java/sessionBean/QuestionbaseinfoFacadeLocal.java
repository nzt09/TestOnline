/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Questionbaseinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface QuestionbaseinfoFacadeLocal {

    void create(Questionbaseinfo questionbaseinfo);

    void edit(Questionbaseinfo questionbaseinfo);

    void remove(Questionbaseinfo questionbaseinfo);

    Questionbaseinfo find(Object id);

    List<Questionbaseinfo> findAll();

    List<Questionbaseinfo> findRange(int[] range);

    int count();
    
}
