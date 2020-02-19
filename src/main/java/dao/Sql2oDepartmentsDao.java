package dao;

import interfaces.DepartmentsDao;
import models.Departments;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentsDao implements DepartmentsDao {
    private Sql2o sql2o;

    public Sql2oDepartmentsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Departments> getAll() {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Departments.class);
        }

    }

    @Override
    public Departments findById(int department_id) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("SELECT * FROM departments WHERE department_id = :department_id")
                    .addParameter("department_id", department_id)
                    .executeAndFetchFirst(Departments.class);
        }
    }

    @Override
    public void createDepartment(Departments departments) {
        String sql = "INSERT INTO departments (department_name, description) VALUES (:department_name, :description)";
        try (Connection connection = sql2o.open()) {
            int department_id = (int) connection.createQuery(sql, true)
                    .bind(departments)
                    .executeUpdate()
                    .getKey();
            connection.setRollbackOnException(false);
            departments.setDepartment_id(department_id);

        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void update(int department_id, String department_name, String description) {
        String sql = "UPDATE departments SET(department_name, description) = (:department_name, :description) WHERE department_id = :department_id";
        try (Connection connection = sql2o.open()) {
            connection.setRollbackOnException(false);
            connection.createQuery(sql)
                    .addParameter("department_name", department_name)
                    .addParameter("description", description)
                    .executeUpdate();
            connection.setRollbackOnException(false);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int department_id) {
        String sql = "DELETE FROM departments WHERE department_id = :department_id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                    .addParameter("department_id", department_id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departments";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(sql).executeUpdate();

        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public List<News> getAllNewsByDepartment(int department_id) {
        List<News> news = new ArrayList();
        String joinQuery = "SELECT news_id FROM departments_news WHERE department_id = :department_id";

        try (Connection connection = sql2o.open()) {
            List<Integer> allNews = connection.createQuery(joinQuery)
                    .addParameter("department_id", department_id)
                    .executeAndFetch(Integer.class);
            for (Integer newsId : allNews) {
                String newsQuery = "SELECT * FROM news WHERE news_id = :news_id";
                news.add(
                        connection.createQuery(newsQuery)
                                .addParameter("news_id", newsId)
                                .executeAndFetchFirst(News.class));
            }

        }catch (Sql2oException ex) {
            System.out.println(ex);
    }
        return news;
}
}
