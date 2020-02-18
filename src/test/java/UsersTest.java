import models.Users;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsersTest {
    Users testUsers = new Users();

    @Test
    public void users_instantiatesCorrectly_true(){
        assertEquals(true, testUsers instanceof Users);
    }
    @Test
    public void Users_instantiatesWithUserId_true() {
        assertEquals(1, testUsers.getUser_id());
    }
}
