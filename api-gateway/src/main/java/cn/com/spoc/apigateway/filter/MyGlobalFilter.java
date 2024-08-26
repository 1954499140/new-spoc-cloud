package cn.com.spoc.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j // Lombok will create a logger automatically for the class with @Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {
    private static final String BEGIN_VISIT_TIME = "begin_visit_time";
    public static final int DEFAULT_ORDER = 0;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(BEGIN_VISIT_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long beginVisitTime = exchange.getAttribute(BEGIN_VISIT_TIME);
            if (beginVisitTime != null) {
                log.info("============= Begin Log =============");
                log.info("Access Interface Host: {}", exchange.getRequest().getURI().getHost());
                log.info("Access Interface Port: {}", exchange.getRequest().getURI().getPort());
                log.info("Access Interface Path: {}", exchange.getRequest().getURI().getPath());
                log.info("Access Interface Time: {}", (System.currentTimeMillis() - beginVisitTime) + "ms");
                log.info("=============  End Log  =============");
                System.out.println();
            }
        }));
    }

    @Override
    public int getOrder() {
        // Smaller the number Higher the priority
        return DEFAULT_ORDER;
    }
}
