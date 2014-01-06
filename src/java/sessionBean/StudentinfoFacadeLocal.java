/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Studentinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface StudentinfoFacadeLocal {

    void create(Studentinfo studentinfo);

    void edit(Studentinfo studentinfo);

    void remove(Studentinfo studentinfo);

    Studentinfo find(Object id);

    List<Studentinfo> findAll();

    List<Studentinfo> findRange(int[] range);

    int count();
    
    Studentinfo findByIdPassword(String userId, String password);
    
    List<Studentinfo> findById(int id);
    
     
}
