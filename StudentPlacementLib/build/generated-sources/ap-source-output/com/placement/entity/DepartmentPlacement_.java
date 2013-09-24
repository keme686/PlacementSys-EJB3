package com.placement.entity;

import com.placement.entity.Department;
import com.placement.entity.Placement;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-09-13T10:40:52")
@StaticMetamodel(DepartmentPlacement.class)
public class DepartmentPlacement_ { 

    public static volatile SingularAttribute<DepartmentPlacement, Integer> id;
    public static volatile SingularAttribute<DepartmentPlacement, Placement> placementId;
    public static volatile SingularAttribute<DepartmentPlacement, Integer> capacity;
    public static volatile SingularAttribute<DepartmentPlacement, Integer> maxViolation;
    public static volatile SingularAttribute<DepartmentPlacement, Department> departmentId;

}