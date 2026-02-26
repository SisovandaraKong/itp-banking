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

    // QueryGateway is the Axon Framework component used to dispatch queries
    // to their respective query handlers in the application
    private final QueryGateway queryGateway;

    @Override
    public Page<CustomerResponse> getAllCustomers(int pageNumber, int pageSize) {

        GetCustomerQuery getCustomerQuery = new GetCustomerQuery();
        getCustomerQuery.setPageNumber(pageNumber);
        getCustomerQuery.setPageSize(pageSize);

        // - ResponseTypes.multipleInstancesOf() tells Axon we expect a List<CustomerResponse>
        // - .join() blocks and waits for the async result to complete
        List<CustomerResponse> customers = queryGateway
                .query(getCustomerQuery, ResponseTypes.multipleInstancesOf(CustomerResponse.class))
                .join();

        // Wrap the result into a Spring PageImpl object:
        // - customers       → the actual data for the current page
        // - PageRequest.of() → holds pageNumber and pageSize metadata
        // - customers.size() → used as total elements count
        return new PageImpl<>(customers, PageRequest.of(pageNumber, pageSize), customers.size());
    }
}
