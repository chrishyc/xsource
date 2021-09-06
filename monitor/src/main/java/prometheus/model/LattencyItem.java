package prometheus.model;

import lombok.Data;

import java.util.Map;

@Data
public class LattencyItem {
    private String metricName;
    private Long value;
    private Map<String, String> labels;
    
}
