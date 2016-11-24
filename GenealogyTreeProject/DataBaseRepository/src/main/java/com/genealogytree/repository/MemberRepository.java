package com.genealogytree.repository;

import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.repository.entity.modules.tree.GT_Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vanilka on 23/11/2016.
 */
@Repository
public interface MemberRepository extends JpaRepository<GT_Member, Long> {

    @Query("SELECT m from GT_Member m where m.ownerF= :family")
    public List<GT_Member> findAllMembersByFamily(@Param("family")GT_Family family);

}
