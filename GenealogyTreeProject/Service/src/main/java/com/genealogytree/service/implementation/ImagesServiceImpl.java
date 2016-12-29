package com.genealogytree.service.implementation;

import com.genealogytree.repository.ImagesRepository;
import com.genealogytree.repository.entity.modules.tree.GT_Images;
import com.genealogytree.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vanilka on 27/12/2016.
 */
@Service
@Transactional
@ComponentScan("com.genealogytree")
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    ImagesRepository repository;

    @Override
    public GT_Images findById(Long id) {
        if(repository.exists(id)) {
            return repository.findOne(id);
        } else {
            return null;
        }
    }

    @Override
    public GT_Images saveImage(GT_Images image) {
        GT_Images savedImage = repository.save(image);
        return  savedImage;
    }

    @Override
    public GT_Images updateImage(GT_Images image) {
        GT_Images savedImage = repository.saveAndFlush(image);
        return  savedImage;
    }

    @Override
    public GT_Images updateImageName(Long id, String name) {
        return null;
    }

}
