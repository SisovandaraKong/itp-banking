package co.istad.dara.customer_service.domain.aggregate;

import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.common.domain.valueobject.CustomerSegmentId;
import co.istad.dara.customer_service.data.repository.CustomerRepository;
import co.istad.dara.customer_service.domain.command.ChangePhoneNumberCommand;
import co.istad.dara.customer_service.domain.command.CreateCustomerCommand;
import co.istad.dara.customer_service.domain.event.CustomerCreatedEvent;
import co.istad.dara.customer_service.domain.event.CustomerPhoneNumberChangedEvent;
import co.istad.dara.customer_service.domain.valueobject.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Aggregate
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Slf4j
public class CustomerAggregate {

    @AggregateIdentifier
    private CustomerId customerId;

    private CustomerName name;
    private CustomerEmail email;
    private String phoneNumber;
    private CustomerGender gender;
    private LocalDate dob;
    private Kyc kyc;
    private Address address;
    private Contact contact;
    private CustomerSegmentId customerSegmentId;
    private List<String> failureMessages;


    // Domain logic for creating customer
    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand,
                             CustomerRepository customerRepository){
        // Perform domain logic
        // Validation email, phoneNumber ...

//        validatePhoneNumber(createCustomerCommand.phoneNumber());
        if (customerRepository.existsByPhoneNumber(createCustomerCommand.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Phone number already exist");
        }

        // Publish event -> CustomerCreatedEvent

        CustomerCreatedEvent customerCreatedEvent = CustomerCreatedEvent.builder()
                .customerId(createCustomerCommand.customerId())
                .name(createCustomerCommand.name())
                .email(createCustomerCommand.email())
                .gender(createCustomerCommand.gender())
                .phoneNumber(createCustomerCommand.phoneNumber())
                .dob(createCustomerCommand.dob())
                .kyc(createCustomerCommand.kyc())
                .address(createCustomerCommand.address())
                .contact(createCustomerCommand.contact())
                .customerSegmentId(createCustomerCommand.customerSegmentId())
                .build();

        AggregateLifecycle.apply(customerCreatedEvent);
    }


    @CommandHandler
    public CustomerId handler(ChangePhoneNumberCommand changePhoneNumberCommand,
                              CustomerRepository customerRepository){

        if (!customerRepository.existsById(changePhoneNumberCommand.customerId().value())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer's uuid not found");
        }
        if (customerRepository.existsByPhoneNumber(changePhoneNumberCommand.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Phone number already exist");
        }

        log.info("Handle ChangePhoneNumberCommand: {}", changePhoneNumberCommand);
        CustomerPhoneNumberChangedEvent customerPhoneNumberChangedEvent = CustomerPhoneNumberChangedEvent.builder()
                .customerId(changePhoneNumberCommand.customerId())
                .phoneNumber(changePhoneNumberCommand.phoneNumber())
                .build();

        AggregateLifecycle.apply(customerPhoneNumberChangedEvent);

        return this.customerId;
    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent customerCreatedEvent){
        this.customerId = customerCreatedEvent.customerId();
        this.name = customerCreatedEvent.name();
        this.email = customerCreatedEvent.email();
        this.gender = customerCreatedEvent.gender();
        this.phoneNumber = customerCreatedEvent.phoneNumber();
        this.dob = customerCreatedEvent.dob();
        this.kyc = customerCreatedEvent.kyc();
        this.address = customerCreatedEvent.address();
        this.contact = customerCreatedEvent.contact();
        this.customerSegmentId = customerCreatedEvent.customerSegmentId();
    }

    @EventSourcingHandler
    public void on(CustomerPhoneNumberChangedEvent customerPhoneNumberChangedEvent){
        this.customerId = customerPhoneNumberChangedEvent.customerId();
        this.phoneNumber = customerPhoneNumberChangedEvent.phoneNumber();
    }

}
