package gentree.server.service.Implementation;

import gentree.server.configuration.enums.RoleEnum;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.repository.OwnerRepository;
import gentree.server.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
@Service
@Transactional
public class OwnerServiceImpl implements OwnerService{

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    Environment env;

    @Override
    public OwnerEntity addNewOwner(OwnerEntity owner) {
        return owner;
    }

    @Override
    public List<OwnerEntity> findAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public OwnerEntity findOperatorByLogin(String login) {
        return ownerRepository.findByLogin(login);
    }

    @PostConstruct
    private void setDefaultOperators() {

        ownerRepository.saveAndFlush(new OwnerEntity
                ("admin", "123456", RoleEnum.ADMIN));
        ownerRepository.saveAndFlush(new OwnerEntity
                ("vanilka", "123456", RoleEnum.USER));
    }
}
