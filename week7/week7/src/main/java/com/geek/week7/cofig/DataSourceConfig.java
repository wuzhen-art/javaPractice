package com.geek.week7.cofig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 吴振
 * @since 2021/11/8 上午2:29
 */
public class DataSourceConfig extends AbstractRoutingDataSource {

    public static final String MASTER_KEY = "master";

    /**
     * 只要包含这个header的，视为只需要读
     */
    private final String READ_ONLY_HEADER = "X-READ-ONLY";

    private final ArrayList<String> readOnlyKeys = new ArrayList<>();

    private final AtomicInteger index = new AtomicInteger(0);

    private final Map<Object, Object> target = new HashMap<>();


    @Override
    protected Object determineCurrentLookupKey() {
        HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request == null || request.getHeader(READ_ONLY_HEADER) == null)
            return MASTER_KEY;
        return choose(readOnlyKeys);
    }

    protected String choose(ArrayList<String> readOnlyKeys) {
        int current = index.get();
        if (current >= readOnlyKeys.size())
            index.set(0);
        return readOnlyKeys.get(index.getAndIncrement());
    }

    public void registerWriteable(HikariDataSource dataSource) {
        target.put(MASTER_KEY, dataSource);
        super.setDefaultTargetDataSource(dataSource);
    }

    public void registerReadOnly(HikariDataSource dataSource) {
        readOnlyKeys.add(dataSource.getPoolName());
        target.put(dataSource.getPoolName(), dataSource);
    }

    public void apply() {
        super.setTargetDataSources(target);
    }
}
