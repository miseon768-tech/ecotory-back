package com.example.ecotory.domain.member.api;


import com.example.ecotory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {
    final MemberRepository memberRepository;


    // 회원가입 - 비밀번호 유효성 검사
    @GetMapping("/password/check")
    public Map<String, Object> checkPassword(@RequestParam String password) {

        Map<String, Object> result = new HashMap<>();

        if (password.length() < 8 || password.length() > 20) {
            result.put("valid", false);
            result.put("message", "비밀번호는 8자 이상, 20자 이하이어야 합니다.");
            return result;
        }

        if (password.contains(" ")) {
            result.put("valid", false);
            result.put("message", "비밀번호에 공백을 포함할 수 없습니다.");
            return result;
        }

        result.put("valid", true);
        result.put("message", "사용 가능한 비밀번호입니다.");
        return result;
    }


    // 회원가입 - 이메일(회원 아이디) 중복 확인
    @GetMapping("/email/check")
    public boolean checkEmail(@RequestParam String email) {

        return memberRepository.existsByEmail(email);

    }
}


    /*// 클래스룸 출석 체크 처리
    @PostMapping("/classroom/{classroomId}/dailyCheck")
    public String classroomDailyCheckHandle(@PathVariable String classroomId, @SessionAttribute String logonId) {
        // 이 요청을 보낸 사용자의 classroom_member 의 id 를 찾아야 함
        Map<String, Object> criteria = Map.of("classroomId", classroomId, "studentId", logonId);
        ClassroomMember member = classroomMemberMapper.selectByClassroomIdAndStudentId(criteria);
        if (member == null) {
            return "error";
        }
        int classroomMemberId = member.getId();
        // 이 사용자가 오늘 체크를 했는지 확인
        boolean r = classroomMemberMapper.existTodayDailyCheckByClassroomMemberId(classroomMemberId);
        if (r) {
            return "already_checked";
        }
        DailyCheck dailyCheck = new DailyCheck();
        dailyCheck.setClassroomMemberId(classroomMemberId);
        dailyCheck.setCheckedAt(LocalDate.now());
        classroomMemberMapper.insertDailyCheck(dailyCheck);
        return "checked";
    }*/



