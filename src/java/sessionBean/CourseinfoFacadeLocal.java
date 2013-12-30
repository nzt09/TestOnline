/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Courseinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface CourseinfoFacadeLocal {

    void create(Courseinfo courseinfo);

    void edit(Courseinfo courseinfo);

    void remove(Courseinfo courseinfo);

    Courseinfo find(Object id);

    List<Courseinfo> findAll();

    List<Courseinfo> findRange(int[] range);

    int count();
    
    public List<Courseinfo> findByCourseId(int id);
    
}
