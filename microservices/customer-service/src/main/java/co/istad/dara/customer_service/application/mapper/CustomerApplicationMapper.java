package co.istad.dara.customer_service.application.mapper;

import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.common.dto.PageResponse;
import co.istad.dara.customer_service.application.dto.create.CreateCustomerRequest;
import co.istad.dara.customer_service.application.dto.query.CustomerResponse;
import co.istad.dara.customer_service.data.entity.CustomerEntity;
import co.istad.dara.customer_service.domain.command.CreateCustomerCommand;
import co.istad.dara.customer_service.domain.event.CustomerCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CustomerApplicationMapper {

    CreateCustomerCommand createCustomerRequestToCreateCustomerCommand(CustomerId customerId, CreateCustomerRequest createCustomerRequest);

    @Mapping(source = "customerId.value", target = "customerId")
    CustomerEntity customerCreateEventToCustomerEntity(CustomerCreatedEvent customerCreatedEvent);

    CustomerResponse customerEntityToCustomerResponse(CustomerEntity customerEntity);

    default PageResponse toPageResponse(Page<CustomerEntity> customerEntityPage) {

        List<CustomerResponse> data = customerEntityPage
                .map(this::customerEntityToCustomerResponse)
                .toList();

        return PageResponse.builder()
                .data(data)
                .pageNumber(customerEntityPage.getNumber())
                .pageSize(customerEntityPage.getSize())
                .totalElements(customerEntityPage.getNumberOfElements())
                .totalPages(customerEntityPage.getTotalPages())
                .build();
    }

}
