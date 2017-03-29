package com.genealogytree.repository;

import com.genealogytree.persist.entity.modules.administration.UserEntity;
import com.genealogytree.persist.entity.modules.tree.FamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {

    @Query("Select f from FamilyEntity f WHERE f.owner= :user")
    List<FamilyEntity> findAllFamillyByOwner(@Param("user") UserEntity user);


    FamilyEntity findFamilyById(Long id);
}
