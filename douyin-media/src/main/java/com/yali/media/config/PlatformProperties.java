package com.yali.media.config;

import com.yali.media.enums.Platform;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "dy.platform")
public class PlatformProperties {
    private Platform file;
    private Platform media;
    private Platform image;
}
