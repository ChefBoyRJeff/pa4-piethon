package edu.psu.ist.analyzer;

import edu.psu.ist.analyzer.utils.Digraph;
import edu.psu.ist.analyzer.utils.Options;

public class BasicMainCli {

    public static void main(String[] args) {
        // just a place to generate a sample graph (most of the time
        // when you interact with PieAnalyzer it will be through the tests --
        // see examples in the /test/analyzer directory)
        System.out.println("PIETHON analyzer:");

        // this triple-quoted string is called a "Text block", a newer multiline
        // form of string added to java (they are used in the jUnit tests as well)
        String sampleScript = """
                def g() : Void is
                end
                
                def f() : Void is
                    g();
                end
                
                def m() : Int32 is
                    f();
                    g();
                    return 0;
                end
                """;
        // we're in CLI mode, we don't want to run silent
        var analyzer = new PieAnalyzer()
                .setOptions(new Options(false))
                .setScriptCode("exampleScript", sampleScript);

        // obtain the graph from the sample script
        Digraph<ProcNode> g = analyzer.buildGraph();

        // write the graph png to the root directory of the pa4 project
        analyzer.exportGraph(g, "test-graph.png",
                "example script graph");
    }
}
