package com.kittylyst.VillagePaymentAgent.db;

public final class EmbeddedDBUtils {


    private EmbeddedDBUtils() {}

    private final static String urlPrefix = "jdbc:h2:mem:";
    private final static String schemaPrefix = "INIT=CREATE SCHEMA IF NOT EXISTS ";
    private final static String scriptPrefix = "RUNSCRIPT FROM 'classpath:";
    private final static String retainAllDataOnNoLiveConnections = "DB_CLOSE_DELAY=-1";

    //http://www.h2database.com/html/features.html#database_url #Database URL Overview
    //http://stackoverflow.com/questions/5225700/can-i-have-h2-autocreate-a-schema-in-an-in-memory-database
    public static String getConnectionSettings(EmbeddedDBConfig config) {

        //TODO tightening of logic for nulls

        StringBuilder sb = new StringBuilder();
        sb.append(urlPrefix).append(config.getdBName()).append(";");
        sb.append(schemaPrefix).append(config.getSchemaName()).append("\\;");
        sb.append(scriptPrefix).append(config.getRelPathCreateScript()).append("'\\;");
        sb.append(scriptPrefix).append(config.getRelPathPopulateScript()).append("'");
        if (!config.deleteDataOnCloseOfLastConnection())
            sb.append(";").append(retainAllDataOnNoLiveConnections);

        return sb.toString();
    }
}
