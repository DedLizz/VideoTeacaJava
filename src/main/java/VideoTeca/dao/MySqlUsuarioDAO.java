package VideoTeca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import VideoTeca.entidad.Usuario;
import VideoTeca.interfaces.usuarioDAO;
import VideoTeca.utils.MySqlConectar;

public class MySqlUsuarioDAO implements usuarioDAO {

	@Override
	public int save(Usuario bean) {
		int salida = -1;
		Connection cn = null;
		PreparedStatement pstm = null;
		try {
			cn = new MySqlConectar().getConnection();
			String sql = "INSERT INTO usuario (nombreU, apellido, contrasena, Email, idTipoCuenta) VALUES (?,?,?,?,?)";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, bean.getNombreUsuario());
			pstm.setString(2, bean.getApellidoUsuario());
			pstm.setString(3, bean.getContraUsuario());
			pstm.setString(4, bean.getEmailUsuaio());
			pstm.setInt(5, bean.getTipoCuentaUsuario());
			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstm != null)
					pstm.close();
				if(cn != null)
					cn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return salida;	
	}

	@Override
	public int update(Usuario bean) {
		int salida = -1;
		Connection cn = null;
		PreparedStatement pstm = null;
		try {
			// 1. Obtener  conexión
			cn = new MySqlConectar().getConnection();
			// 2. Sentencia AQL
			String sql = "UPDATE usuario SET nombreU=?, apellido=?, contrasena=?, Email=?, idTipoCuenta=? WHERE idUsuario=?";
			// 3. Crear objeto "pstm" y enviar la variable "sql"
			pstm = cn.prepareStatement(sql);
			// 4. parametros
			pstm.setString(1, bean.getNombreUsuario());
			pstm.setString(2,  bean.getApellidoUsuario());
			pstm.setString(3, bean.getContraUsuario());
			pstm.setString(4, bean.getEmailUsuaio());
			pstm.setInt(5, bean.getTipoCuentaUsuario());
			// 5. Ejecutar "pstm"
			// si la sentencia UPDATE se ejecuta correctamente, el metodo executeUpdate retorna un valor mayor a 0
			salida = pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (pstm != null)
					pstm.close();
				if ( cn != null)
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
			// 1. Obtener conexion
			cn = new MySqlConectar().getConnection();
			// 2. Sentecia SQL
			String sql = "DELETE FROM usuario WHERE idUsuario=?";
			// 3. crear objeto "pstm" y enviar la variable "sql"
			pstm = cn.prepareStatement(sql);
			// 4. parametros
			pstm.setInt(1, id);
			// 5. Ejecutar "pstm"
			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if ( cn != null)
					cn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return salida;
	}

	
	public Usuario findUsuarioById(int idUsuario) {
	    Usuario user = null;
	    Connection cn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;
	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
	        pstm = cn.prepareStatement(sql);
	        pstm.setInt(1, idUsuario);
	        rs = pstm.executeQuery();
	        if (rs.next()) {
	            user = new Usuario();
	            user.setIdUsuario(rs.getInt("idUsuario"));
	            user.setNombreUsuario(rs.getString("nombreU"));
	            user.setApellidoUsuario(rs.getString("apellido"));
	            user.setContraUsuario(rs.getString("contrasena"));
	            user.setEmailUsuaio(rs.getString("Email"));
	            user.setTipoCuentaUsuario(rs.getInt("idTipoCuenta"));
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
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	    return user;
	}


	@Override
	public List<Usuario> findAllUsuario() {
		List<Usuario> lista = new ArrayList<Usuario>();
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			cn = new MySqlConectar().getConnection();
			String sql = "SELECT * FROM usuario";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Usuario user = new Usuario();
				user.setIdUsuario(rs.getInt("idUsuario"));
				user.setNombreUsuario(rs.getString("nombreU"));
				user.setApellidoUsuario(rs.getString("apellido"));
				user.setContraUsuario(rs.getString("contrasena"));
				user.setEmailUsuaio(rs.getString("Email"));
				user.setTipoCuentaUsuario(rs.getInt("idTipoCuenta"));
				lista.add(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if ( cn != null)
					cn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return lista;
	}
	
	public boolean usuarioExistente(String email) { 
	    PreparedStatement pstm = null;
	    Connection cn = null;
	    ResultSet rs = null;
	    boolean existe = false;

	    try {
	        cn = new MySqlConectar().getConnection();
	        String sql = "SELECT COUNT(*) FROM usuario WHERE Email = ?"; 
	        pstm = cn.prepareStatement(sql);
	        pstm.setString(1, email); 
	        rs = pstm.executeQuery();

	        if(rs.next() && rs.getInt(1) > 0) {
	            existe = true;
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
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	    return existe;
	}
	
    // Método para iniciar sesión y obtener datos del usuario
    public static Usuario iniciarSesion(String email, String contrasena) {
        PreparedStatement pstm = null;
        Connection cn = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            cn = new MySqlConectar().getConnection();
            String sql = "SELECT * FROM usuario WHERE Email = ? AND contrasena = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, email);
            pstm.setString(2, contrasena);
            rs = pstm.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombreUsuario(rs.getString("nombreU"));;
                usuario.setApellidoUsuario(rs.getString("apellido"));
                usuario.setEmailUsuaio(rs.getString("Email"));
                usuario.setTipoCuentaUsuario(rs.getInt("idTipoCuenta"));
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
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return usuario;
    }

    public int updateTipoCuenta(int idUsuario, int nuevoIdTipoCuenta) {
        int salida = -1;
        Connection cn = null;
        PreparedStatement pstm = null;
        try {
            // 1. Obtener conexión
            cn = new MySqlConectar().getConnection();
            // 2. Sentencia SQL
            String sql = "UPDATE usuario SET idTipoCuenta=? WHERE idUsuario=?";
            // 3. Crear objeto "pstm" y enviar la variable "sql"
            pstm = cn.prepareStatement(sql);
            // 4. Parámetros
            pstm.setInt(1, nuevoIdTipoCuenta);
            pstm.setInt(2, idUsuario);
            // 5. Ejecutar "pstm"
            // si la sentencia UPDATE se ejecuta correctamente, el método executeUpdate retorna un valor mayor a 0
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


}
