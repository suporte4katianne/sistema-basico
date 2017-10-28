package br.com.hsi.util;

import liquibase.integration.cdi.CDILiquibaseConfig;
import liquibase.integration.cdi.annotations.LiquibaseType;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

@ApplicationScoped
public class LiquibaseProducer {


    @Produces @LiquibaseType
    public CDILiquibaseConfig createConfig() {
        CDILiquibaseConfig config = new CDILiquibaseConfig();
        config.setChangeLog("hsisistemas/liquibase/sistema_basico.xml");
        return config;
    }

    @Produces @LiquibaseType
    public DataSource createDataSource() throws SQLException {
        try {
            InitialContext contexto = new InitialContext();
            DataSource dataSource = (DataSource)contexto.lookup("java:comp/env/jdbc/banco");
            return dataSource;
        } catch (NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Produces @LiquibaseType
    public ResourceAccessor create() {
        return new ClassLoaderResourceAccessor(getClass().getClassLoader());
    }

}