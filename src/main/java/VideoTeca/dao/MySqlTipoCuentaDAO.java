package VideoTeca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VideoTeca.entidad.TipoCuenta;
import VideoTeca.interfaces.tipocuentaDAO;
import VideoTeca.utils.MySqlConectar;

public class MySqlTipoCuentaDAO implements tipocuentaDAO {

	@Override
    public List<TipoCuenta> findAllTipoCuenta() {
        List<TipoCuenta> lista = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM tipocuenta";
            pstm = cn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                TipoCuenta tipoCuenta = new TipoCuenta();
                tipoCuenta.setIdTipoCuenta(rs.getInt("idTicuenta"));
                tipoCuenta.setTipoCuenta(rs.getString("tipoCuenta"));
                tipoCuenta.setCosto(rs.getInt("costo"));
                lista.add(tipoCuenta);
            }
        } catch (Exception e) {
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

}
