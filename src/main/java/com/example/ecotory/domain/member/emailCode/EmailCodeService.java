package com.example.ecotory.domain.member.emailCode;

import com.example.ecotory.domain.member.emailCode.dto.EmailCodeResponse;
import com.example.ecotory.domain.member.entity.Member;
import com.example.ecotory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailCodeService {
    private final MemberRepository memberRepository;
    private final MailSender mailSender;
    private final EmailCodeRepository emailCodeRepository;

    // 이메일 인증 코드 발송
    public EmailCodeResponse sendEmailCode(String email) {

        // 1. 사용자 조회
        Member member = memberRepository.findByEmail(email).orElse(null);

        if (member == null) {
            return EmailCodeResponse.builder()
                    .success(false)
                    .message("가입된 사용자가 아닙니다.")
                    .build();
        }

        // 2. 인증코드 생성
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 3. 메일 발송
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(member.getEmail());
        mailMessage.setSubject("[ECOTORY] 이메일 인증 코드");
        mailMessage.setText(
                "안녕하세요. " + member.getNickname() + "님!\n\n" +
                        "아래 인증 코드를 입력해 로그인 절차를 완료해주세요.\n\n" +
                        "인증코드 : " + code + "\n\n" +
                        "감사합니다."
        );
        mailSender.send(mailMessage);

        // 4. 인증 코드 저장
        EmailCode emailCode = new EmailCode();
        emailCode.setCode(code);
        emailCode.setMemberId(member.getId());
        emailCode.setExpiredAt(LocalDateTime.now().plusMinutes(10));

        emailCodeRepository.save(emailCode);

        return EmailCodeResponse.builder()
                .success(true)
                .message("인증 코드가 이메일로 전송되었습니다.")
                .build();
    }
}
