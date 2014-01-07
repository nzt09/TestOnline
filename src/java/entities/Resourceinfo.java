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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "RESOURCEINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resourceinfo.findAll", query = "SELECT r FROM Resourceinfo r"),
    @NamedQuery(name = "Resourceinfo.findById", query = "SELECT r FROM Resourceinfo r WHERE r.id = :id"),
    @NamedQuery(name = "Resourceinfo.findByName", query = "SELECT r FROM Resourceinfo r WHERE r.name = :name"),
    @NamedQuery(name = "Resourceinfo.findByRefas", query = "SELECT r FROM Resourceinfo r WHERE r.refas = :refas"),
    @NamedQuery(name = "Resourceinfo.findByComment", query = "SELECT r FROM Resourceinfo r WHERE r.comment = :comment"),
    @NamedQuery(name = "Resourceinfo.findByRecommendrole", query = "SELECT r FROM Resourceinfo r WHERE r.recommendrole = :recommendrole"),
    @NamedQuery(name = "Resourceinfo.findByMenuorder", query = "SELECT r FROM Resourceinfo r WHERE r.menuorder = :menuorder")})
public class Resourceinfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "NAME")
    private String name;
    @Size(max = 100)
    @Column(name = "REFAS")
    private String refas;
    @Size(max = 500)
    @Column(name = "COMMENT")
    private String comment;
    @Size(max = 20)
    @Column(name = "RECOMMENDROLE")
    private String recommendrole;
    @Column(name = "MENUORDER")
    private Integer menuorder;
    @OneToMany(mappedBy = "resourceinfo")
    private List<Resourceinfo> resourceinfoList;
    @JoinColumn(name = "PARENTID", referencedColumnName = "ID")
    @ManyToOne
    private Resourceinfo resourceinfo;

    public Resourceinfo() {
    }

    public Resourceinfo(Integer id) {
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

    public String getRefas() {
        return refas;
    }

    public void setRefas(String refas) {
        this.refas = refas;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRecommendrole() {
        return recommendrole;
    }

    public void setRecommendrole(String recommendrole) {
        this.recommendrole = recommendrole;
    }

    public Integer getMenuorder() {
        return menuorder;
    }

    public void setMenuorder(Integer menuorder) {
        this.menuorder = menuorder;
    }

    @XmlTransient
    public List<Resourceinfo> getResourceinfoList() {
        return resourceinfoList;
    }

    public void setResourceinfoList(List<Resourceinfo> resourceinfoList) {
        this.resourceinfoList = resourceinfoList;
    }

    public Resourceinfo getResourceinfo() {
        return resourceinfo;
    }

    public void setResourceinfo(Resourceinfo resourceinfo) {
        this.resourceinfo = resourceinfo;
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
        if (!(object instanceof Resourceinfo)) {
            return false;
        }
        Resourceinfo other = (Resourceinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Resourceinfo[ id=" + id + " ]";
    }
    
}
