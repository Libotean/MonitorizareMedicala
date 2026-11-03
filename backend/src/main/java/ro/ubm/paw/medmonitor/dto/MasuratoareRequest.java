package ro.ubm.paw.medmonitor.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MasuratoareRequest(
        @Min(20) @Max(250) Integer puls,
        @Min(50) @Max(300) Integer tensiuneSistolica,
        @Min(30) @Max(200) Integer tensiuneDiastolica,
        @DecimalMin("30.0") @DecimalMax("45.0") BigDecimal temperatura,
        @Min(20) @Max(800) Integer glicemie,
        @PastOrPresent LocalDateTime masuratLa
) {
    @AssertTrue(message = "Completeaza cel putin o valoare")
    public boolean isCelPutinOValoare() {
        return puls != null || tensiuneSistolica != null || tensiuneDiastolica != null
                || temperatura != null || glicemie != null;
    }
}
