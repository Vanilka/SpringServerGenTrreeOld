package com.genealogytree.service;

import com.genealogytree.persist.entity.modules.tree.ImagesEntity;

/**
 * Created by vanilka on 27/12/2016.
 */

public interface ImagesService {

    ImagesEntity findById(Long id);

    ImagesEntity saveImage(ImagesEntity image);

    ImagesEntity updateImage(ImagesEntity image);

    ImagesEntity updateImageName(Long id, String name);
}