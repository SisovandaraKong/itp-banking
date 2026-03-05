package co.istad.dara.customer_service.application;

import co.istad.dara.common.dto.PageResponse;
import co.istad.dara.customer_service.application.dto.query.CustomerResponse;
import co.istad.dara.customer_service.application.projection.GetCustomerByIdQuery;
import co.istad.dara.customer_service.application.projection.GetCustomerQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final QueryGateway queryGateway;
    private final EventStore eventStore;

    @Override
    public List<?> getCustomerHistory(UUID customerId) {
        return eventStore.readEvents(customerId.toString()).asStream()
                .map(Message::getPayload)
                .toList();
    }

    @Override
    public PageResponse getAllCustomers(int pageNumber, int pageSize) {

        GetCustomerQuery getCustomerQuery = new GetCustomerQuery();
        getCustomerQuery.setPageNumber(pageNumber);
        getCustomerQuery.setPageSize(pageSize);

        return queryGateway
                .query(getCustomerQuery, ResponseTypes.instanceOf(PageResponse.class))
                .join();
    }

    @Override
    public CustomerResponse getCustomerById(UUID customerId) {
        GetCustomerByIdQuery getCustomerByIdQuery = new GetCustomerByIdQuery(customerId);
        return queryGateway.query(getCustomerByIdQuery, ResponseTypes.instanceOf(CustomerResponse.class)).join();
    }
}
