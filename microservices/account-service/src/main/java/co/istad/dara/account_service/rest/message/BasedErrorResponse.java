package co.istad.dara.account_service.rest.message;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedErrorResponse {
    private BasedError error;
}

