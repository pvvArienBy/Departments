package by.it_academy.jd2.Mk_JD2_98_23.service.factory;

import by.it_academy.jd2.Mk_JD2_98_23.dao.factory.DepartmentDaoFactory;
import by.it_academy.jd2.Mk_JD2_98_23.service.DepartmentService;
import by.it_academy.jd2.Mk_JD2_98_23.service.api.IDepartmentService;

public class DepartmentServiceFactory {
    private static volatile IDepartmentService instance;

    private DepartmentServiceFactory() {
    }

    public static IDepartmentService getInstance() {
        if (instance == null)  {
            synchronized (DepartmentServiceFactory.class) {
                if (instance == null) {
                    instance =new DepartmentService(DepartmentDaoFactory.getInstance());
                }
            }

        }
        return instance;
    }
}
