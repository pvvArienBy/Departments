package by.it_academy.jd2.Mk_JD2_98_23.dao.db.ds;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    private static volatile DatabaseConnectionFactory instance;
    private static ComboPooledDataSource cpds;

    private DatabaseConnectionFactory() {}

    public static DatabaseConnectionFactory getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnectionFactory.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionFactory();
                }
            }
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        if (cpds == null) {
            synchronized (DatabaseConnectionFactory.class) {
                if (cpds == null) {
                    cpds = new ComboPooledDataSource();
                    try {
                        cpds.setDriverClass("org.postgresql.Driver");
                        cpds.setJdbcUrl("jdbc:postgresql://localhost:5433/dep");
                        cpds.setUser("postgres123");
                        cpds.setPassword("postgres123");
                    } catch (PropertyVetoException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return cpds.getConnection();
    }
}