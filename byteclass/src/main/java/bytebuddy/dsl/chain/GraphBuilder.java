package bytebuddy.dsl.chain;

import bytebuddy.dsl.origin.Graph;

public class GraphBuilder {
    private Graph graph;

    public GraphBuilder() {
        graph = new Graph();
    }

    //Start the Graph DSL with this method.
    public static GraphBuilder Graph(){
        return new GraphBuilder();
    }

    //Start the edge building with this method.
    public EdgeBuilder edge(){
        EdgeBuilder builder = new EdgeBuilder(this);

        getGraph().addEdge(builder.edge);

        return builder;
    }

    public Graph getGraph() {
        return graph;
    }

    public void printGraph(){
        Graph.printGraph(graph);
    }
}
