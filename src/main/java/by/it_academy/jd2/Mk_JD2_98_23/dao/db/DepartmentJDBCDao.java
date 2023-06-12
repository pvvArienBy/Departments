package by.it_academy.jd2.Mk_JD2_98_23.dao.db;

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

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DepartmentJDBCDao implements IDepartmentDao {

    private final String INSERT_ADD_DEPARTMENT_SQL = "INSERT INTO app.departments (dep_name, dep_phone, loc_id) VALUES (?, ?, ?)";
    private final String INSERT_ADD_HEAD_SUB_SQL = "INSERT INTO app.head_sub (parent_id, child_id) VALUES (?, ?)";

    private final String GET_PARENT_SQL = "SELECT dep_id, dep_name FROM app.departments " +
            "WHERE dep_id = (SELECT parent_id FROM app.departments dp JOIN app.head_sub hs ON dp.dep_id = hs.child_id WHERE dep_id = ?)";
    private final String GET_CHILDREN_SQL = "SELECT dep_id, dep_name FROM app.departments dp " +
            "JOIN (SELECT child_id FROM app.departments dp JOIN app.head_sub hs ON dp.dep_id = hs.parent_id WHERE dep_id = ?) hs ON  dp.dep_id = hs.child_id";

    private final String GET_LOCATION_SQL = "SELECT loc_id, loc_name FROM app.locations WHERE loc_id = ?";
    private final String GET_DEPARTMENT_SQL = "SELECT dep_id, dep_name, dep_phone, loc_id, loc_name FROM app.departments" +
            " JOIN app.locations USING (loc_id) WHERE dep_id = ?";
    private final String GET_DEPARTMENT_SHORT_SQL = "SELECT dep_name FROM app.departments WHERE dep_id = ?";
    private final String GET_DEPARTMENT_ALL_SQL = "SELECT dep_id, dep_name, dep_phone, loc_id FROM app.departments";



    @Override
    public List<DepartmentDTO> get() {
        List<DepartmentDTO> dtoList = null;
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_DEPARTMENT_ALL_SQL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);
               dtoList.add(new DepartmentDTO(id, name,getParent(id),getChildren(id),rs.getString(3),getLocation(id)));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error get department short version: " + e.getMessage(), e);
        }

        return dtoList;
    }

    @Override
    public DepartmentDTO get(long id) {
        DepartmentDTO dto = null;
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_DEPARTMENT_SQL)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LocationDTO  loc = new LocationDTO(rs.getLong(4),rs.getString(5));
                dto = new DepartmentDTO(id, rs.getString(2),getParent(id),getChildren(id),rs.getString(3),loc);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error get department short version: " + e.getMessage(), e);
        }

        return dto;
    }

    @Override
    public DepartmentShortDTO getShort(long id) {
        DepartmentShortDTO dtoShort = null;
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_DEPARTMENT_SHORT_SQL)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dtoShort = new DepartmentShortDTO(rs.getLong(1), rs.getString(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error get department short version: " + e.getMessage(), e);
        }

        return dtoShort;
    }


    @Override
    public DepartmentDTO add(DepartmentDTO item) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ADD_DEPARTMENT_SQL, RETURN_GENERATED_KEYS)) {

            conn.setAutoCommit(false);

            ps.setString(1, item.getName());
            ps.setString(2, item.getPhoneNumber());
            ps.setLong(3, item.getLocation().getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Не удалось создать отдел, идентификатор не получен.");
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            long depId;
            if (generatedKeys.next()) {
                depId = generatedKeys.getLong(1);
                item.setId(depId);
            } else {
                throw new SQLException("Не удалось создать отдел, идентификатор не получен.");
            }
            conn.commit();

            if (item.getParent() != null) {
                if (!departmentExists(item.getParent().getId())) {
                    throw new RuntimeException("Родительский департамент с идентификатором " + item.getParent() + " не найден в базе данных.");
                }
                addParent(item.getParent().getId(), depId);
            }

            if (!item.getChildren().isEmpty()) {
                for (DepartmentShortDTO childId : item.getChildren()) {
                    if (!departmentExists(childId.getId())) {
                        throw new RuntimeException("Дочерний департамент с идентификатором " + childId + " не найден в базе данных.");
                    }
                }
                    addChildren(depId, item.getChildren());
            }

            return item;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting department: " + e.getMessage(), e);
        }
    }


    private void addParent(long parent_id, long child_id) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_ADD_HEAD_SUB_SQL)) {

            ps.setLong(1, parent_id);
            ps.setLong(2, child_id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting parent: " + e.getMessage(), e);
        }
    }

    @Override
    public DepartmentShortDTO getParent(long id) {
        DepartmentShortDTO dtoParent = null;
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_PARENT_SQL)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dtoParent = new DepartmentShortDTO(rs.getLong(1), rs.getString(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error get parent: " + e.getMessage(), e);
        }

        return dtoParent;
    }

    private void addChildren(long id, List<DepartmentShortDTO> list) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps2 = conn.prepareStatement(INSERT_ADD_HEAD_SUB_SQL)) {

            for (DepartmentShortDTO child : list) {
                ps2.setLong(1, id);
                ps2.setLong(2, child.getId());
                ps2.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting children: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DepartmentShortDTO> getChildren(long id) {
        List<DepartmentShortDTO> listChild;
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_CHILDREN_SQL)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            listChild = new ArrayList<>();
            while (rs.next()) {
                listChild.add(new DepartmentShortDTO(rs.getLong(1), rs.getString(2)));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error get children: " + e.getMessage(), e);
        }

        return listChild;
    }

    @Override
    public LocationDTO getLocation (long id) {
        LocationDTO locationDTO = null;
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_LOCATION_SQL)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                locationDTO = new LocationDTO(rs.getLong(1), rs.getString(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error get parent: " + e.getMessage(), e);
        }

        return locationDTO;
    }


    private boolean departmentExists(long depId) throws SQLException {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM app.departments WHERE dep_id = ?")) {
            ps.setLong(1, depId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            } else {
                return false;
            }
        }
    }
}