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
@Table(name = "ROLESINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rolesinfo.findAll", query = "SELECT r FROM Rolesinfo r"),
    @NamedQuery(name = "Rolesinfo.findById", query = "SELECT r FROM Rolesinfo r WHERE r.id = :id"),
    @NamedQuery(name = "Rolesinfo.findByName", query = "SELECT r FROM Rolesinfo r WHERE r.name = :name"),
    @NamedQuery(name = "Rolesinfo.findByCanseeall", query = "SELECT r FROM Rolesinfo r WHERE r.canseeall = :canseeall"),
    @NamedQuery(name = "Rolesinfo.findByPrivilege", query = "SELECT r FROM Rolesinfo r WHERE r.privilege = :privilege"),
    @NamedQuery(name = "Rolesinfo.findByResouceids", query = "SELECT r FROM Rolesinfo r WHERE r.resouceids = :resouceids")})
public class Rolesinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 20)
    @Column(name = "NAME")
    private String name;
    @Column(name = "CANSEEALL")
    private Integer canseeall;
    @Column(name = "PRIVILEGE")
    private Integer privilege;
    @Size(max = 500)
    @Column(name = "RESOUCEIDS")
    private String resouceids;
    @OneToMany(mappedBy = "rolesinfo")
    private List<Teacher> teacherList;

    public Rolesinfo() {
    }

    public Rolesinfo(Integer id) {
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

    public Integer getCanseeall() {
        return canseeall;
    }

    public void setCanseeall(Integer canseeall) {
        this.canseeall = canseeall;
    }

    public Integer getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

    public String getResouceids() {
        return resouceids;
    }

    public void setResouceids(String resouceids) {
        this.resouceids = resouceids;
    }

    @XmlTransient
    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
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
        if (!(object instanceof Rolesinfo)) {
            return false;
        }
        Rolesinfo other = (Rolesinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Rolesinfo[ id=" + id + " ]";
    }
    
}
