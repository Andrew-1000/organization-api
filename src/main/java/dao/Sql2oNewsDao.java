package dao;

import interfaces.NewsDao;
import models.Departments;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oNewsDao implements NewsDao {

    private final Sql2o sql2o;

    public Sql2oNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(News news) {
        String sql = "INSERT INTO news (news_details) VALUES (:news_details)";
        try (Connection connection = sql2o.open()){
            connection.setRollbackOnException(false);
            int news_id = (int)connection.createQuery(sql, true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
            news.setNews_id(news_id);
            connection.setRollbackOnException(false);
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }


    }
    @Override
    public List<News> getAll() {
        try(Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM news")
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public void deleteById(int news_id) {
        String sql = "DELETE FROM news WHERE news_id = :news_id";
        try(Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("news_id", news_id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }
    @Override
    public void clearAll() {
        String sql = "DELETE from news";
        try(Connection connection = sql2o.open()) {
            connection.createQuery(sql).executeUpdate();

        }catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void addNewsToDepartment(News news, Departments departments) {
        String sql = "INSERT INTO departments_news (department_id, news_id) VALUES (:department_id, :news_id)";
        try(Connection connection = sql2o.open()) {
            connection.createQuery(sql)
                    .addParameter("department_id", departments.getDepartment_id())
                    .addParameter("news_id", news.getNews_id())
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }



    @Override
    public List<Departments> getAllNewsForASpecificDepartment(int news_id) {
        List<Departments> departments = new ArrayList();
        String joinsSqlQuery = "SELECT department_id FROM departments_news WHERE news_id = :news_id";


        try(Connection connection = sql2o.open()){
            List<Integer> allDepartmentIds = connection.createQuery(joinsSqlQuery)
                    .addParameter("news_id", news_id)
                    .executeAndFetch(Integer.class);
            for (Integer departmentId: allDepartmentIds) {
                String departmentSql = "SELECT * FROM departments WHERE department_id = :departmentId";
                departments.add(
                        connection.createQuery(departmentSql)
                        .addParameter("departmentId", departmentId)
                        .executeAndFetchFirst(Departments.class));
            }
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
        return departments;
    }

    @Override
    public News findById(int news_id) {
        try(Connection connection = sql2o.open()){
            return connection.createQuery("SELECT * FROM news WHERE news_id = :news")
                    .addParameter("news_id", news_id)
                    .executeAndFetchFirst(News.class);
        }
    }




}
