package by.it_academy.jd2.Mk_JD2_98_23.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentDTO;

public interface IDepartmentService extends ICRUDService {
    DepartmentDTO add (DepartmentCreateDTO item);
}
