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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hgs
 */
@Entity
@Table(name = "DEPARTCLASS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departclass.findAll", query = "SELECT d FROM Departclass d"),
    @NamedQuery(name = "Departclass.findById", query = "SELECT d FROM Departclass d WHERE d.id = :id"),
    @NamedQuery(name = "Departclass.findByName", query = "SELECT d FROM Departclass d WHERE d.name = :name"),
    @NamedQuery(name = "Departclass.findByPassword", query = "SELECT d FROM Departclass d WHERE d.password = :password"),
    @NamedQuery(name = "Departclass.findByMajorid", query = "SELECT d FROM Departclass d WHERE d.majorid = :majorid"),
    @NamedQuery(name = "Departclass.findByClassname", query = "SELECT d FROM Departclass d WHERE d.classname = :classname"),
    @NamedQuery(name = "Departclass.findByStuno", query = "SELECT d FROM Departclass d WHERE d.stuno = :stuno"),
    @NamedQuery(name = "Departclass.findByClassid", query = "SELECT d FROM Departclass d WHERE d.classid = :classid"),
    @NamedQuery(name = "Departclass.findByDepartment", query = "SELECT d FROM Departclass d WHERE d.department = :department")})
public class Departclass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @Id
    private int id;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 20)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAJORID")
    private int majorid;
    @Size(max = 20)
    @Column(name = "CLASSNAME")
    private String classname;
    @Size(max = 20)
    @Column(name = "STUNO")
    private String stuno;
    @Column(name = "CLASSID")
    private Integer classid;
    @Column(name = "DEPARTMENT")
    private Integer department;

    public Departclass() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMajorid() {
        return majorid;
    }

    public void setMajorid(int majorid) {
        this.majorid = majorid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }
    
}
