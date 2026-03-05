package co.istad.dara.account_service.domain.interceptor;

import co.istad.dara.account_service.application.dto.client.CustomerResponse;
import co.istad.dara.account_service.domain.command.CreateAccountCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class MyCommandDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCommandDispatchInterceptor.class);
    private final WebClient.Builder webClientBuilder;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            CreateAccountCommand createAccountCommand = (CreateAccountCommand) command.getPayload();
            WebClient webClient =  webClientBuilder.baseUrl("lb://customer").build();
            CustomerResponse customerResponse = webClient.get()
                    .uri("/api/customers/{customerId}", createAccountCommand.customerId().getValue())
                    .retrieve().bodyToMono(CustomerResponse.class).block();
            if(customerResponse != null){
                LOGGER.info("Dispatching a command {}.", createAccountCommand);
                LOGGER.info("Customer : {}", customerResponse);
                return new GenericCommandMessage<>(
                        new CreateAccountCommand(
                                createAccountCommand.accountId(),
                                createAccountCommand.accountNumber(),
                                createAccountCommand.accountHolder(),
                                createAccountCommand.customerId(),
                                createAccountCommand.accountTypeCode(),
                                createAccountCommand.branchId(),
                                createAccountCommand.initialBalance()
                        )
                );
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer id not found");

        };
    }
}
