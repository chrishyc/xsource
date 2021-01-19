/**
 * sharemer.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package nio.reactor.mainsub;

import java.io.IOException;

/**
 * @author sunqinwen
 * @version \: Starter.java,v 0.1 2019-03-28 15:47
 */
public class Starter {

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(2333)).start();
    }

}
