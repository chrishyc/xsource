package bytebuddy.dsl.nested;


import bytebuddy.dsl.origin.Graph;

import static bytebuddy.dsl.nested.NestedEdgeBuilder.edge;
import static bytebuddy.dsl.nested.NestedEdgeBuilder.weight;
import static bytebuddy.dsl.nested.NestedGraphBuilder.Graph;
import static bytebuddy.dsl.nested.NestedVertexBuilder.from;
import static bytebuddy.dsl.nested.NestedVertexBuilder.to;

public class Sample {
    public static void main(String[] args) {
        Graph.printGraph(
                Graph(
                        edge(from("a"), to("b"), weight(23.4)),
                        edge(from("b"), to("c"), weight(56.7)),
                        edge(from("d"), to("e"), weight(10.4)),
                        edge(from("e"), to("a"), weight(45.9))
                )
        );
    }
}
