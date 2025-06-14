package cn.ccc212.eduagent.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "edu-agent.log.autolog")
@Data
public class LogProperties {
    private boolean enable;
}
