package genealogytree.service.implementation;

import genealogytree.persist.entity.modules.tree.ImagesEntity;
import genealogytree.repository.ImagesRepository;
import genealogytree.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vanilka on 27/12/2016.
 */
@Service
@Transactional
@ComponentScan("genealogytree")
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    ImagesRepository repository;

    @Override
    public ImagesEntity findById(Long id) {
        if (repository.exists(id)) {
            return repository.findOne(id);
        } else {
            return null;
        }
    }

    @Override
    public ImagesEntity saveImage(ImagesEntity image) {
        ImagesEntity savedImage = repository.save(image);
        return savedImage;
    }

    @Override
    public ImagesEntity updateImage(ImagesEntity image) {
        ImagesEntity savedImage = repository.saveAndFlush(image);
        return savedImage;
    }

    @Override
    public ImagesEntity updateImageName(Long id, String name) {
        return null;
    }

}
