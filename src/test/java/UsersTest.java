import models.Users;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersTest {
    Users testUsers = new Users ( 1, "Alex", 1, "Finance", "Accountant");

    @Test
    public void testUsers_instantiatesCorrectly_true() {
        assertEquals(true, testUsers instanceof Users );
    }

    @Test
    public void Users_instantiatesWithUserId_int() {
       assertEquals(1, testUsers.getUser_id());
    }
    @Test
    public void Users_instantiatesWithUsername_String() {
        assertEquals("Alex", testUsers.getUser_name());
    }
    @Test
    public void Users_instantiatesWithDepartmentName_String() {
        assertEquals( "Finance", testUsers.getDepartment_name());
    }

}
