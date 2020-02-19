import dao.Sql2oDepartmentsDao;
import dao.Sql2oNewsDao;
import models.Departments;
import models.News;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;

import org.sql2o.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class NewsTest {
    private static Connection connection;
    private static Sql2oNewsDao newsDao;
    private static Sql2oDepartmentsDao departmentsDao;
    News testNews = setupNews();

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/organization_api_test";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "admin");
        newsDao = new Sql2oNewsDao(sql2o);
        departmentsDao = new Sql2oDepartmentsDao(sql2o);
        connection = sql2o.open();
    }
    @After
    public void tearDown() throws Exception {
        newsDao.clearAll();
        System.out.println("Clearing Database");
    }

    @AfterClass
    public static void shutDown() throws Exception {
        connection.close();
        System.out.println("Connection closed");
    }

    @Test
    public  void fetchesNewsThatHasBeenProvided() {
        assertEquals("Introduction to AI", testNews.getNews_details());
    }

    @Test
    public void checksWhateverProvidedIsNewsDetail() {
        testNews.setNews_details("2378sdsj3823");
        assertNotEquals("Introduction of AI", testNews.getNews_details());
    }

    @Test
    public void testsTheSetterMethodIntheNewsClass_id() {
        testNews.setNews_id(3);
        assertEquals(3, testNews.getNews_id());
    }

    @Test
    public void addingNewsSetsNewsId_false() throws Exception {
        int id = testNews.getNews_id();
        newsDao.add(testNews);
        assertNotEquals(id, testNews.getNews_id());
    }

    @Test
    public void addedNewsReturnedFromGetAllMethod() throws  Exception{
        News testNews = setupNews();
        newsDao.add(testNews);
        assertEquals(1, newsDao.getAll().size());
    }

    @Test
    public void noNewsReturnsAnEmptyList() throws  Exception{
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void newsIsDeletedByIdUsingDeleteById(){
        News news = setupNews();
        newsDao.add(news);
        newsDao.deleteById(news.getNews_id());
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void addingNewsToDepartmentCompletesSuccessfully(){
        Departments testDepartments = setupDepartment();
        Departments otherDepartment = setupDepartmentalNews();

        departmentsDao.createDepartment(testDepartments);
        departmentsDao.createDepartment(otherDepartment);

        News testNews = setupNews();
        newsDao.add(testNews);

        newsDao.addNewsToDepartment(testNews, testDepartments);
        newsDao.addNewsToDepartment(testNews, otherDepartment);

        assertEquals(2, newsDao.getAllNewsForASpecificDepartment(testNews.getNews_id()).size());

    }

    public News setupNews() {
        return new News("Introduction to AI");
    }

    public Departments setupDepartment() {
        return new Departments("Finance", "Accounts and money");
    }
    public Departments setupDepartmentalNews() {
        return new Departments("Finance", "Accounts and money");
    }

}
