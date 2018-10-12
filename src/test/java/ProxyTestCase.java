import com.kaishengit.proxy.Mp3;
import com.kaishengit.proxy.Mp3AdProxy;
import com.kaishengit.proxy.Mp3LogProxy;
import com.kaishengit.proxy.Player;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTestCase {

    String string = new Player()  {
        public int play(String username) {
            System.out.println("正在播放："+username);
            return 1;
        }

        public void stop() {
            System.out.println("结束播放：");
        }

        public void run(){
            System.out.println("跑一跑");
        }

        public String name="小红";
    }.name;

    Player player1 = new Player() {
        public int play(String username) {
            System.out.println("正在播放"+username);
            return 1;
        }

        public void stop() {
            System.out.println("停止播放");
        }
    };

    Mp3 mp4 = new Mp3(){
        @Override
        public int play(String username) {
            System.out.println("正在播放："+username+"这首歌");
            return 1;
        }
        public void run(){
            System.out.println("跑一跑");
        }
    };

    Mp3 mp5 = new Mp3();
   // ProxyTestCase proxyTestCase = new ProxyTestCase();
    TestCase testCase = new TestCase();
    @Test
    public void test1(){
       /* player1.play("光辉岁月");
        player1.stop();
        mp4.play("七里香");*/
        System.out.println(player1.getClass().getName());
        System.out.println(mp4.getClass().getPackage());
        System.out.println(mp5.getClass().getPackage());
        System.out.println(testCase.getClass());


    }

    @Test
    public void testPlayer(){
        Mp3 mp3 = new Mp3();
        Mp3LogProxy mp3LogProxy = new Mp3LogProxy(mp3);
        Mp3AdProxy mp3AdProxy = new Mp3AdProxy(mp3LogProxy);
        mp3AdProxy.play("七里香");
        mp3AdProxy.stop();
    }

    @Test
    public void testProxy(){
        final Mp3 mp3 = new Mp3();
        ClassLoader classLoader = mp3.getClass().getClassLoader();
        Class[] interfaces = mp3.getClass().getInterfaces();
        Player player = (Player) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                System.out.println(methodName+"代理开始");
                Object result = method.invoke(mp3,args);
                System.out.println(methodName+"代理结束");
                return result;
            }
        });
        player.play("七里香");
        player.stop();

    }

    @Test
    public void testCglib(){

        Mp3 mp3 = new Mp3();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(mp3.getClass());
        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("前置");
                Object result = methodProxy.invokeSuper(o,objects);
                System.out.println("后置");


                return result;
            }
        });
        Mp3 mp31 = (Mp3) enhancer.create();
        mp31.play("东风破");
        mp31.stop();


    }

    @Test
    public void testAop(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop.xml");
        Player player = (Player) applicationContext.getBean("mp3");
        Object object = applicationContext.getBean("mp3");
        player.play("七里香");
        /*UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        userDao.save();*/
        System.out.println(player.getClass().getName());
        System.out.println(object.getClass().getName());
    }

}

/*内部类*/
 class TestCase {

}
