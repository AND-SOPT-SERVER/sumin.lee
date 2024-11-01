package org.sopt.week3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//아까 만든 테이블과 연동
@Repository
public interface SoptMemberRepository extends JpaRepository<SoptMemberEntity,Long> {
}
