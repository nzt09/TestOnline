/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.Questionsinfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Administrator
 */
@Local
public interface QuestionsinfoFacadeLocal {

    void create(Questionsinfo questionsinfo);

    void edit(Questionsinfo questionsinfo);

    void remove(Questionsinfo questionsinfo);

    Questionsinfo find(Object id);

    List<Questionsinfo> findAll();

    List<Questionsinfo> findRange(int[] range);

    List<Questionsinfo> findConstrainRange(int typeid, int knowid, int[] range);

    int count();

}
