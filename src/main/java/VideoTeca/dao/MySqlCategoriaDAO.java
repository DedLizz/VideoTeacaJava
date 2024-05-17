package VideoTeca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VideoTeca.entidad.Categoria;
import VideoTeca.interfaces.categoriaDAO;
import VideoTeca.utils.MySqlConectar;

public class MySqlCategoriaDAO implements categoriaDAO {

	@Override
    public int save(Categoria bean) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "INSERT INTO categoria (nombreCate) VALUES (?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, bean.getNombreCategoria());
            salida = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return salida;
    }
	
	
	@Override
    public int update(Categoria bean) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "UPDATE categoria SET nombreCate=? WHERE idCategoria=?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, bean.getNombreCategoria());
            pstm.setInt(2, bean.getIdCategoria());
            salida = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return salida;
    }

	@Override
    public int deleteById(int id) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "DELETE FROM categoria WHERE idCategoria=?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, id);
            salida = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return salida;
    }

	@Override
    public Categoria findById(int id) {
        Categoria categoria = null;
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM categoria WHERE idCategoria = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNombreCategoria(rs.getString("nombreCate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categoria;
    }
	
	
	@Override
    public List<Categoria> findAllCategoria() {
    List<Categoria> lista = new ArrayList<>();
    Connection cn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM categoria";
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNombreCategoria(rs.getString("nombreCate"));
                lista.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
	
	@Override
    public boolean categoriaExistente(String nombreCategoria) {
        PreparedStatement pstm = null;
        Connection cn = null;
        ResultSet rs = null;
        boolean existe = false;

        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT COUNT(*) FROM categoria WHERE nombreCate = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, nombreCategoria);
            rs = pstm.executeQuery();

            if(rs.next() && rs.getInt(1) > 0) {
                existe = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstm != null)
                    pstm.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return existe;
    }

}
