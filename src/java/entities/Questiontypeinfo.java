/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "QUESTIONTYPEINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questiontypeinfo.findAll", query = "SELECT q FROM Questiontypeinfo q"),
    @NamedQuery(name = "Questiontypeinfo.findById", query = "SELECT q FROM Questiontypeinfo q WHERE q.id = :id"),
    @NamedQuery(name = "Questiontypeinfo.findByName", query = "SELECT q FROM Questiontypeinfo q WHERE q.name = :name")})
public class Questiontypeinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "questiontypeinfo")
    private List<Questionsinfo> questionsinfoList;
    @OneToMany(mappedBy = "questiontypeinfo")
    private List<Questionbaseinfo> questionbaseinfoList;

    public Questiontypeinfo() {
    }

    public Questiontypeinfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Questionsinfo> getQuestionsinfoList() {
        return questionsinfoList;
    }

    public void setQuestionsinfoList(List<Questionsinfo> questionsinfoList) {
        this.questionsinfoList = questionsinfoList;
    }

    @XmlTransient
    public List<Questionbaseinfo> getQuestionbaseinfoList() {
        return questionbaseinfoList;
    }

    public void setQuestionbaseinfoList(List<Questionbaseinfo> questionbaseinfoList) {
        this.questionbaseinfoList = questionbaseinfoList;
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
        if (!(object instanceof Questiontypeinfo)) {
            return false;
        }
        Questiontypeinfo other = (Questiontypeinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Questiontypeinfo[ id=" + id + " ]";
    }
    
}
