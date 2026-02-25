package co.istad.dara.customer_service.infrastucture.message;



import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedErrorResponse {
    private BasedError error;
}

