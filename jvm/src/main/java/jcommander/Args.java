package jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Args {
    
    @Parameter(names = {"-log", "-verbose"}, description = "Level of verbosity")
    private Integer verbose = 1;
    
    @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
    private String groups;
    
    @Parameter(names = "-debug", description = "Debug mode")
    private boolean debug = false;
    
    public static void main(String[] args) {
        Args arg = new Args();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(arg)
                .build();
        jCommander.parse(args);
        jCommander.usage();
        System.out.println(arg);
    }
}
