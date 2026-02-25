package co.istad.dara.customer_service.application;

import co.istad.dara.customer_service.application.dto.query.CustomerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerQueryService {

    Page<CustomerResponse> getAllCustomers(int pageNumber, int pageSize);
}
