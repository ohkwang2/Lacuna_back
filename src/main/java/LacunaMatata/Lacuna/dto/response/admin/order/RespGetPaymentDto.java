package LacunaMatata.Lacuna.dto.response.admin.order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class RespGetPaymentDto {
    private int paymentId;
    private String paymentApproveId;
    private String name;
    private String paymentMethod;
    private int amount;
    private String paymentStatus;
    private LocalDateTime createdDate;
}
