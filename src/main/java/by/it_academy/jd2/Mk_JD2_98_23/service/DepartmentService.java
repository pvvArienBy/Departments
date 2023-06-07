package by.it_academy.jd2.Mk_JD2_98_23.service;

import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentDTO;
import by.it_academy.jd2.Mk_JD2_98_23.dao.api.IDepartmentDao;
import by.it_academy.jd2.Mk_JD2_98_23.service.api.IDepartmentService;

import java.util.List;

public class DepartmentService implements IDepartmentService {

    IDepartmentDao departmentDao;

    public DepartmentService(IDepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public List get() {
        return departmentDao.get();
    }

    @Override
    public Object get(int id) {
        return departmentDao.get(id);
    }

    @Override
    public DepartmentDTO add(DepartmentCreateDTO item) {
        return departmentDao.add(item);
    }
}
