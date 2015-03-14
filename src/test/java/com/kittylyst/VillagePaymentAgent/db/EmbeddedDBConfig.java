package com.kittylyst.VillagePaymentAgent.db;

public class EmbeddedDBConfig {

    private final String dBName;
    private final String userName;
    private final String password;
    private final String schemaName;
    private final String relPathCreateScript;
    private final String relPathPopulateScript;
    private final boolean deleteDataOnCloseOfLastConnection;

    private EmbeddedDBConfig(String dBName, String username, String password, String schemaName, String relPathCreateScriptToCp, String relPathPopulateScriptToCp, boolean deleteDataOnCloseOfLastConnection) {
        this.dBName = dBName;
        this.userName = username;
        this.password = password;
        this.schemaName = schemaName;
        this.relPathCreateScript = relPathCreateScriptToCp;
        this.relPathPopulateScript = relPathPopulateScriptToCp;
        this.deleteDataOnCloseOfLastConnection = deleteDataOnCloseOfLastConnection;
    }

    public static class Builder {
        private String dBName;
        private String userName;
        private String password;
        private String schemaName;
        private String relPathCreateScript;
        private String relPathPopulateScript;
        private boolean clearDataOnCloseOfLastConnection;

        public Builder setDBName(String dBName) {
            this.dBName = dBName;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setSchemaName(String schemaName) {
            this.schemaName = schemaName;
            return this;
        }

        public Builder setRelPathCreateScript(String relPathCreateScript) {
            this.relPathCreateScript = relPathCreateScript;
            return this;
        }

        public Builder setRelPathPopulateScript(String relPathPopulateScript) {
            this.relPathPopulateScript = relPathPopulateScript;
            return this;
        }

        public Builder setClearDataOnCloseOfLastConnection(boolean clearDataOnCloseOfLastConnection) {
            this.clearDataOnCloseOfLastConnection = clearDataOnCloseOfLastConnection;
            return this;
        }

        public EmbeddedDBConfig build() {
            return new EmbeddedDBConfig(dBName, userName, password, schemaName, relPathCreateScript, relPathPopulateScript, clearDataOnCloseOfLastConnection);
        }
    }

    public String getdBName() {
        return dBName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getRelPathCreateScript() {
        return relPathCreateScript;
    }

    public String getRelPathPopulateScript() {
        return relPathPopulateScript;
    }

    public boolean deleteDataOnCloseOfLastConnection() {
        return deleteDataOnCloseOfLastConnection;
    }
}
