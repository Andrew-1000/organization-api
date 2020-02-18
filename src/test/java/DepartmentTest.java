import models.Departments;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DepartmentTest {
    Departments departments = new Departments(1, "Finance", "Deals with money matters");

    @Test

    public void Departments_instantiatesCorrectly_true() {
        assertEquals(true, departments instanceof Departments);
    }

    @Test

    public void Departments_instantiatesWithDepartmentId_true() {
        assertEquals(1, departments.getDepartment_id());
    }

    @Test
    public void Departments_instantiatesWithDepartmentName_String(){
        assertEquals("Finance", departments.getDepartment_name());
    }

    @Test
    public void Department_instantiateswithDescription_String() {
        assertEquals("Deals with money matters", departments.getDescription());
    }

}
