package com.genealogytree.repository;

import com.genealogytree.persist.entity.modules.tree.FamilyEntity;
import com.genealogytree.persist.entity.modules.tree.MemberEntity;
import com.genealogytree.persist.entity.modules.tree.RelationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by vanilka on 30/12/2016.
 */
public interface RelationRepository extends JpaRepository<RelationsEntity, Long> {

    @Query("SELECT r from RelationsEntity r where r.ownerF= :family")
    List<RelationsEntity> findAllRelationsByFamily(@Param("family") FamilyEntity family);

    @Query("SELECT r from RelationsEntity r where r.simLeft =:simLeft and r.simRight =:simRight")
    RelationsEntity findRelationBySimLeftAndSimRight(@Param("simLeft") MemberEntity simLeft,
                                                     @Param("simRight") MemberEntity simRight);
}
