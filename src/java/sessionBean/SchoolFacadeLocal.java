/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.School;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface SchoolFacadeLocal {

    void create(School school);

    void edit(School school);

    void remove(School school);

    School find(Object id);

    List<School> findAll();

    List<School> findRange(int[] range);

    int count();
    
}
