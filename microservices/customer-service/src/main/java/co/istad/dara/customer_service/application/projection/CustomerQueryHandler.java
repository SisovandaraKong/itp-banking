package co.istad.dara.customer_service.application.projection;

import co.istad.dara.customer_service.application.dto.query.CustomerResponse;
import co.istad.dara.customer_service.application.mapper.CustomerApplicationMapper;
import co.istad.dara.customer_service.data.entity.CustomerEntity;
import co.istad.dara.customer_service.data.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerQueryHandler {

    private final CustomerRepository customerRepository;
    private final CustomerApplicationMapper customerApplicationMapper;

    @QueryHandler
    public Page<CustomerResponse> handle(GetCustomerQuery getCustomerQuery){

        Pageable pageable = PageRequest.of(
                getCustomerQuery.getPageNumber(),
                getCustomerQuery.getPageSize(),
                Sort.by(Sort.Direction.ASC, "dob")
        );

        Page<CustomerEntity> customers = customerRepository.findAll(pageable);

        return customers
                .map(customerApplicationMapper::customerEntityToCustomerResponse);
    }
}
