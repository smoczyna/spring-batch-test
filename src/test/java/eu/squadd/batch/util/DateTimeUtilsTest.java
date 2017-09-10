/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.util;

import java.time.ZonedDateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author smoczyna
 */
public class DateTimeUtilsTest {
    
    /**
     * Test of toStringWithoutText method, of class DateTimeUtils.
     */
    @Test
    public void testToStringWithoutText() {
        System.out.println("toStringWithoutText");
        ZonedDateTime zdt = ZonedDateTime.now();
        String expResult = "";
        String result = DateTimeUtils.toStringWithoutText(zdt);
        System.out.println("Time Zone Date from Java Time: " + result);
        assertTrue(result!=null);
        //assertEquals(expResult, result);
        
    }
    
}
