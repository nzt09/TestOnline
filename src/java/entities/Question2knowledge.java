/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hgs
 */
@Entity
@Table(name = "QUESTION2KNOWLEDGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question2knowledge.findAll", query = "SELECT q FROM Question2knowledge q"),
    @NamedQuery(name = "Question2knowledge.findById", query = "SELECT q FROM Question2knowledge q WHERE q.id = :id")})
public class Question2knowledge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "QUESTIONID", referencedColumnName = "ID")
    @ManyToOne
    private Questionsinfo questionsinfo;
    @JoinColumn(name = "KNOWID", referencedColumnName = "ID")
    @ManyToOne
    private Knowledge knowledge;

    public Question2knowledge() {
    }

    public Question2knowledge(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Questionsinfo getQuestionsinfo() {
        return questionsinfo;
    }

    public void setQuestionsinfo(Questionsinfo questionsinfo) {
        this.questionsinfo = questionsinfo;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question2knowledge)) {
            return false;
        }
        Question2knowledge other = (Question2knowledge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Question2knowledge[ id=" + id + " ]";
    }
    
}
