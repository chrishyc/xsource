package prometheus.model;

import lombok.Data;

import java.util.List;

@Data
public class LattencySample {
    public List<LattencyItem> data;
}
