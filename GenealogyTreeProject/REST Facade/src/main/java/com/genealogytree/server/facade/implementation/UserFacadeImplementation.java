package genealogytree.server.facade.implementation;

import genealogytree.ExceptionManager.exception.NotUniqueUserLoginException;
import genealogytree.ExceptionManager.exception.UserOrPasswordIncorrectException;
import genealogytree.domain.dto.UserDTO;
import genealogytree.persist.entity.modules.administration.UserEntity;
import genealogytree.server.facade.UserFacade;
import genealogytree.server.facade.converters.ConverterDtoToEntity;
import genealogytree.server.facade.converters.ConverterEntityToDto;
import genealogytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
@Service
public class UserFacadeImplementation implements UserFacade {

    //Todo

    @Autowired
    UserService service;

    @Autowired
    ConverterEntityToDto converterEntityToDto;

    @Autowired
    ConverterDtoToEntity converterDtoToEntity;

    /*
        PERSIST
     */
    @Override
    public UserDTO addUser(UserDTO user) throws NotUniqueUserLoginException {
        UserEntity entity = service.addUser(converterDtoToEntity.convert(user));
        return converterEntityToDto.convert(entity);
    }


    /*
        GET
     */

    @Override
    public UserDTO findUser(String login, String password) throws UserOrPasswordIncorrectException {
        return converterEntityToDto.convert(service.findUser(login, password));
    }

    @Override
    public UserDTO findUser(String login) {
        return converterEntityToDto.convert(service.findUser(login));
    }

    @Override
    public List<UserDTO> getAll() {
        return null;
    }


}
