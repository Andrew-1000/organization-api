package interfaces;

import models.Departments;
import models.News;

import java.util.List;

public interface NewsDao {

    //Create
    void add(News news);
    void addNewsToDepartment(News news, Departments departments);


    //Read
    List<News> getAll();
    List<Departments> getAllNewsForASpecificDepartment(int department_id);
    News findById(int news_id);


    //Delete
    void deleteById(int news_id);
    void clearAll();
}
