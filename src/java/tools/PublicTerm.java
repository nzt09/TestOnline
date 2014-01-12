/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Administrator
 */
@Named
@ApplicationScoped
public class PublicTerm {

    private List<SelectItem> itemlist;

    public List<SelectItem> getTerms() {
        if (null == itemlist) {
            itemlist = new ArrayList<SelectItem>();
            for (int i = 2013; i < Calendar.getInstance().get(Calendar.YEAR) + 1; i++) {
                SelectItem tem = new SelectItem();
                tem.setLabel(i + "-" + (i + 1) + "-" + "1");
                tem.setValue(i + "-" + (i + 1) + "-" + "1");
                itemlist.add(tem);
            }
        }

        return itemlist;
    }
}
