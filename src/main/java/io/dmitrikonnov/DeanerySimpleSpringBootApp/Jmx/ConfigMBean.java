package io.dmitrikonnov.DeanerySimpleSpringBootApp.Jmx;

import lombok.AllArgsConstructor;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;

@Component
@ManagedResource (description = "App Config Bean")
@AllArgsConstructor
public class ConfigMBean {
    private final ConfigService configService;

    @ManagedAttribute (description = "set reloadTime")
    public void setReloadTime (long time){
        configService.setReloadTime(time);
    }
    @ManagedAttribute (description = "get reloadTime")
    public long getReloadTime (){
        return configService.getReloadTime();
    }

    @ManagedOperation
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "key", description = "get config value")
    })
    public String getConfig(String key){
        return configService.getConfigByKey(key);
    }

}
