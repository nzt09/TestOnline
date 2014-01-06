/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Classinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface ClassinfoFacadeLocal {

    void create(Classinfo classinfo);

    void edit(Classinfo classinfo);

    void remove(Classinfo classinfo);

    Classinfo find(Object id);

    List<Classinfo> findAll();

    List<Classinfo> findRange(int[] range);

    int count();
    
    List<Classinfo> findByMajor(int id);
    
     List<Classinfo> findById(int classId);
}
