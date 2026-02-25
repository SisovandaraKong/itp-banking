package co.istad.dara.customer_service.application.mapper;

import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.customer_service.application.dto.create.CreateCustomerRequest;
import co.istad.dara.customer_service.application.dto.query.CustomerResponse;
import co.istad.dara.customer_service.data.entity.CustomerEntity;
import co.istad.dara.customer_service.domain.command.CreateCustomerCommand;
import co.istad.dara.customer_service.domain.event.CustomerCreatedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CustomerApplicationMapper {

    CreateCustomerCommand createCustomerRequestToCreateCustomerCommand(CustomerId customerId, CreateCustomerRequest createCustomerRequest);

    @Mapping(source = "customerId.value", target = "customerId")
    CustomerEntity customerCreateEventToCustomerEntity(CustomerCreatedEvent customerCreatedEvent);

    CustomerResponse customerEntityToCustomerResponse(CustomerEntity customerEntity);
}
