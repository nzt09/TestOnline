/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Question2knowledge;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface Question2knowledgeFacadeLocal {

    void create(Question2knowledge question2knowledge);

    void edit(Question2knowledge question2knowledge);

    void remove(Question2knowledge question2knowledge);

    Question2knowledge find(Object id);

    List<Question2knowledge> findAll();

    List<Question2knowledge> findRange(int[] range);

    int count();
    
    Question2knowledge findByQusetionId(int id);
    
}
