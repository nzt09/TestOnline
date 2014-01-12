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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "SUBJECTINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subjectinfo.findAll", query = "SELECT s FROM Subjectinfo s"),
    @NamedQuery(name = "Subjectinfo.findById", query = "SELECT s FROM Subjectinfo s WHERE s.id = :id"),
    @NamedQuery(name = "Subjectinfo.findByName", query = "SELECT s FROM Subjectinfo s WHERE s.name = :name")})
public class Subjectinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "subjectinfo")
    private List<Courseinfo> courseinfoList;

    public Subjectinfo() {
    }

    public Subjectinfo(Integer id) {
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
    public List<Courseinfo> getCourseinfoList() {
        return courseinfoList;
    }

    public void setCourseinfoList(List<Courseinfo> courseinfoList) {
        this.courseinfoList = courseinfoList;
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
        if (!(object instanceof Subjectinfo)) {
            return false;
        }
        Subjectinfo other = (Subjectinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Subjectinfo[ id=" + id + " ]";
    }
    
}
