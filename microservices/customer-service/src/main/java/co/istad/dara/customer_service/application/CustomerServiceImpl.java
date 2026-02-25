package co.istad.dara.customer_service.application;

import co.istad.dara.common.domain.valueobject.CustomerId;
import co.istad.dara.customer_service.application.dto.create.CreateCustomerRequest;
import co.istad.dara.customer_service.application.dto.create.CreateCustomerResponse;
import co.istad.dara.customer_service.application.dto.update.ChangePhoneNumberRequest;
import co.istad.dara.customer_service.application.dto.update.ChangePhoneNumberResponse;
import co.istad.dara.customer_service.application.mapper.CustomerApplicationMapper;
import co.istad.dara.customer_service.domain.command.ChangePhoneNumberCommand;
import co.istad.dara.customer_service.domain.command.CreateCustomerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService{
    private final CustomerApplicationMapper customerMapper;
    private final CommandGateway commandGateway;


    @Override
    public ChangePhoneNumberResponse changePhoneNumber(UUID customerId, ChangePhoneNumberRequest changePhoneNumberRequest) {

        // Transfer data from request body to let command execute
        ChangePhoneNumberCommand changePhoneNumberCommand = ChangePhoneNumberCommand.builder()
                .customerId(new CustomerId(customerId))
                .phoneNumber(changePhoneNumberRequest.phoneNumber())
                .build();
        log.info("ChangePhoneNumberCommand: {}", changePhoneNumberCommand);

        CustomerId result = commandGateway.sendAndWait(changePhoneNumberCommand);
//        commandGateway.sendAndWait(changePhoneNumberCommand); // ← don't capture result, it's void

        return ChangePhoneNumberResponse.builder()
                .customerId(result.value())
                .phoneNumber(changePhoneNumberCommand.phoneNumber())
                .message("Phone number changed successfully")
                .build();
    }

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {

        // Transfer data from request body to command execute
        CreateCustomerCommand createCustomerCommand = customerMapper
                .createCustomerRequestToCreateCustomerCommand(new CustomerId(UUID.randomUUID()), createCustomerRequest);
        log.info("CreateCustomerCommand: {}", createCustomerCommand);

        // Invoke and handle Axon command gateway
        CustomerId result = commandGateway.sendAndWait(createCustomerCommand);
        log.info("CommandGateway Result: {}", result);

        return CreateCustomerResponse.builder()
                .customerId(createCustomerCommand.customerId().value())
                .message("Customer saved successfully")
                .build();
    }
}
