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
public class WrappedKnowledge implements java.io.Serializable {

    private Knowledge kl;
    private List<WrappedKnowledge> KParents;
    private List<WrappedKnowledge> KChildren;
    public Knowledge getKl() {
        return kl;
    }

    public void setKl(Knowledge kl) {
        System.out.println("haoxiangshizheyang");
        this.kl = kl;
    }

    public WrappedKnowledge(Knowledge knowledge) {
        this.kl = knowledge;
    }

    public List<WrappedKnowledge> getKParents() {
        if (null == this.KParents) {
            this.KParents = new LinkedList<>();
        }
        return KParents;
    }

    public void setKParents(List<WrappedKnowledge> KParents) {
        this.KParents = KParents;
    }

    public List<WrappedKnowledge> getKChildren() {
        if (null == this.KChildren) {
            this.KChildren = new LinkedList<>();
        }
        return KChildren;

    }

    public void setKChildren(List<WrappedKnowledge> KChildren) {
        this.KChildren = KChildren;
    }

}
