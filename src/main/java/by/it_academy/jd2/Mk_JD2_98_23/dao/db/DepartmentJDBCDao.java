package by.it_academy.jd2.Mk_JD2_98_23.dao.db;

import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.DepartmentShortDTO;
import by.it_academy.jd2.Mk_JD2_98_23.core.dto.LocationDTO;
import by.it_academy.jd2.Mk_JD2_98_23.dao.api.IDepartmentDao;
import by.it_academy.jd2.Mk_JD2_98_23.dao.db.ds.DatabaseConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentJDBCDao implements IDepartmentDao {

    @Override
    public List get() {
        return null;
    }

    @Override
    public Object get(int id) {
        return null;
    }

    public DepartmentDTO add(DepartmentCreateDTO item) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps1 = conn
                     .prepareStatement("INSERT INTO app.departments (dep_name, dep_phone, loc_id) " +
                             "VALUES (?, ?, ?) RETURNING dep_id");
             PreparedStatement ps2 = conn
                     .prepareStatement("INSERT INTO app.head_sub (parent_id, child_id) " +
                             "VALUES (?, ?)");
             PreparedStatement ps3 = conn
                     .prepareStatement("SELECT dep_id, dep_name " +
                             "FROM app.departments " +
                             "WHERE dep_id = (SELECT parent_id " +
                             "FROM app.departments dp " +
                             "JOIN app.head_sub hs ON dp.dep_id = hs.child_id " +
                             "WHERE dep_id = ?);");
             PreparedStatement ps4 = conn
                     .prepareStatement("SELECT dep_id, dep_name " +
                             "FROM app.departments dp " +
                             "JOIN (SELECT child_id " +
                             "FROM app.departments dp " +
                             "JOIN app.head_sub hs ON dp.dep_id = hs.parent_id " +
                             "WHERE dep_id = ?) hs ON  dp.dep_id = hs.child_id");
             PreparedStatement ps5 = conn
                     .prepareStatement("SELECT loc_id, loc_name " +
                             "FROM app.locations " +
                             "WHERE loc_id = ?;")) {

            conn.setAutoCommit(false);

            ps1.setString(1, item.getName());
            ps1.setString(2, item.getPhoneNumber());
            ps1.setLong(3, item.getLocation());

            ResultSet rs = ps1.executeQuery();
            long depId;
            if (rs.next()) {
                depId = rs.getLong(1);
            } else {
                throw new SQLException("Не удалось создать отдел, идентификатор не получен.");
            }
            if (!item.getChildren().isEmpty()) {
                for (Long child : item.getChildren()) {
                    ps2.setLong(1, depId);
                    ps2.setLong(2, child);
                    ps2.executeUpdate();
                }
            }

            if (item.getParent() > 0) {
                ps2.setLong(1, item.getParent());
                ps2.setLong(2, depId);
                ps2.executeUpdate();
            }

            ps3.setLong(1, depId);
            rs = ps3.executeQuery();
            DepartmentShortDTO dtoParent = new DepartmentShortDTO();
            if (rs.next()) {
                dtoParent = new DepartmentShortDTO(rs.getLong(1), rs.getString(2));
            }

            ps4.setLong(1, depId);
            rs = ps4.executeQuery();
            List<DepartmentShortDTO> listChild = new ArrayList<>();
            while (rs.next()) {
                listChild.add(new DepartmentShortDTO(rs.getLong(1), rs.getString(2)));
            }

            ps5.setLong(1, item.getLocation());
            rs = ps5.executeQuery();
            LocationDTO dtoLoc = new LocationDTO();
            if (rs.next()) {
                dtoLoc = new LocationDTO(rs.getLong(1), rs.getString(2));
            }

            conn.commit();

            return new DepartmentDTO(depId, item.getName(), dtoParent, listChild, item.getPhoneNumber(), dtoLoc);
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting department: " + e.getMessage(), e);
        }
    }
}