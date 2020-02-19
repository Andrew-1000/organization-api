package interfaces;

import models.Departments;
import models.News;

import java.util.List;

public interface DepartmentsDao {

    List<Departments> getAll();

    Departments findById(int department_id);
    void createDepartment(Departments departments);

    //update
    void update(int department_id, String department_name, String description);
    void deleteById(int department_id);
    void clearAll();

    List<News> getAllNewsByDepartment(int department_id);
}
