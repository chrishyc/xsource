package java.spi.impl;

import java.spi.Search;

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
