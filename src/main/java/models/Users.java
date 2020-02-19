package models;

import java.util.Objects;

public class Users {
    private int user_id;
    private String user_name;
    private int department_id;
    private String department_name;
    private String position;

    public Users( String user_name, int department_id, String department_name, String position) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.department_id = department_id;
        this.department_name = department_name;
        this.position = position;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return user_id == users.user_id &&
                department_id == users.department_id &&
                Objects.equals(user_name, users.user_name) &&
                Objects.equals(department_name, users.department_name) &&
                Objects.equals(position, users.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, user_name, department_id, department_name, position);
    }
}
