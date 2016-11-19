package com.genealogytree.repository;

import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<GT_Family, Long> {

    @Query("Select f from GT_Family f WHERE f.owner= :user")
    public List<GT_Family> findAllFamillyByOwner(@Param("user") GT_User user);

}
