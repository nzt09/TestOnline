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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hgs
 */
@Entity
@Table(name = "CLASSQUESTIONDIFFICULT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classquestiondifficult.findAll", query = "SELECT c FROM Classquestiondifficult c"),
    @NamedQuery(name = "Classquestiondifficult.findById", query = "SELECT c FROM Classquestiondifficult c WHERE c.id = :id"),
    @NamedQuery(name = "Classquestiondifficult.findByDifficulty", query = "SELECT c FROM Classquestiondifficult c WHERE c.difficulty = :difficulty"),
    @NamedQuery(name = "Classquestiondifficult.findByAveragetime", query = "SELECT c FROM Classquestiondifficult c WHERE c.averagetime = :averagetime")})
public class Classquestiondifficult implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DIFFICULTY")
    private Double difficulty;
    @Column(name = "AVERAGETIME")
    private Double averagetime;
    @JoinColumn(name = "QUESTIONID", referencedColumnName = "ID")
    @ManyToOne
    private Questionsinfo questionsinfo;
    @JoinColumn(name = "CLASSID", referencedColumnName = "ID")
    @ManyToOne
    private Classinfo classinfo;

    public Classquestiondifficult() {
    }

    public Classquestiondifficult(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    public Double getAveragetime() {
        return averagetime;
    }

    public void setAveragetime(Double averagetime) {
        this.averagetime = averagetime;
    }

    public Questionsinfo getQuestionsinfo() {
        return questionsinfo;
    }

    public void setQuestionsinfo(Questionsinfo questionsinfo) {
        this.questionsinfo = questionsinfo;
    }

    public Classinfo getClassinfo() {
        return classinfo;
    }

    public void setClassinfo(Classinfo classinfo) {
        this.classinfo = classinfo;
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
        if (!(object instanceof Classquestiondifficult)) {
            return false;
        }
        Classquestiondifficult other = (Classquestiondifficult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Classquestiondifficult[ id=" + id + " ]";
    }
    
}
