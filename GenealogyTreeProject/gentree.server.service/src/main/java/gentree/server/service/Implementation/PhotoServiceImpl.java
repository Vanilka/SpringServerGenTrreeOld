package gentree.server.service.Implementation;

import gentree.server.configuration.properties.GenTreeProperties;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.PhotoEntity;
import gentree.server.repository.PhotoRepository;
import gentree.server.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.*;
import java.util.Base64;
import java.util.Date;

/**
 * Created by Martyna SZYMKOWIAK on 06/11/2017.
 */
@Transactional
@Service
public class PhotoServiceImpl implements PhotoService {

    public static final String FILE_NAME_TEMPLATE = "MEMBER_ID_%s_%s";
    @Autowired
    Environment env;
    @Autowired
    PhotoRepository repository;
    private String DIRECTORY_IMAGE_PATH;

    private static void decoder(String base64Image, String pathFile) {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }

    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

    @PostConstruct
    private void generateDirectoryImagePath() {
        DIRECTORY_IMAGE_PATH = env.getProperty(GenTreeProperties.PROP_FILESYSTEM_MAIN)
                .concat("/")
                .concat(env.getProperty(GenTreeProperties.PROP_DIRECTORY_IMG))
                .concat("/");
    }

    @Override
    public PhotoEntity persistPhoto(PhotoEntity entity) {
        saveFileOnDisk(entity);
        return repository.saveAndFlush(entity);
    }

    @Override
    public PhotoEntity retrievePhoto(MemberEntity memberEntity) {
        PhotoEntity entity = repository.findByOwner(memberEntity);
        if (entity != null) {
            entity.setEncodedStringPhoto(encoderImage(entity.getPhoto()));
        }
        return entity;
    }

    @Override
    public void populateEncodedPhoto(MemberEntity memberEntity) {
        if (memberEntity.getPhoto() != null) {
            memberEntity.getPhoto().setEncodedStringPhoto(encoderImage(memberEntity.getPhoto().getPhoto()));
        }
    }

    private void saveFileOnDisk(PhotoEntity entity) {
        String photoName = nameGenerator(entity.getOwner().getId());
        decoderImage(entity.getEncodedStringPhoto(), photoName);
        entity.setPhoto(photoName);

    }

    private String nameGenerator(Long memberId) {
        return String.format(FILE_NAME_TEMPLATE, memberId, Long.toString((new Date()).getTime()));
    }

    private void decoderImage(String base64Image, String filename) {
        decoder(base64Image, DIRECTORY_IMAGE_PATH.concat(filename));
    }

    private String encoderImage(String filename) {
        return encoder(DIRECTORY_IMAGE_PATH.concat(filename));
    }


}
