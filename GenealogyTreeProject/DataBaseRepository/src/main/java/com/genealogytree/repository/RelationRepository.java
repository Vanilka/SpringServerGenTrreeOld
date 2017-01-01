package com.genealogytree.repository;

import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.repository.entity.modules.tree.GT_Member;
import com.genealogytree.repository.entity.modules.tree.GT_Relations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by vanilka on 30/12/2016.
 */
public interface RelationRepository extends JpaRepository<GT_Relations, Long> {

    @Query("SELECT r from GT_Relations r where r.ownerF= :family")
     List<GT_Relations> findAllRelationsByFamily(@Param("family")GT_Family family);

    @Query("SELECT r from GT_Relations r where r.simLeft =:simLeft and r.simRight =:simRight")
    GT_Relations findRelationBySimLeftAndSimRight(@Param("simLeft")GT_Member simLeft,
                                                  @Param("simRight")GT_Member simRight);
}
