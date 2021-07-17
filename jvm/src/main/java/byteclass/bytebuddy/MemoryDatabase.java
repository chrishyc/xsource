package byteclass.bytebuddy;

import java.util.Arrays;
import java.util.List;

public class MemoryDatabase {
    public List load(String info) {
        return Arrays.asList(info + ": foo", info + ": bar");
    }

    public List unload(String info) {
        return Arrays.asList(info + ": foo", info + ": bar");
    }
}
