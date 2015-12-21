package ua.com.friendslocator;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.friendslocator.dao.GroupDao;
import ua.com.friendslocator.dao.GroupMemberDao;
import ua.com.friendslocator.dao.LocationDao;
import ua.com.friendslocator.entity.Group;
import ua.com.friendslocator.entity.GroupMember;
import ua.com.friendslocator.entity.Location;

import javax.sql.DataSource;

@Configuration
public class FriendsLocatorConfig {
    @Bean
    public StringGroupIdGenerator groupIdCalculator() {
        return new StringGroupIdGenerator(groupDao());
    }

    @Bean
    public EbeanServer ebeanServer() {
        return EbeanServerFactory.create(serverConfig());
    }

    @Bean
    public ServerConfig serverConfig() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setDataSource(dataSource());
        serverConfig.setName("main");
        serverConfig.addClass(Group.class);
        serverConfig.addClass(GroupMember.class);
        serverConfig.addClass(Location.class);
        return  serverConfig;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/friendslocator");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setDefaultAutoCommit(false);
        return dataSource;
    }

    @Bean
    public GroupDao groupDao() {
        return new GroupDao(ebeanServer());
    }

    @Bean
    public GroupMemberDao groupMemberDao() {
        return new GroupMemberDao(ebeanServer());
    }

    @Bean
    public LocationDao locationDao() {
        return new LocationDao(ebeanServer());
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return new DateTimeProvider();
    }
}
