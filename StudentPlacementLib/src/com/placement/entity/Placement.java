/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author kemele
 */
@Entity(name = "placement")
@XmlRootElement
public class Placement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "code")
    private String code;
    @Size(max = 145)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "academic_year")
    private String academicYear;
    @Size(max = 45)
    @Column(name = "type")
    private String type;
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Rule ruleId;
    @JoinColumn(name = "college_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private College collegeId;
    @OneToMany(mappedBy = "placementId", fetch = FetchType.LAZY)
    private List<Student> studentList;
//    @OneToMany(mappedBy = "placementId", fetch = FetchType.LAZY)
//    private List<Choice> choiceList;
    @OneToMany(mappedBy = "placementId", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<DepartmentPlacement> departmentPlacementList;

    public Placement() {
    }

    public Placement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Rule getRuleId() {
        return ruleId;
    }

    public void setRuleId(Rule ruleId) {
        this.ruleId = ruleId;
    }

    public College getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(College collegeId) {
        this.collegeId = collegeId;
    }

    @XmlTransient
    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
//
//    @XmlTransient
//    public List<Choice> getChoiceList() {
//        return choiceList;
//    }
//
//    public void setChoiceList(List<Choice> choiceList) {
//        this.choiceList = choiceList;
//    }

    @XmlTransient
    public List<DepartmentPlacement> getDepartmentPlacementList() {
        return departmentPlacementList;
    }

    public void setDepartmentPlacementList(List<DepartmentPlacement> departmentPlacementList) {
        this.departmentPlacementList = departmentPlacementList;
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
        if (!(object instanceof Placement)) {
            return false;
        }
        Placement other = (Placement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.placement.entity.Placement[ id=" + id + " ]";
    }
    
}
