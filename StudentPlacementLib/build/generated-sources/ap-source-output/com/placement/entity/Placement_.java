package com.placement.entity;

import com.placement.entity.College;
import com.placement.entity.DepartmentPlacement;
import com.placement.entity.Rule;
import com.placement.entity.Student;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-09-13T10:40:52")
@StaticMetamodel(Placement.class)
public class Placement_ { 

    public static volatile SingularAttribute<Placement, Integer> id;
    public static volatile SingularAttribute<Placement, College> collegeId;
    public static volatile SingularAttribute<Placement, String> name;
    public static volatile SingularAttribute<Placement, Rule> ruleId;
    public static volatile SingularAttribute<Placement, String> code;
    public static volatile ListAttribute<Placement, DepartmentPlacement> departmentPlacementList;
    public static volatile SingularAttribute<Placement, String> type;
    public static volatile ListAttribute<Placement, Student> studentList;
    public static volatile SingularAttribute<Placement, String> academicYear;

}