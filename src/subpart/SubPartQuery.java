package subpart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Item;
import beans.ItemJson;
import db.Db;

/**
 * Servlet implementation class SubPartQuery
 */
@WebServlet(description = "ѧ�Ʒ�����Ϣ��ѯ", urlPatterns = { "/SubPartQuery" })
public class SubPartQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubPartQuery() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodname = request.getParameter("methodname");
		if (methodname != null) {
			switch (methodname) {

			case "deletesingle":
				deletesingle(request, response);
				break;
			default:
				queryList(request, response);
			}

		} else {
			queryList(request, response);

		}
	}

	private void queryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		String json = "";
		int numbers = 0;
		Db db = new Db();
		String id = request.getParameter("id");
		String name = request.getParameter("subpartname");

		int limit = Integer.parseInt(request.getParameter("limit"));
		int page = Integer.parseInt(request.getParameter("page"));
		int offset = limit * (page - 1);
		String sql = ""; // ��ѯ�����ݵ�����sqlƴ�����
		String sqlf = "select id,subpartname from subpart where 1=1"; // sqlͷ��
		// ��ҳ��ѯ
		String sqle = "  order by id limit " + offset + "," + limit + ""; // sqlβ��
		String sql1 = "select count(*) numbers from subpart where 1=1  "; // �õ�countֵ���ܽ��ֵ
		String str = "";
		if (id == "" || id == null) {

		} else {
			str = str + " and id like '%" + id + "%'";
		}

		if (name == "" || name == null) {

		} else {
			str = str + " and subpartname like '%" + name + "%'";
		}

		sql1 = sql1 + str;
		sql = sqlf + str + sqle;
		// System.out.println(sql);
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = db.getPs(sql1);
			rs = ps.executeQuery();
			if (rs.next()) {
				numbers = rs.getInt("numbers");
			} else {
				out.print("<script>alert('δ֪����');window.history.go(-1);</script>");
				return;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<Item> ItemList = new ArrayList<Item>();
		ItemJson ItemJson = new ItemJson();
		ItemJson.setCode(0);
		ItemJson.setCount(numbers);
		ItemJson.setMsg("");

		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = db.getPs(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt(1));
				item.setName(rs.getString(2));

				ItemList.add(item);
			}
			ItemJson.setData(ItemList);
			json = gson.toJson(ItemJson);
			// System.out.println(json);
			rs.close();
			ps.close();
			db.getConnect().close();
			out.print(json);
			out.flush();
			out.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private void deletesingle(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
