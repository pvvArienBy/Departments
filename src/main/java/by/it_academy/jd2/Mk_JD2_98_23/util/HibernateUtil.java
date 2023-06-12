package by.it_academy.jd2.Mk_JD2_98_23.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static final EntityManagerFactory emFactory;

    static {
        emFactory = Persistence.createEntityManagerFactory("by.it");
    }

    public static EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

    public static void close() {
        emFactory.close();
    }
}
