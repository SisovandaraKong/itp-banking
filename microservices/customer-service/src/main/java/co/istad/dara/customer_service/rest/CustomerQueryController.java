package co.istad.dara.customer_service.rest;

import co.istad.dara.customer_service.application.CustomerQueryService;
import co.istad.dara.customer_service.application.dto.query.CustomerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Slf4j
public class CustomerQueryController {

    private final CustomerQueryService customerQueryService;

    @GetMapping
    public Page<CustomerResponse> getAllCustomers(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "5", required = false) int pageSize
    ){
        return customerQueryService.getAllCustomers(pageNumber, pageSize);
    }


}
