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
 * @author Administrator
 */
@Entity
@Table(name = "QUESTIONKNOWLEDGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questionknowledge.findAll", query = "SELECT q FROM Questionknowledge q"),
    @NamedQuery(name = "Questionknowledge.findById", query = "SELECT q FROM Questionknowledge q WHERE q.id = :id"),
    @NamedQuery(name = "Questionknowledge.findByContent", query = "SELECT q FROM Questionknowledge q WHERE q.content = :content"),
    @NamedQuery(name = "Questionknowledge.findByScore", query = "SELECT q FROM Questionknowledge q WHERE q.score = :score"),
    @NamedQuery(name = "Questionknowledge.findByDifficulty", query = "SELECT q FROM Questionknowledge q WHERE q.difficulty = :difficulty"),
    @NamedQuery(name = "Questionknowledge.findBySelections", query = "SELECT q FROM Questionknowledge q WHERE q.selections = :selections"),
    @NamedQuery(name = "Questionknowledge.findByQuestiontype", query = "SELECT q FROM Questionknowledge q WHERE q.questiontype = :questiontype"),
    @NamedQuery(name = "Questionknowledge.findByAnswer", query = "SELECT q FROM Questionknowledge q WHERE q.answer = :answer"),
    @NamedQuery(name = "Questionknowledge.findByAveragetime", query = "SELECT q FROM Questionknowledge q WHERE q.averagetime = :averagetime"),
    @NamedQuery(name = "Questionknowledge.findByCode", query = "SELECT q FROM Questionknowledge q WHERE q.code = :code"),
    @NamedQuery(name = "Questionknowledge.findByInsequence", query = "SELECT q FROM Questionknowledge q WHERE q.insequence = :insequence"),
    @NamedQuery(name = "Questionknowledge.findByCount", query = "SELECT q FROM Questionknowledge q WHERE q.count = :count"),
    @NamedQuery(name = "Questionknowledge.findByTestcasepara", query = "SELECT q FROM Questionknowledge q WHERE q.testcasepara = :testcasepara"),
    @NamedQuery(name = "Questionknowledge.findByTestcaseresult", query = "SELECT q FROM Questionknowledge q WHERE q.testcaseresult = :testcaseresult"),
    @NamedQuery(name = "Questionknowledge.findByAnalysis", query = "SELECT q FROM Questionknowledge q WHERE q.analysis = :analysis"),
    @NamedQuery(name = "Questionknowledge.findByKnowid", query = "SELECT q FROM Questionknowledge q WHERE q.knowid = :knowid")})
public class Questionknowledge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @Id
    private int id;
    @Size(max = 30000)
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "SCORE")
    private Integer score;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DIFFICULTY")
    private Double difficulty;
    @Size(max = 5000)
    @Column(name = "SELECTIONS")
    private String selections;
    @Column(name = "QUESTIONTYPE")
    private Integer questiontype;
    @Size(max = 5000)
    @Column(name = "ANSWER")
    private String answer;
    @Column(name = "AVERAGETIME")
    private Double averagetime;
    @Size(max = 30000)
    @Column(name = "CODE")
    private String code;
    @Column(name = "INSEQUENCE")
    private Integer insequence;
    @Column(name = "COUNT")
    private Double count;
    @Size(max = 2000)
    @Column(name = "TESTCASEPARA")
    private String testcasepara;
    @Size(max = 1000)
    @Column(name = "TESTCASERESULT")
    private String testcaseresult;
    @Size(max = 1000)
    @Column(name = "ANALYSIS")
    private String analysis;
    @Column(name = "KNOWID")
    private Integer knowid;

    public Questionknowledge() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    public String getSelections() {
        return selections;
    }

    public void setSelections(String selections) {
        this.selections = selections;
    }

    public Integer getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(Integer questiontype) {
        this.questiontype = questiontype;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getAveragetime() {
        return averagetime;
    }

    public void setAveragetime(Double averagetime) {
        this.averagetime = averagetime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getInsequence() {
        return insequence;
    }

    public void setInsequence(Integer insequence) {
        this.insequence = insequence;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public String getTestcasepara() {
        return testcasepara;
    }

    public void setTestcasepara(String testcasepara) {
        this.testcasepara = testcasepara;
    }

    public String getTestcaseresult() {
        return testcaseresult;
    }

    public void setTestcaseresult(String testcaseresult) {
        this.testcaseresult = testcaseresult;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Integer getKnowid() {
        return knowid;
    }

    public void setKnowid(Integer knowid) {
        this.knowid = knowid;
    }
    
}
