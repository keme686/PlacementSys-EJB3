/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.placement.business;

import com.placement.entity.Rule;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kemele
 */
public class RuleBLTest {
    
    public RuleBLTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of save method, of class RuleBL.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        Rule rule = null;
        
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        RuleBLRemote instance = (RuleBLRemote)container.getContext().lookup("java:global/classes/RuleBL");
        boolean expResult = false;
        boolean result = instance.save(rule);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class RuleBL.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Rule rule = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        RuleBLRemote instance = (RuleBLRemote)container.getContext().lookup("java:global/classes/RuleBL");
        boolean expResult = false;
        boolean result = instance.update(rule);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class RuleBL.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        int id = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        RuleBLRemote instance = (RuleBLRemote)container.getContext().lookup("java:global/classes/RuleBL");
        boolean expResult = false;
        boolean result = instance.delete(id);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class RuleBL.
     */
    @Test
    public void testGet() throws Exception {
        System.out.println("get");
        int id = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        RuleBLRemote instance = (RuleBLRemote)container.getContext().lookup("java:global/classes/RuleBL");
        Rule expResult = null;
        Rule result = instance.get(id);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAll method, of class RuleBL.
     */
    @Test
    public void testGetAll() throws Exception {
        System.out.println("getAll");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        RuleBLRemote instance = (RuleBLRemote)container.getContext().lookup("java:global/classes/RuleBL");
        List expResult = null;
        List result = instance.getAll();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}