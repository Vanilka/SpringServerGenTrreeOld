package gentree.server.manager.db;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
public class DBProperties {
    public static final String PARAM_DB_SOURCE_DRIVER_CLASS_NAME = "emot.db.driver.class.name";
    public static final String PARAM_DB_SOURCE_URL = "emot.db.source.url";
    public static final String PARAM_DB_SOURCE_USER = "emot.db.source.user";
    public static final String PARAM_DB_SOURCE_PASSWORD = "emot.db.source.password";
    public static final String PARAM_DB_SOURCE_SCHEMA = "emot.db.source.schema";

    public static final String PARAM_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    public static final String PARAM_HIBERNATE_DIALECT = "hibernate.dialect";
    public static final String PARAM_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    public static final String PARAM_HIBERNATE_USE_SQL_COMMENTS = "hibernate.use_sql_comments";


    private static String VALUE_DEFAULT_HIBERNATE_HBM2DDL_AUTO = "create-drop";
}
