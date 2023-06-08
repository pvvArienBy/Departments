package by.it_academy.jd2.Mk_JD2_98_23.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentShortDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.LocationDTO;

import java.util.List;

public interface IDepartmentService extends ICRUDService<DepartmentDTO, DepartmentCreateDTO> {

    DepartmentShortDTO getShort(long id);

    DepartmentShortDTO getParent(long id);

    List<DepartmentShortDTO> getChildren(long id);

    LocationDTO getLocation (long id);
}
