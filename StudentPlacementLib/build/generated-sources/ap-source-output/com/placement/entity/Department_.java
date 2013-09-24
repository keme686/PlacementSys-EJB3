package com.placement.entity;

import com.placement.entity.College;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-09-13T10:40:52")
@StaticMetamodel(Department.class)
public class Department_ { 

    public static volatile SingularAttribute<Department, Integer> id;
    public static volatile SingularAttribute<Department, College> collegeId;
    public static volatile SingularAttribute<Department, String> address;
    public static volatile SingularAttribute<Department, String> description;
    public static volatile SingularAttribute<Department, String> name;
    public static volatile SingularAttribute<Department, String> code;

}