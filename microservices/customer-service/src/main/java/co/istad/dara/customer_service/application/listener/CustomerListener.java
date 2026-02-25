package co.istad.dara.customer_service.application.listener;

import co.istad.dara.customer_service.application.mapper.CustomerApplicationMapper;
import co.istad.dara.customer_service.data.entity.CustomerEntity;
import co.istad.dara.customer_service.data.entity.CustomerSegmentEntity;
import co.istad.dara.customer_service.data.repository.CustomerRepository;
import co.istad.dara.customer_service.data.repository.CustomerSegmentRepository;
import co.istad.dara.customer_service.domain.event.CustomerCreatedEvent;
import co.istad.dara.customer_service.domain.event.CustomerPhoneNumberChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerListener {

    private final CustomerRepository customerRepository;
    private final CustomerSegmentRepository customerSegmentRepository;
    private final CustomerApplicationMapper customerApplicationMapper;

    @EventHandler
    public void on(CustomerCreatedEvent customerCreatedEvent){
        log.info("on CustomerCreatedEvent: {}", customerCreatedEvent);

        CustomerEntity customerEntity =
                customerApplicationMapper.customerCreateEventToCustomerEntity(customerCreatedEvent);

        customerEntity.getAddress().setCustomer(customerEntity);
        customerEntity.getContact().setCustomer(customerEntity);
        customerEntity.getKyc().setCustomer(customerEntity);

        CustomerSegmentEntity customerSegmentEntity = customerSegmentRepository
                .findById(customerCreatedEvent.customerSegmentId().customerSegmentId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,"Segment not found"));
        customerEntity.setCustomerSegment(customerSegmentEntity);

        customerRepository.save(customerEntity);
    }

    @EventHandler
    public void on(CustomerPhoneNumberChangedEvent customerPhoneNumberChangedEvent){
        log.info("on CustomerPhoneNumberChangedEvent: {}", customerPhoneNumberChangedEvent);

        CustomerEntity customerEntity = customerRepository
                .findById(customerPhoneNumberChangedEvent.customerId().value())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found"));

        customerEntity.setPhoneNumber(customerPhoneNumberChangedEvent.phoneNumber());
        customerRepository.save(customerEntity);
    }
}
