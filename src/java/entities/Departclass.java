/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "DEPARTCLASS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departclass.findAll", query = "SELECT d FROM Departclass d"),
    @NamedQuery(name = "Departclass.findByStuno", query = "SELECT d FROM Departclass d WHERE d.stuno = :stuno"),
    @NamedQuery(name = "Departclass.findByName", query = "SELECT d FROM Departclass d WHERE d.name = :name"),
    @NamedQuery(name = "Departclass.findByPassword", query = "SELECT d FROM Departclass d WHERE d.password = :password"),
    @NamedQuery(name = "Departclass.findByClassid", query = "SELECT d FROM Departclass d WHERE d.classid = :classid"),
    @NamedQuery(name = "Departclass.findByDepartment", query = "SELECT d FROM Departclass d WHERE d.department = :department")})
public class Departclass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 20)
    @Column(name = "STUNO")
    @Id
    private String stuno;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Size(max = 20)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "CLASSID")
    private Integer classid;
    @Column(name = "DEPARTMENT")
    private Integer department;

    public Departclass() {
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
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
