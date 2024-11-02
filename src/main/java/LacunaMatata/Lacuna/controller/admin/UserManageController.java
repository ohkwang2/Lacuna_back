package LacunaMatata.Lacuna.controller.admin;

import LacunaMatata.Lacuna.dto.request.admin.usermanage.ReqDeleteUserListDto;
import LacunaMatata.Lacuna.dto.request.admin.usermanage.ReqGetUserListDto;
import LacunaMatata.Lacuna.dto.request.admin.usermanage.ReqModifyUserDto;
import LacunaMatata.Lacuna.dto.request.admin.usermanage.ReqRegistUserDto;
import LacunaMatata.Lacuna.dto.response.admin.usermanage.RespCountAndUserListDto;
import LacunaMatata.Lacuna.dto.response.admin.usermanage.RespUserDetailDto;
import LacunaMatata.Lacuna.service.admin.UserManageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/************************************************
 * version: 1.0.4                               *
 * author: 손경태                                *
 * description: AdminUserManageController()-세팅 *
 * createDate: 2024-10-16                       *
 * updateDate: 2024-10-16                       *
 ************************************************/
@RestController
@RequestMapping("/api/v1/admin/user")
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    // 사용자 목록 출력
    @GetMapping("/list")
    public ResponseEntity<?> getUserList(ReqGetUserListDto dto) {
        RespCountAndUserListDto respCountAndUserListDto = userManageService.getUserInfoList(dto);
        return ResponseEntity.ok().body(respCountAndUserListDto);
    }

    // 사용자 등록
    @PostMapping("/regist")
    public ResponseEntity<?> registerUser(@RequestBody ReqRegistUserDto dto) {
        userManageService.registUser(dto);
        return ResponseEntity.ok().body(true);
    }

    // 사용자 항목 출력
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetail(@PathVariable int userId) {
        RespUserDetailDto userDetail = userManageService.getUserDetail(userId);
        return ResponseEntity.ok().body(userDetail);
    }

    // 사용자 권한 목록 출력(필터)
    @GetMapping("/role/list/filter")
    public ResponseEntity<?> getUserRoleList() {

        return ResponseEntity.ok().body(true);
    }

    // 사용자 수정(권한, 활성화)
    @PutMapping("/modify/{userId}")
    public ResponseEntity<?> modifyUser(@RequestBody ReqModifyUserDto dto, @PathVariable int userId) {
        userManageService.modifyUser(dto);
        return ResponseEntity.ok().body(true);
    }

    // 사용자 삭제
    @PostMapping("/delete/{userId}")
    @ApiOperation(value = "deleteUserApi")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        userManageService.deleteUser(userId);
        return ResponseEntity.ok().body(true);
    }

    // 사용자 복수개 삭제
    @PostMapping("/delete")
    @ApiOperation(value = "deleteUserListApi")
    public ResponseEntity<?> deleteUserList(@RequestBody ReqDeleteUserListDto dto) {
        userManageService.deleteUserList(dto);
        return ResponseEntity.ok().body(true);
    }
}
