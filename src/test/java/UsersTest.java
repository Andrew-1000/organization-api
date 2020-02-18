import models.Users;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersTest {
    Users testUsers = new Users();

    @Test
    public void testUsers_instantiatesCorrectly_true() {
        assertEquals(true, testUsers instanceof Users);
    }
}
