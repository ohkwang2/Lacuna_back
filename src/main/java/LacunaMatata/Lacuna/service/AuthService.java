package LacunaMatata.Lacuna.service;

import LacunaMatata.Lacuna.dto.request.user.auth.ReqGeneralSigninDto;
import LacunaMatata.Lacuna.dto.request.user.auth.ReqGeneralSignupDto;
import LacunaMatata.Lacuna.entity.user.*;
import LacunaMatata.Lacuna.exception.InactiveAccountException;
import LacunaMatata.Lacuna.repository.user.UserMapper;
import LacunaMatata.Lacuna.repository.user.UserRoleMetMapper;
import LacunaMatata.Lacuna.security.ip.IpUtils;
import LacunaMatata.Lacuna.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/************************************
 * version: 1.0.5                   *
 * author: 손경태                    *
 * description: AuthService         *
 * createDate: 2024-10-17           *
 * updateDate: 2024-10-21           *
 ***********************************/
@Service
public class AuthService {

    @Autowired private IpUtils ipUtils;

    @Autowired private JwtProvider jwtProvider;

    @Autowired private UserMapper userMapper;
    @Autowired private UserRoleMetMapper userRoleMetMapper;

    // 일반 회원 가입
    public void signup(ReqGeneralSignupDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .build();
        userMapper.saveUser(user);
        int userId = user.getUserId();
        int thirdPartyInfoSharingAgreement = 0;
        int marketingReceiveAgreement = 0;
        int useConditionAgreement = 0;
        if(dto.getThirdPartyInfoSharingAgreement() == true) {
            thirdPartyInfoSharingAgreement = 1;
        } else {
            thirdPartyInfoSharingAgreement = 2;
        }
        if(dto.getMarketingReceiveAgreement() == true) {
            marketingReceiveAgreement = 1;
        } else {
            marketingReceiveAgreement = 2;
        }
        if(dto.getUseConditionAgreement() == true) {
            useConditionAgreement = 1;
        } else {
            useConditionAgreement = 2;
        }
        UserOptionalInfo userOptionalInfo = UserOptionalInfo.builder()
                .userId(user.getUserId())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .marketingReceiveAgreement(marketingReceiveAgreement)
                .thirdPartyInfoSharingAgreement(thirdPartyInfoSharingAgreement)
                .useConditionAgreement(useConditionAgreement)
                .build();
        userMapper.saveUserOptionalInfo(userOptionalInfo);

        UserRoleMet userRoleMet = UserRoleMet.builder()
                .roleUserId(user.getUserId())
                .build();
        userRoleMetMapper.saveUserRoleMat(userRoleMet);
    }

    // 일반 로그인
    public String signin(HttpServletRequest request, ReqGeneralSigninDto dto) {
        User user = userMapper.findUserByUsername(dto.getUsername());

        // 입력한 로그인 아이디가 있는지 확인 -> 없으면 오류
        if(user == null) {
            throw new RuntimeException("없는 아이디"); // 나중에 사용자 정보를 확인하세요로 고칠 부분
        }

        // 입력한 아이디가 존재할 때 해당 아이디의 비밀번호가 일치하는지 확인 => 없으면 오류
        if(!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("사용자 정보를 확인하세요");
        }

        // 계정 비활성화인 경우
        if(user.getLastLoginDate().isBefore(LocalDateTime.now().minusYears(1))) {
            throw new InactiveAccountException();
        }

        // ip와 토큰 가져오기
        String loginIp = IpUtils.getClientIp(request);
        int roleId = user.getUserRoleMets().stream().map(ur -> ur.getRoleId())
                .max(Comparator.naturalOrder()).orElse(2);

        String accessToken = jwtProvider.generateAccessToken(user.getUserId(), roleId);

        // 로그인 정보에 로그인 시간과 ip 저장하기
        LoginHistory loginHistory = LoginHistory.builder()
                .loginUserId(user.getUserId())
                .loginIp(loginIp)
                .build();
        userMapper.saveLoginHistory(loginHistory);

        return accessToken;
    }
}
