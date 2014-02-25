/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sessionBean;

import entities.Subjectinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface SubjectinfoFacadeLocal {

    void create(Subjectinfo subjectinfo);

    void edit(Subjectinfo subjectinfo);

    void remove(Subjectinfo subjectinfo);

    Subjectinfo find(Object id);

    List<Subjectinfo> findAll();

    List<Subjectinfo> findRange(int[] range);

    int count();
    
}
