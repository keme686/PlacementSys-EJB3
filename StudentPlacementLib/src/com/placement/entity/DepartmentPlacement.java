/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kemele
 */
@Entity(name = "department_placement")
@XmlRootElement
public class DepartmentPlacement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "max_violation")
    private Integer maxViolation;
//    @OneToMany(mappedBy = "programPlacementId", fetch = FetchType.LAZY)
//    private List<Choice> choiceList;
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Department departmentId;
    @JoinColumn(name = "placement_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Placement placementId;

    public DepartmentPlacement() {
    }

    public DepartmentPlacement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getMaxViolation() {
        return maxViolation;
    }

    public void setMaxViolation(Integer maxViolation) {
        this.maxViolation = maxViolation;
    }

//    @XmlTransient
//    public List<Choice> getChoiceList() {
//        return choiceList;
//    }
//
//    public void setChoiceList(List<Choice> choiceList) {
//        this.choiceList = choiceList;
//    }
    public Department getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Department departmentId) {
        this.departmentId = departmentId;
    }

    public Placement getPlacementId() {
        return placementId;
    }

    public void setPlacementId(Placement placementId) {
        this.placementId = placementId;
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
        if (!(object instanceof DepartmentPlacement)) {
            return false;
        }
        DepartmentPlacement other = (DepartmentPlacement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (departmentId != null) {
            return departmentId.getCode();
        } else {
            return "id=" + id;
        }
    }
}
