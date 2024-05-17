package inclui.modelo_translate_properties;

import innui.modelos.errors.Oks;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author emilio
 */
public class Modelo_translate_propertiesTest {
    
    public Modelo_translate_propertiesTest() {
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
     * Test of run method, of class Modelo_translate_properties.
     */
    @Ignore
    public void testRun() throws Exception {
        System.out.println("run");
        Oks ok = null;
        Object[] extras_array = null;
        Modelo_translate_properties instance = new Modelo_translate_properties();
        boolean expResult = false;
        boolean result = instance.run(ok, extras_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of init method, of class Modelo_translate_properties.
     */
    @Ignore
    public void testInit() throws Exception {
        System.out.println("init");
        Oks ok = null;
        Object[] extra_array = null;
        Modelo_translate_properties instance = new Modelo_translate_properties();
        boolean expResult = false;
        boolean result = instance.init(ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of terminate method, of class Modelo_translate_properties.
     */
    @Ignore
    public void testTerminate() throws Exception {
        System.out.println("terminate");
        Oks ok = null;
        Object[] extra_array = null;
        Modelo_translate_properties instance = new Modelo_translate_properties();
        boolean expResult = false;
        boolean result = instance.terminate(ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write_help method, of class Modelo_translate_properties.
     */
    @Ignore
    public void testWrite_help() throws Exception {
        System.out.println("write_help");
        Oks ok = null;
        Object[] extra_array = null;
        Modelo_translate_properties instance = new Modelo_translate_properties();
        boolean expResult = false;
        boolean result = instance.write_help(ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create_options method, of class Modelo_translate_properties.
     */
    @Ignore
    public void testCreate_options() throws Exception {
        System.out.println("create_options");
        Oks ok = null;
        Object[] extra_array = null;
        Modelo_translate_properties instance = new Modelo_translate_properties();
        boolean expResult = false;
        boolean result = instance.create_options(ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of process_options_received method, of class Modelo_translate_properties.
     */
    @Ignore
    public void testProcess_options_received() throws Exception {
        System.out.println("process_options_received");
        String[] args = null;
        Oks ok = null;
        Object[] extra_array = null;
        Modelo_translate_properties instance = new Modelo_translate_properties();
        boolean expResult = false;
        boolean result = instance.process_options_received(args, ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of process_option method, of class Modelo_translate_properties.
     */
    @Ignore
    public void testProcess_option() throws Exception {
        System.out.println("process_option");
        CommandLine commandLine = null;
        Option option = null;
        Oks ok = null;
        Object[] extra_array = null;
        Modelo_translate_properties instance = new Modelo_translate_properties();
        boolean expResult = false;
        boolean result = instance.process_option(commandLine, option, ok, extra_array);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Modelo_translate_properties.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = {
            "-fj",
            "/home/emilio/swag/java/modelo_translate_properties/src/main/java/inclui/modelo_translate_properties/"
        };
        Modelo_translate_properties.main(args);
    }
    
}
