package java.spi;

import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Sample {
    @Test
    public void testSPI() {
        ServiceLoader<Search> s = ServiceLoader.load(Search.class);
        Iterator<Search> searchList = s.iterator();
        while (searchList.hasNext()) {
            Search curSearch = searchList.next();
            System.out.println(curSearch.findDriver("test"));
        }
    }
}
