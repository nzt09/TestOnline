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

public class Publicfields implements java.io.Serializable{
    public static int ADMINISTRATOR_ROLE=1,TEACHER_ROLE=2,EDUTEACHER_ROLE=3,STUDENT_ROLE=4;
    private  List<SelectItem> itemlist;
    public  List<SelectItem> getTerms(){
         if(null==itemlist){
             List<SelectItem> itemlist = new ArrayList<>();
             for (int i = 2013; i < Calendar.getInstance().get(Calendar.YEAR)+1; i++) {
                 System.out.println("2334");
                 SelectItem tem=new SelectItem();
                 tem.setLabel(i+"-"+i+1+"-"+"1");
                 tem.setValue(i+"-"+i+1+"-"+"1");
                 itemlist.add(tem);  
                 System.out.println("324342");
                 System.out.println(tem.toString());
                 }
             }
         
         return itemlist;
    }
}
