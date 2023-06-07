package by.it_academy.jd2.Mk_JD2_98_23.dao.factory;

import by.it_academy.jd2.Mk_JD2_98_23.dao.api.IDepartmentDao;
import by.it_academy.jd2.Mk_JD2_98_23.dao.db.DepartmentJDBCDao;

public class DepartmentDaoFactory {
    private static volatile IDepartmentDao instance;

    private DepartmentDaoFactory() {
    }

    public static IDepartmentDao getInstance() {
        if (instance == null)  {
            synchronized (DepartmentDaoFactory.class) {
                if (instance == null) {
                    instance = new DepartmentJDBCDao();
                }
            }

        }

        return instance;
    }
}
