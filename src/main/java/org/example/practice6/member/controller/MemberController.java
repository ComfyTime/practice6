package org.example.practice6.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.practice6.common.consts.Const;
import org.example.practice6.member.dto.MemberResponseDto;
import org.example.practice6.member.dto.MemberSaveRequestDto;
import org.example.practice6.member.dto.MemberSaveResponseDto;
import org.example.practice6.member.dto.MemberUpdateRequestDto;
import org.example.practice6.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> getOne(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }

    @PutMapping("/members/{memberId}")
    public void update(
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @RequestBody MemberUpdateRequestDto dto) {
        memberService.update(memberId, dto);
    }

    @DeleteMapping("/members/{memberId}")
    public void delete(
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId
    ) {
        memberService.deleteById(memberId);
    }
}