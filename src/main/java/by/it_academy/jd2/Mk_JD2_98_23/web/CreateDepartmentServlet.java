package by.it_academy.jd2.Mk_JD2_98_23.web;

import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentDTO;
import by.it_academy.jd2.Mk_JD2_98_23.service.api.IDepartmentService;
import by.it_academy.jd2.Mk_JD2_98_23.service.factory.DepartmentServiceFactory;
import by.it_academy.jd2.Mk_JD2_98_23.service.factory.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;


@WebServlet(urlPatterns = "/api/add-dep")
public class CreateDepartmentServlet extends HttpServlet {

    private final IDepartmentService departmentService;
    private final ObjectMapper objectMapper;

    public CreateDepartmentServlet() {
        this.departmentService = DepartmentServiceFactory.getInstance();
        this.objectMapper = ObjectMapperFactory.getInstance();
        this.objectMapper.findAndRegisterModules();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        InputStream requestBody = req.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        DepartmentCreateDTO departmentCreateDTO = mapper.readValue(requestBody, DepartmentCreateDTO.class);

        DepartmentDTO departmentDTO = departmentService.add(departmentCreateDTO);

        resp.getWriter().write(mapper.writeValueAsString(departmentDTO));
    }
}
