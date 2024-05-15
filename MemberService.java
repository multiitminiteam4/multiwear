package multiwear.member.service;

import multiwear.member.model.dao.MemberDao;
import multiwear.member.model.dto.MemberDto;


    public class MemberService {
        MemberDao memberDao = null;

        public MemberService() {
            memberDao = new MemberDao();
        }

        public boolean memberSeach(MemberDto memberDto) {
            return memberDao.memberSeach(memberDto);
        }

        public void memberJoin(MemberDto memberDto) {
            memberDao.memberJoin(memberDto);
        }




        public void deleteMember(String user_Id) {
            memberDao.deleteMember(user_Id);
        }



        public void updateMember(int change, String newPw) {
            memberDao.updateMember(change, newPw);
        }

        public void myPage() {
        }
    }

