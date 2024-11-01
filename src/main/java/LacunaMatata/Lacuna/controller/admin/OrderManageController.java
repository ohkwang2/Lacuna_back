package LacunaMatata.Lacuna.controller.admin;

import LacunaMatata.Lacuna.dto.request.admin.order.ReqGetOrderListDto;
import LacunaMatata.Lacuna.dto.response.admin.order.RespCountAndOderListDto;
import LacunaMatata.Lacuna.dto.response.admin.order.RespGetOrderStatusFilterDto;
import LacunaMatata.Lacuna.service.admin.OrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/************************************************
 * version: 1.0.4                               *
 * author: 손경태                                *
 * description: AdminOrderController() - 세팅    *
 * createDate: 2024-10-16                       *
 * updateDate: 2024-10-16                       *
 ************************************************/
@RestController
@RequestMapping("/api/v1/admin")
public class OrderManageController {

    @Autowired
    private OrderManageService orderManageService;

    // 회원 주문 리스트 출력
    @GetMapping("/order/list")
    public ResponseEntity<?> getOrderList(@RequestBody ReqGetOrderListDto dto) {
        RespCountAndOderListDto respCountAndOderListDto = orderManageService.getOrderList(dto);
        return ResponseEntity.ok().body(respCountAndOderListDto);
    }

    // 회원 주문 항목 상세 출력
    @GetMapping("/order/detail/{orderId}")
    public ResponseEntity<?> getOrderDetail() {
        return ResponseEntity.ok().body(true);
    }

    // 회원 결제 항목 상세 출력
    @GetMapping("/pay/detail/{paymentId}")
    public ResponseEntity<?> getPaymentDetail() {
        return ResponseEntity.ok().body(true);
    }

    // 회원 결제 취소하기(결제 시스템)
    @PutMapping("/pay/system/cancel/{paymentId}")
    public ResponseEntity<?> cancelSystemPayment() {
        return ResponseEntity.ok().body(true);
    }

    // 주문 수정 - 회원 결제 취소하기(계좌 이체)
    @PostMapping("/pay/account/cancel")
    public ResponseEntity<?> cancelAccountPayment() {
        return ResponseEntity.ok().body(true);
    }

    // 주문 수정 - 회원 결제 승인하기(계좌 이체)
    @PostMapping("/pay/account/approve")
    public ResponseEntity<?> approveAccountPayment() {
        return ResponseEntity.ok().body(true);
    }

    // 회원 주문 항목 삭제
    @DeleteMapping("/pay/account/order/delete/{orderId}")
    public ResponseEntity<?> deleteOrder() {
        return ResponseEntity.ok().body(true);
    }

    // 회원 주문 항목 복수개 삭제
    @DeleteMapping("/pay/account/order/delete")
    public ResponseEntity<?> deleteOrderLIST() {
        return ResponseEntity.ok().body(true);
    }

    @GetMapping("/status/filter")
    public ResponseEntity<?> getOrderStatusFilter() {
        List<RespGetOrderStatusFilterDto> orderFilter = orderManageService.getOrderStatusFilter();
        return ResponseEntity.ok().body(orderFilter);
    }
}
