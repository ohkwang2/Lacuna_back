package LacunaMatata.Lacuna.dto.response.admin.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RespLowerCategoryAndProductDto {
    private int productLowerCategoryId;
    private int productUpperCategoryId;
    private String productLowerCategoryName;
}
