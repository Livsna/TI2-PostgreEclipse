package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO extends DAO {
	
	public PlayerDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Player player) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO usuario (nick, rank, main, idade) "
				       + "VALUES ("+player.getNick()+ ", '" + player.getRank() + "', '"  
				       + player.getRank() + "', '" + player.getIdade() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Player get(String nick) {
		Player player = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE id=" + player;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 player = new Player(rs.getString("nick"), rs.getString("rank"), rs.getString("main"), rs.getInt("idade"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return player;
	}
	
	
	public List<Player> getOrderByRank() {
		return get("rank");
	}

	
	public List<Player> getOrderByMain() {
		return get("main");		
	}
	
	
	public List<Player> getOrderByNick() {
		return get("nick");		
	}
	
	
	public List<Player> getOrderByIdade() {
		return get("idade");		
	}
	
	
	private List<Player> get(String orderBy) {	
	
		List<Player> players = new ArrayList<Player>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Player u = new Player(rs.getString("nick"), rs.getString("rank"), rs.getString("main"), rs.getInt("idade"));
	            usuarios.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	public boolean update(Player player) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE usuario SET nick = '" + player.getNick() + "', rank = '"  
				       + player.getRank() + "', main = '" + player.getMain() + "'"
					   + " WHERE idade = " + player.getIdade();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(String nick) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM usuario WHERE codigo = " + nick;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean autenticar(String login, String senha) {
		boolean resp = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE login LIKE '" + login + "' AND senha LIKE '" + senha  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}	
}
