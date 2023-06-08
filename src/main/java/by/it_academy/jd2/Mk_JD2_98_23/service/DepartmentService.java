package by.it_academy.jd2.Mk_JD2_98_23.service;

import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentShortDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.LocationDTO;
import by.it_academy.jd2.Mk_JD2_98_23.dao.api.IDepartmentDao;
import by.it_academy.jd2.Mk_JD2_98_23.service.api.IDepartmentService;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService implements IDepartmentService {

    IDepartmentDao departmentDao;

    public DepartmentService(IDepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public DepartmentDTO add(DepartmentCreateDTO item) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName(item.getName());
        dto.setParent(getParent(item.getParent()));

        List<DepartmentShortDTO> listShortDto = new ArrayList<>();
        for (Long child : item.getChildren()) {
            listShortDto.add(getShort(child));
        }
        dto.setChildren(listShortDto);
        dto.setPhoneNumber(item.getPhoneNumber());
        dto.setLocation(departmentDao.getLocation(item.getLocation()));

        return departmentDao.add(dto);
    }

    @Override
    public DepartmentShortDTO getShort(long id) {
        return departmentDao.getShort(id);
    }

    @Override
    public DepartmentShortDTO getParent(long id) {
        return departmentDao.getParent(id);
    }

    @Override
    public List<DepartmentShortDTO> getChildren(long id) {
        return departmentDao.getChildren(id);
    }

    @Override
    public LocationDTO getLocation(long id) {
        return departmentDao.getLocation(id);
    }


    @Override
    public List<DepartmentDTO> get() {
        return departmentDao.get();
    }

    @Override
    public DepartmentDTO get(int id) {
        return departmentDao.get(id);
    }

    @Override
    public DepartmentDTO get(DepartmentCreateDTO item) {
        return null;
    }
}
