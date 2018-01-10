package gentree.server.service;

import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.PhotoEntity;

/**
 * Created by Martyna SZYMKOWIAK on 06/11/2017.
 */
public interface PhotoService {

    PhotoEntity persistPhoto(PhotoEntity entity);

    PhotoEntity retrievePhoto(MemberEntity memberEntity);

    void populateEncodedPhoto(MemberEntity memberEntity);

}
