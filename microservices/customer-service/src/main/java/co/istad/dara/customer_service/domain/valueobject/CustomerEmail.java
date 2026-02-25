package co.istad.dara.customer_service.domain.valueobject;

public record CustomerEmail(
        String primaryEmail,
        String secondaryEmail
) {
}
