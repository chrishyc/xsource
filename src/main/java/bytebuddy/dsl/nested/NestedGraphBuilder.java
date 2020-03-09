package bytebuddy.dsl.nested;

import bytebuddy.dsl.origin.Edge;
import bytebuddy.dsl.origin.Graph;

public class NestedGraphBuilder {
    public static Graph Graph(Edge... edges) {
        Graph g = new Graph();
        for (Edge e : edges) {
            g.addEdge(e);
            g.addVertice(e.getFromVertex());
            g.addVertice(e.getToVertex());
        }
        return g;
    }

}
