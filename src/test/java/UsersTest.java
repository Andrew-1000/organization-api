import models.Departments;
import models.Users;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersTest {
    Departments departments = new Departments("Finance", "Deals with money matters");
    Users testUsers = new Users ( "Alex", 1, "Finance", "Accountant");

    @Test
    public void testUsers_instantiatesCorrectly_true() {
        assertEquals(true, testUsers instanceof Users );
    }
    
    @Test
    public void Users_instantiatesWithUsername_String() {
        assertEquals("Alex", testUsers.getUser_name());
    }


}
