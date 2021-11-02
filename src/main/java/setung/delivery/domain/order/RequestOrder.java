package setung.delivery.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrder {

    private long restaurantId;

    private String address;

    // private PayType playType 후에 결제 방식 추가
}
