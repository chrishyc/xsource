package myjava.spi.impl;

import myjava.spi.Search;

/**
 * MySqlSearch被称为provider,提供者
 * {@link java.util.ServiceLoader#providers }
 *
 */
public class MySqlSearch implements Search {
    @Override
    public String findDriver(String keyWord) {
        return "@bigger";
    }
}
