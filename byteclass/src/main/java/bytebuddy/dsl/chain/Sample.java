package bytebuddy.dsl.chain;

import static bytebuddy.dsl.chain.GraphBuilder.Graph;

public class Sample {
    public static void main(String[] args) {

        Graph()
                .edge()
                .from("a")
                .to("b")
                .weight(40.0)
                .edge()
                .from("b")
                .to("c")
                .weight(20.0)
                .edge()
                .from("d")
                .to("e")
                .weight(50.5)
                .printGraph();

        Graph()
                .edge()
                .from("w")
                .to("y")
                .weight(23.0)
                .edge()
                .from("d")
                .to("e")
                .weight(34.5)
                .edge()
                .from("e")
                .to("y")
                .weight(50.5)
                .printGraph();

    }
}
