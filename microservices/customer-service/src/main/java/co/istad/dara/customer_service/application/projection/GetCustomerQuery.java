package co.istad.dara.customer_service.application.projection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class GetCustomerQuery {
    private Integer pageNumber;
    private Integer pageSize;
}
