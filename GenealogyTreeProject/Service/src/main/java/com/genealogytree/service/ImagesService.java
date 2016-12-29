package com.genealogytree.service;

import com.genealogytree.repository.entity.modules.tree.GT_Images;

/**
 * Created by vanilka on 27/12/2016.
 */

public interface ImagesService {

    GT_Images findById(Long id);

    GT_Images saveImage(GT_Images image);

    GT_Images updateImage(GT_Images image);

    GT_Images updateImageName(Long id, String name);
}