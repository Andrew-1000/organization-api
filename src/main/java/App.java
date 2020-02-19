import com.google.gson.Gson;
import dao.Sql2oDepartmentsDao;
import dao.Sql2oNewsDao;
import exceptions.ApiException;
import models.Departments;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        Sql2oDepartmentsDao departmentsDao;
        Sql2oNewsDao newsDao;
        Connection connection;
        Gson gson = new Gson();

        String connString = "jdbc:postgresql://localhost/organization_api";
        Sql2o sql2o = new Sql2o(connString, "postgres", "admin");
        departmentsDao = new Sql2oDepartmentsDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        connection = sql2o.open();

        get("/departments", "application/json", (request, response) -> {
            System.out.println(departmentsDao.getAll());
            if (departmentsDao.getAll().size()>0) {
                return gson.toJson(departmentsDao.getAll());
            }
            else {
                return "{\"message\":\"Sorry, No Departments listed in database\"}";
            }

        });

        get("/news", "application/json", (request, response) -> {
            return gson.toJson(newsDao.getAll());
        });

        get("/departments/:department_id", "application/json", (request, response) -> {
            int department_id = Integer.parseInt(request.params("department_id"));
            Departments departmentsToFind = departmentsDao.findById(department_id);
            if (departmentsToFind == null) {
                throw new ApiException(404, String.format("No Department with the id: \"%s\" exists", request.params("department_id")));
            }
            return gson.toJson(departmentsToFind);
        });

        post("/departments/new", (request, response) -> {
            Departments departments = gson.fromJson(request.body(), Departments.class);
            departmentsDao.createDepartment(departments);
            response.status(201);
            return gson.toJson(departments);
        });

        post("/news/new", "application/json", (request, response) -> {
            News news = gson.fromJson(request.body(), News.class);
            newsDao.add(news);
            response.status(201);
            return gson.toJson(news);
        });

        post("/departments/:department_id/news/:news_id", "application/json", (request, response) -> {
            int department_id = Integer.parseInt(request.params("department_id"));
            int news_id = Integer.parseInt(request.params("news_id"));
            Departments departments = departmentsDao.findById(department_id);
            News news = newsDao.findById(news_id);

            if (departments != null && news != null) {
                newsDao.addNewsToDepartment(news, departments);
                response.status(201);
                return gson.toJson(String.format("Department '%s' and News '%s' have some kind of association", news.getNews_details(), departments.getDepartment_name()));

            }else {
                throw new ApiException(404, String.format("Department or News does not exist"));
            }
        });

        get("/departments/:department_id/news", "application/json", (request, response) -> {
            int department_id = Integer.parseInt(request.params("department_id"));
            Departments departments = departmentsDao.findById(department_id);
            if (departments == null) {
                throw new ApiException(404, String.format("No department of id: \"%s\" exists", request.params("department_id")));

            } else if (departmentsDao.getAllNewsByDepartment(department_id).size() == 0 ){
                return "{\"message\":\" Sorry, no news updates for this department\"}";
            } else {
                return gson.toJson(departmentsDao.getAllNewsByDepartment(department_id));
            }
        });
        //FILTERS
        exception(ApiException.class, (exception, request, response) -> {
            ApiException error = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", error.getStatusCode());
            jsonMap.put("errorMessage", error.getMessage());
            response.type("application/json");
            response.status(error.getStatusCode());
            response.body(gson.toJson(jsonMap));
        });

        after((request, response) -> {
            response.type("application/json");
        });


    }

}
