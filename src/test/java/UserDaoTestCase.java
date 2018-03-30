import com.kaishengit.dao.UserDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoTestCase {

    @Test
    public void testSave(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        UserDao userDao1 = (UserDao) applicationContext.getBean("userDao");
        userDao.save();
    }

    @Test
    public void test(){
        try {
            UserDao userDao = (UserDao) Class.forName("com.kaishengit.dao.UserDao").newInstance();
            userDao.save();
            UserDao userDao1 = new UserDao();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


}
