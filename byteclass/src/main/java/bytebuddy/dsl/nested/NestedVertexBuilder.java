package bytebuddy.dsl.nested;

import bytebuddy.dsl.origin.Vertex;

public class NestedVertexBuilder {
    public static Vertex from(String lbl) {
        return new Vertex(lbl);
    }

    public static Vertex to(String lbl) {
        return new Vertex(lbl);
    }
}
