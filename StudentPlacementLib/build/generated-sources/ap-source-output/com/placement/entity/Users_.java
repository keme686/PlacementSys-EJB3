package com.placement.entity;

import com.placement.entity.Student;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-09-13T10:40:52")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> username;
    public static volatile SingularAttribute<Users, Date> lastLogin;
    public static volatile SingularAttribute<Users, Date> lastModified;
    public static volatile SingularAttribute<Users, Integer> status;
    public static volatile SingularAttribute<Users, Integer> role;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, Date> createdDate;
    public static volatile ListAttribute<Users, Student> studentList;

}