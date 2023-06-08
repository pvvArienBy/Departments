package by.it_academy.jd2.Mk_JD2_98_23.dao.api;

import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentShortDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.LocationDTO;

import java.util.List;

public interface IDepartmentDao extends ICRUDDao<DepartmentDTO> {
    DepartmentShortDTO getShort(long id);

    DepartmentShortDTO getParent(long id);

    List<DepartmentShortDTO> getChildren(long id);

    LocationDTO getLocation (long id);
}
