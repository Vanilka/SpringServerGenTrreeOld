package gentree.client.desktop.configuration.messages;

import org.apache.logging.log4j.Logger;

/**
 * Created by Martyna SZYMKOWIAK on 19/03/2017.
 */
public class LogMessages {

    public static final String DELIMITER_OPEN =  "************************************";
    public static final String DELIMITER_MIDDLE = "------------------------------------";
    public static final String MSG_LABEL_REST_CALL = "Start Rest Call";
    public static final String MSG_LABEL_FIN_REST_CALL = "Fin Rest Call";
    public static final String MSG_LABEL_UPDATE_GUARD = "Start Update Relations by Guard";
    public static final String MSG_LABEL_FIN_UPDATE_GUARD = "Fin Update Relations by Guard";
    public static final String MSG_LABEL_CLEAN_GUARD = "Start Update Relations by Guard";
    public static final String MSG_LABEL_FIN_CLEAN_GUARD = "Fin Update Relations by Guard";

    public static final String MSG_CTRL_INITIALIZATION = "Initialization controller";
    public static final String MSG_CTRL_INITIALIZED = "Controller Initialized correctly";

    public static final String MSG_FAMILY_SERVICE_CURRENT_FAMILY = "Set Current Family [{}]";

    public static final String MSG_MEMBER_ADD_NEW = "Add new Member [{}]";
    public static final String MSG_RELATION_ADD_NEW = "Add new Relation [{}]";
    public static final String MSG_MEMBER_UPDATE = "Update Member [{}]";
    public static final String MSG_RELATION_ADDED =  "Relation was added : [{}]";
    public static final String MSG_RELATION_REMOVED =  "Relation was removed : [{}]";
    public static final String MSG_RELATION_ADDED_CAUSE = "Relation to be active : [{}]";
    public static final String MSG_RELATION_UPDATE =  "ID of relation to be NON ACTIVE : [{}]";
    public static final String MSF_RELATION_ID_CLEAN = "ID of relation to remove Guard : [{}]";

    public static final String MSG_MEMBER_ADDED =  "Member was added : [{}]";
    public static final String MSG_MEMBER_REMOVED =  "Member was removed : [{}]";


    public static final String MSG_RELATION_VERIF_EXIST_BORN = "Verification Existing of Born Relation";
    public static final String MSG_RELATION_VERIF_EXIST_BORN_FOR = "Verification Existing of Born Relation for [{}]";
    public static final String MSG_RELATION_BORN = "Born Relation [{}]";
    public static final String MSG_ERROR_BORN = "Member [{}] has not an unique relation";
    public static final String MSG_ERROR_CREATE_FAMILY = "Error Create family : [{}]";
    public static final String MSG_ERROR_SAVE_FAMILY = "Error Save family in folder : [{}] \n  ErrorMessage: [{}]";

    public static final String MSG_SERVICE_INITIALIZATION = "Service Initialization";


    public static final String MSG_NO_CONFIG_FILE = "Cannot find config file. It will be created.";
    public static final String MSG_READ_CONFIG_FILE = "Read config file";
    public static final String MSG_DIR_NOT_EXIST = "Mandatory directory {{}} not exist. It will be created";
    public static final String MSG_MISSING_PROPERTY = "The property {{}} is missing in config file. The default value will be used";

    public static final String MSG_ERROR_LOAD_IMAGE = "Error while load image";
    public static final String MSG_NO_STRONG_PERSON = "Cannot fins strong person for Relation. Should never happens";
    public static final String MSG_MERGING_RELATIONS = "Relation {{}} will be merge to {{}}";
    public static final String MSG_AFTER_MERGE = "Merging Relation result -> {{}}";

    public static final String MSG_POST_REQUEST = "POST REQUEST";
    public static final String MSG_POST_REQUEST_CONTENT = "Content : [{}]";

    public static final String MSG_SERVER_ACCESS_PATH = "Server access path : [{}]";
    public static final String MSG_SERVER_RETURNED_RESPONE = "Response returned by server {Status : {}, Body: {}}";


    public static final String MSG_SAVE_PROJECT_LOCAL = "Trying to save project in folder : {}";
    public static final String MSG_CONFIRM_SAVE_PROJECT_LOCAL = "Project has been correctly saved in folder: {}";

    //TRACE MESSAGES
    public static final String MSG_PARAMETER_PASSED_TO_FUNCTION = "Call Function : [{}] with parameters [{}]";


    public static void printHeader(Logger logger, String text) {
        logger.info(LogMessages.DELIMITER_OPEN);
        logger.info(text);
        logger.info(LogMessages.DELIMITER_OPEN);
    }

    public static void printFooter(Logger log, String text) {
        log.info(LogMessages.DELIMITER_OPEN);
        log.info(text);
        log.info(LogMessages.DELIMITER_OPEN);

    }
}
