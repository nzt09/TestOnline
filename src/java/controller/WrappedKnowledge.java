/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Knowledge;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Administrator
 */
@Named
@SessionScoped
public class WrappedKnowledge implements java.io.Serializable{

    private Knowledge cc;

    public Knowledge getCc() {
        return cc;
    }
public WrappedKnowledge(){
    
}
    public void setCc(Knowledge cc) {
        this.cc = cc;
    }
    private List<WrappedKnowledge> CCParents;
    private List<WrappedKnowledge> CCChildren;

    public WrappedKnowledge(Knowledge cc1) {
        cc=cc1;
    }

    public List<WrappedKnowledge> getCCParents() {
            this.CCChildren = new LinkedList<>();
            this.CCParents = new LinkedList<>();
            
            if (cc.getKnowledgeList().size() > 0) {
                for (Knowledge cc1 : cc.getKnowledgeList()) {
                    if (cc1.getKnowledgeList().size() > 0) {
                        this.CCParents.add(new WrappedKnowledge(cc1));
                    } else {
                        this.CCChildren.add(new WrappedKnowledge(cc1));
                    }
                }
            }
        return this.CCParents;
    }

    public List<WrappedKnowledge> getCCChildren() {
            this.CCChildren = new LinkedList<>();
            this.CCParents = new LinkedList<>();
            if (cc.getKnowledgeList().size() > 0) {
                for (Knowledge cc1 : cc.getKnowledgeList()) {
                    if (cc1.getKnowledgeList().size() > 0) {
                        this.CCParents.add(new WrappedKnowledge(cc1));
                    } else {
                        this.CCChildren.add(new WrappedKnowledge(cc1));
                    }
                } 
        }
        return this.CCChildren;

    }
}
