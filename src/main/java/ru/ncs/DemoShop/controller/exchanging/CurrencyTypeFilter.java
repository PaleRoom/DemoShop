package ru.ncs.DemoShop.controller.exchanging;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@AllArgsConstructor
public class CurrencyTypeFilter extends OncePerRequestFilter {
    CurrencyTypeProvider currencyTypeProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String currencyType = request.getHeader("Currency");
        log.info("Header: {}", currencyType);

        if (!EnumUtils.isValidEnum(CurrencyEnum.class, currencyType)) {
            log.info("Header does not contain proper currency type ");
        } else {
            log.info("Currency type using: {}", currencyType);
            currencyTypeProvider.setCurrencyType(currencyType);
        }
        filterChain.doFilter(request, response);
    }
}
