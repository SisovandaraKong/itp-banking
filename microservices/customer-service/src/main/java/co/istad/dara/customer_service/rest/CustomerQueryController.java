package co.istad.dara.customer_service.rest;

import co.istad.dara.common.dto.PageResponse;
import co.istad.dara.customer_service.application.CustomerQueryService;
import co.istad.dara.customer_service.application.dto.query.CustomerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Slf4j
public class CustomerQueryController {

    private final CustomerQueryService customerQueryService;

    @GetMapping("/{customerId}/history")
    public List<?> getCustomerHistory(@PathVariable UUID customerId){
        return customerQueryService.getCustomerHistory(customerId);
    }

    @GetMapping
    public PageResponse getAllCustomers(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "5", required = false) int pageSize
    ){
        return customerQueryService.getAllCustomers(pageNumber, pageSize);
    }

    @GetMapping("/{customerId}")
    public CustomerResponse getCustomerById(@PathVariable UUID customerId){
        return customerQueryService.getCustomerById(customerId);
    }
}
