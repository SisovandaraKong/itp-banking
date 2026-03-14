package co.istad.dara.customer_service.application.projection;

import co.istad.dara.common.dto.PageResponse;
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
    public PageResponse handle(GetCustomerQuery getCustomerQuery) {

        Pageable pageable = PageRequest.of(
                getCustomerQuery.getPageNumber(),
                getCustomerQuery.getPageSize(),
                Sort.by(Sort.Direction.ASC, "dob")
        );

        Page<CustomerEntity> customerEntityPage = customerRepository.findAll(pageable);

        List<CustomerResponse> customers = customerEntityPage.getContent().stream()
                .map(customerApplicationMapper::customerEntityToCustomerResponse)
                .toList();

        return new PageResponse(
                customers,
                customerEntityPage.getTotalPages(),
                customerEntityPage.getSize(),
                customerEntityPage.getTotalElements(),
                customerEntityPage.getNumber()
        );
    }
    @QueryHandler
    public CustomerResponse handle(GetCustomerByIdQuery getCustomerByIdQuery){
        CustomerEntity customer = customerRepository.findById(getCustomerByIdQuery.customerId()).orElse(null);
        return customerApplicationMapper.customerEntityToCustomerResponse(customer);
    }

}
