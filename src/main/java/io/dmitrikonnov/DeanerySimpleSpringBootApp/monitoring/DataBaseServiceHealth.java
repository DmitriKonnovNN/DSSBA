package io.dmitrikonnov.DeanerySimpleSpringBootApp.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DataBaseServiceHealth implements HealthIndicator {

    private final String DATABASE_SERVICE = "DatabaseService";
    @Override
    public Health health() {
        if (isDataBaseHealthOperable()){
            return Health.up().withDetail(DATABASE_SERVICE, "Service is running").build(); // use withDetails() to exposure more than one key-value-pair
        }
        return Health.down().withDetail(DATABASE_SERVICE, "Service is not available").build();
    }

    private boolean isDataBaseHealthOperable(){
        // some logic
        return true;
    }
}
