package Rest.host;

import ZkService.ZkServiceImpl;
import ch.qos.logback.classic.Level;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class}
)
public class main {

    //public static ZkServiceImpl zkservice; Shai remove

    public static final void main(String[] args) {

        BasicConfigurator.configure();
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);

        SpringApplication.run(main.class, args);
    }
}
