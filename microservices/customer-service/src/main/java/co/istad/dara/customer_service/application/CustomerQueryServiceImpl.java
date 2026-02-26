package co.istad.dara.customer_service.application;

import co.istad.dara.customer_service.application.dto.query.CustomerResponse;
import co.istad.dara.customer_service.application.projection.GetCustomerQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final QueryGateway queryGateway;

    @Override
    public Page<CustomerResponse> getAllCustomers(int pageNumber, int pageSize) {

        GetCustomerQuery getCustomerQuery = new GetCustomerQuery();
        getCustomerQuery.setPageNumber(pageNumber);
        getCustomerQuery.setPageSize(pageSize);

        List<CustomerResponse> customers = queryGateway
                .query(getCustomerQuery, ResponseTypes.multipleInstancesOf(CustomerResponse.class))
                .join();

        return new PageImpl<>(customers, PageRequest.of(pageNumber, pageSize), customers.size());
    }
}
