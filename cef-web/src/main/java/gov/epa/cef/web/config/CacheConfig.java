package gov.epa.cef.web.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.HazelcastInstanceFactory;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

@EnableCaching
@Configuration
public class CacheConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Environment environment;

    @Autowired
    CacheConfig(Environment environment) {

        this.environment = environment;
    }

    @Bean
    CacheManager cacheManager() {

        return new HazelcastCacheManager(hazelcastInstance());
    }

    @Bean(destroyMethod = "shutdown")
    HazelcastInstance hazelcastInstance() {

//        Config config = new Config()
//            .setInstanceName("cef-hazelcast-cache")
//            .addMapConfig(new MapConfig()
//                .setName("default")
//                .setMaxSizeConfig(new MaxSizeConfig(2000, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
//                .setEvictionPolicy(EvictionPolicy.LRU)
//                .setTimeToLiveSeconds(120));

        String configLocation = this.environment.getRequiredProperty("spring.hazelcast.config");
        logger.info("Using cache configuration: {}", configLocation);

        Config config = null;
        try {

            config = new XmlConfigBuilder(ResourceUtils.getURL(configLocation)).build();

        } catch (IOException e) {

            throw new IllegalStateException(e);
        }

        HazelcastInstance result =
            HazelcastInstanceFactory.getHazelcastInstance(config.getInstanceName());
        if (result == null) {
            result = HazelcastInstanceFactory.newHazelcastInstance(config);
        }

        return result;
    }
}
