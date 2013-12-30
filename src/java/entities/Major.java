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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "MAJOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Major.findAll", query = "SELECT m FROM Major m"),
    @NamedQuery(name = "Major.findById", query = "SELECT m FROM Major m WHERE m.id = :id"),
    @NamedQuery(name = "Major.findByName", query = "SELECT m FROM Major m WHERE m.name = :name")})
public class Major implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "major")
    private List<Classinfo> classinfoList;
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "ID")
    @ManyToOne
    private Department department;
    @OneToMany(mappedBy = "major")
    private List<Relationcourmaj> relationcourmajList;
    @OneToMany(mappedBy = "major")
    private List<Courseinfo> courseinfoList;

    public Major() {
    }

    public Major(Integer id) {
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
    public List<Classinfo> getClassinfoList() {
        return classinfoList;
    }

    public void setClassinfoList(List<Classinfo> classinfoList) {
        this.classinfoList = classinfoList;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @XmlTransient
    public List<Relationcourmaj> getRelationcourmajList() {
        return relationcourmajList;
    }

    public void setRelationcourmajList(List<Relationcourmaj> relationcourmajList) {
        this.relationcourmajList = relationcourmajList;
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
        if (!(object instanceof Major)) {
            return false;
        }
        Major other = (Major) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Major[ id=" + id + " ]";
    }
    
}
