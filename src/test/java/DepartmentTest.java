import dao.Sql2oDepartmentsDao;
import models.Departments;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;


public class DepartmentTest {

    private static Connection connection;
    private static Sql2oDepartmentsDao depatmentDao;

    Departments departments = new Departments("Finance", "Deals with money matters");
    Departments testDepartment = setupDepartment();


    @BeforeClass
    public static void setUp() throws  Exception {
        String connString = "jdbc:postgresql://localhost:5432/organization_api_test";
        Sql2o sql2o = new Sql2o(connString, "postgres", "admin");
        depatmentDao = new Sql2oDepartmentsDao(sql2o);
        connection = sql2o.open();
     }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing Database");
        depatmentDao.clearAll();
    }
    @AfterClass
    public static void shutDown() throws Exception {
        connection.close();
        System.out.println("Connection closed");
        connection.setRollbackOnException(true);
    }
    @Test

    public void Departments_instantiatesCorrectly_true() {
        assertTrue(departments instanceof Departments);
    }

    @Test
    public void Departments_instantiatesWithDepartmentName_String(){
        assertEquals("Finance", departments.getDepartment_name());
    }

    @Test
    public void Department_instantiateswithDescription_String() {
        assertEquals("Deals with money matters", departments.getDescription());
    }
    @Test
    public void addingDepartmentSetsId() {
        Departments departments = setupDepartment();
        int id  = testDepartment.getDepartment_id();
        depatmentDao.createDepartment(departments);
        assertNotEquals(id, departments.getDepartment_id());
    }

    @Test
    public void addedDepartmentReturnedThroughGetAllMethod(){
        Departments testDepartment = setupDepartment();
        assertEquals(2, depatmentDao.getAll().size());
    }

    @Test
    public void noDepartmentReturnsEmptyList() {
        assertEquals(1, depatmentDao.getAll().size());
    }

    private Departments setupDepartment() {
        Departments departments = new Departments("Finance", "Deals with money matters");
        depatmentDao.createDepartment(departments);
        return departments;

    }
    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        depatmentDao.update(testDepartment.getDepartment_id(), "Finance", "Deals with money matters");
        Departments foundDepartment = depatmentDao.findById(testDepartment.getDepartment_id());
        assertEquals("Finance", foundDepartment.getDepartment_name());
        assertEquals("Deals with money matters", testDepartment.getDescription());

    }

}
