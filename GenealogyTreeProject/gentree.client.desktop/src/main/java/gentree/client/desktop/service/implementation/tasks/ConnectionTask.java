package gentree.client.desktop.service.implementation.tasks;

import gentree.client.desktop.configuration.converters.ConverterDtoToModel;
import gentree.client.desktop.configuration.converters.ConverterModelToDto;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.service.implementation.ConnectionService;
import gentree.client.desktop.service.implementation.GenTreeOnlineService;
import lombok.extern.log4j.Log4j2;

/**
 * Created by vanilka on 17/12/2017.
 */
@Log4j2
public abstract class ConnectionTask {

    protected final ConverterModelToDto cmd = new ConverterModelToDto();
    protected final ConverterDtoToModel cdm = new ConverterDtoToModel();

    protected ConnectionService cs = ConnectionService.INSTANCE;

    protected GenTreeOnlineService service;

    public ConnectionTask() {

    }


    public void registerService(GenTreeOnlineService onlineService) {
        this.service = onlineService;
    }

}
