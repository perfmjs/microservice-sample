package starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.concurrent.TimeUnit;

/**
 * 
 *JRebel6 Agent版本的VM启动参数如下：
 -noverify
 -javaagent:/Users/tony/myfiles/jrebel/xrebel_running/xrebel.jar
 -agentpath:/Users/tony/myfiles/jrebel/jrebel_running/lib/libjrebel64.dylib
 -Drebel.base=/Users/tony/myfiles/jrebel/jrebel_base
 -Drebel.properties=/Users/tony/myfiles/jrebel/jrebel_config/jrebel.properties
 -Drebel.remoting_plugin=false
 -Drebel.dirs=/Users/tony/IdeaProjects/microservice-sample/target/classes
 * @project youyue-service
 * @author tony.shen
 * @date 2015年7月7日 下午16:38:51
 */
@Configuration
@ImportResource("spring-dal.xml")
@PropertySource("/application.properties")
@EnableAutoConfiguration
@ComponentScan({"com.*", "example.*"})
public class Application {
	
    public static void main(String[] args) {
    	SpringApplication.run(Application.class);
    }
    
    @Bean
    InternalResourceViewResolver internalResourceViewResolver () {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
    @Bean
    EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(8888);
        factory.setSessionTimeout(30, TimeUnit.MINUTES);
        return factory;
    }

}
