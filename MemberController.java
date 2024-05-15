package multiwear.member.controller;


import multiwear.member.model.dto.MemberDto;
import multiwear.member.service.MemberService;

public class MemberController {
    MemberService memberService;

    public MemberController() {
        memberService = new MemberService();
    }

    public void memberJoin(MemberDto memberDto) { // 회원추가
        memberService.memberJoin(memberDto);
    }

    public void deleteMember(String id) {
        memberService.deleteMember(id);
    }
    

    public void updateMember(String change, String newPw) {
        memberService.updateMember(Integer.parseInt(change), newPw);
    }

    public boolean memberSearch(MemberDto memberDto) { // 유저 조회
        return memberService.memberSeach(memberDto);
    }

    public void myPage() {
        memberService.myPage();
    }
}
