package com.genealogytree.repository;

import com.genealogytree.persist.entity.modules.tree.FamilyEntity;
import com.genealogytree.persist.entity.modules.tree.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vanilka on 23/11/2016.
 */
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    @Query("SELECT m from MemberEntity m where m.ownerF= :family")
    List<MemberEntity> findAllMembersByFamily(@Param("family") FamilyEntity family);

}
