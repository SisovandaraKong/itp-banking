package co.istad.dara.pipeline_service.stream;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Product {
    private String code;
    private Integer qty;
}
