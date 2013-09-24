/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.utility;

import com.placement.business.BusinessRemote;
import com.placement.business.ChoiceBLRemote;
import com.placement.business.CollegeBLRemote;
import com.placement.business.DepartmentBLRemote;
import com.placement.business.DepartmentPlacementBLRemote;
import com.placement.business.PlacementBLRemote;
import com.placement.business.RuleBLRemote;
import com.placement.business.StudentBLRemote;
import com.placement.business.StudentPlacementBLRemote;
import com.placement.business.UsersBLRemote;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author kemele
 */
public class EJBWrapperFactory {

    public RuleBLRemote getRuleBL() {
        String name = "RuleBL";
        String remote = "RuleBLRemote";
        BusinessRemote bean = getBusiness(name, remote);
        if (bean == null) {
            return null;
        }
        return (RuleBLRemote) bean;
    }

    public  PlacementBLRemote getPlacementBL() {
        String name = "PlacementBL";
        String remote = "PlacementBLRemote";
        BusinessRemote bean = getBusiness(name, remote);
        if (bean == null) {
            return null;
        }
        return (PlacementBLRemote) bean;
    }

    public StudentBLRemote getStudentBL() {
        String name = "StudentBL";
        String remote = "StudentBLRemote";
        BusinessRemote bean = getBusiness(name, remote);
        if (bean == null) {
            return null;
        }
        return (StudentBLRemote) bean;
    }

    public ChoiceBLRemote getChoiceBL() {
        String name = "ChoiceBL";
        String remote = "ChoiceBLRemote";
        BusinessRemote bean = getBusiness(name, remote);
        if (bean == null) {
            return null;
        }
        return (ChoiceBLRemote) bean;
    }

    public DepartmentBLRemote getDepartmentBL() {
        String name = "DepartmentBL";
        String remote = "DepartmentBLRemote";
        BusinessRemote bean = getBusiness(name, remote);
        if (bean == null) {
            return null;
        }
        return (DepartmentBLRemote) bean;
    }

    public CollegeBLRemote getCollegeBL() {
        String name = "CollegeBL";
        String remote = "CollegeBLRemote";
        BusinessRemote bean = getBusiness(name, remote);
        if (bean == null) {
            return null;
        }
        return (CollegeBLRemote) bean;
    }

    public UsersBLRemote getUsersBL() {
        String name = "UsersBL";
        String remote = "UsersBLRemote";
        BusinessRemote bean = getBusiness(name, remote);
        if (bean == null) {
            return null;
        }
        return (UsersBLRemote) bean;
    }

    public DepartmentPlacementBLRemote getDepartmentPlacementBL() {
        String name = "DepartmentPlacementBL";
        String remote = "DepartmentPlacementBLRemote";
        BusinessRemote bean = getBusiness(name, remote);
        if (bean == null) {
            return null;
        }
        return (DepartmentPlacementBLRemote) bean;
    }

    public StudentPlacementBLRemote getStudentPlacementBL() {
        String name = "StudentPlacementBL";
        String remote = "StudentPlacementBLRemote";//?stateful";
        BusinessRemote bean = getBusiness(name, remote);
        if (bean == null) {
            return null;
        }
        return (StudentPlacementBLRemote) bean;
    }

    public BusinessRemote getBusiness(String className, String remoteInterface) {
        Context context=null;
//        Properties properties = new Properties();
//        properties.put(Context.PROVIDER_URL, "remote://localhost:4447");
        // properties.put(Context.SECURITY_PRINCIPAL, "appuser");
        // properties.put(Context.SECURITY_CREDENTIALS, "!@#123asd"); 
//        properties.put("jboss.naming.client.ejb.context", true);
//        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            
            // context = new InitialContext(properties);            
            context = getContext();
            BusinessRemote bean = null;
            String appName = "StudentPlacementEAR";
            String moduleName = "StudentPlacementEAR-ejb";
            String distinctName = "";
            String beanName = className;
            final String interfaceName = "com.placement.business." + remoteInterface;            
            String path = appName + "/" + moduleName + "/"
                    + distinctName + "/" + beanName + "!" + interfaceName;
            bean = (BusinessRemote) context.lookup(path);                 
            return bean;
        } catch (NamingException ex) {
            ex.printStackTrace();
            Logger.getLogger(EJBWrapperFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private InitialContext getContext() throws NamingException {
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProps.put(Context.PROVIDER_URL, "remote://localhost:4447");
        jndiProps.put("jboss.naming.client.ejb.context", true);
        return new InitialContext(jndiProps);
    }
}
