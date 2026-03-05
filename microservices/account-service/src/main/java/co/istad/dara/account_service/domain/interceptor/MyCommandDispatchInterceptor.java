package co.istad.dara.account_service.domain.interceptor;

import co.istad.dara.account_service.application.dto.client.CustomerResponse;
import co.istad.dara.account_service.domain.command.CreateAccountCommand;
import co.istad.dara.account_service.domain.command.DepositMoneyCommand;
import co.istad.dara.account_service.domain.command.FreezeAccountCommand;
import co.istad.dara.account_service.domain.command.WithdrawMoneyCommand;
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
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {

        return (index, command) -> {
            Object payload = command.getPayload();

            if (payload instanceof CreateAccountCommand createAccountCommand) {
                CustomerResponse customerResponse = fetchCustomer(
                        createAccountCommand.customerId().toString()
                );
                if (customerResponse != null) {
                    LOGGER.info("Dispatching CreateAccountCommand: {}", createAccountCommand);
                    return command; // no need to rebuild, just pass it through
                }
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer id not found");
            }

            if (payload instanceof DepositMoneyCommand depositMoneyCommand) {
                CustomerResponse customerResponse = fetchCustomer(
                        depositMoneyCommand.customerId().toString()
                );
                if (customerResponse != null) {
                    LOGGER.info("Dispatching DepositMoneyCommand: {}", depositMoneyCommand);
                    return command; // pass through as-is
                }
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer id not found");
            }

            if (payload instanceof WithdrawMoneyCommand withdrawMoneyCommand) {
                CustomerResponse customerResponse = fetchCustomer(
                        withdrawMoneyCommand.customerId().toString()
                );
                if (customerResponse != null) {
                    LOGGER.info("Dispatching WithdrawMoneyCommand: {}", withdrawMoneyCommand);
                    return command; // pass through as-is
                }
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer id not found");
            }

            if (payload instanceof FreezeAccountCommand freezeAccountCommand) {
                CustomerResponse customerResponse = fetchCustomer(
                        freezeAccountCommand.customerId().toString()
                );
                if (customerResponse != null) {
                    LOGGER.info("Dispatching FreezeAccountCommand: {}", freezeAccountCommand);
                    return command; // pass through as-is
                }
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer id not found");
            }

            return command; // pass through unrecognized commands untouched
        };
    }

    // Extract reusable WebClient call
    private CustomerResponse fetchCustomer(String customerId) {
        return webClientBuilder.baseUrl("lb://customer").build()
                .get()
                .uri("/api/customers/{customerId}", customerId)
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .block();
    }
}
