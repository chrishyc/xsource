package bytebuddy.dsl.nested;

import bytebuddy.dsl.origin.Edge;
import bytebuddy.dsl.origin.Vertex;

public class NestedEdgeBuilder {

    public static Edge edge(Vertex from, Vertex to,
                            Double weight) {
        return new Edge(from, to, weight);
    }

    public static Double weight(Double value) {
        return value;
    }
}
