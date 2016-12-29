package com.genealogytree.repository;

import com.genealogytree.repository.entity.modules.tree.GT_Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vanilka on 27/12/2016.
 */
@Repository
public interface ImagesRepository extends JpaRepository<GT_Images, Long> {


}
