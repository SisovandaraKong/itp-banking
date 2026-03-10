package co.istad.dara.customer_service.application;

import co.istad.dara.common.dto.PageResponse;
import co.istad.dara.customer_service.application.dto.query.CustomerResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerQueryService {

        List<?> getCustomerHistory(UUID customerId);

        PageResponse getAllCustomers(int pageNumber, int pageSize);

        CustomerResponse getCustomerById(UUID customerId);
}                                  