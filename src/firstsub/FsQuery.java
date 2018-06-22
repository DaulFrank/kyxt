package firstsub;

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

import beans.ItemB;
import beans.ResJson;
import db.Db;

/**
 * Servlet implementation class FsQuery
 */
@WebServlet(description = "һ��ѧ�Ʋ�ѯ", urlPatterns = { "/FsQuery" })
public class FsQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FsQuery() {
		super();

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

	private void deletesingle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Db db = new Db();
		String sql = "delete from firstsub where id = ?";
		int id = Integer.parseInt(request.getParameter("id")); // ��ȡҪɾ���Ľ�ʦ��id(empNum)
		String sql1 = "select * from paper where firstsubid = ?";
		PreparedStatement ps = db.getPs(sql1);
		try {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				out.print("404"); // ����ɾ����������
			} else {
				int row = 0;
				ps = db.getPs(sql);
				ps.setInt(1, id);
				row = ps.executeUpdate();
				if (row != 0) {
					out.print("1"); // ɾ���ɹ�
				} else {
					out.print("0"); // ֱ�ӷ���ֻ�ܷ������֣�����Ҫ����json����
				}
				ps.close();
				db.getConnect().close();

			}
			out.flush();
			out.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		String name = request.getParameter("firstsubname");
		String partid = request.getParameter("partid");

		int limit = Integer.parseInt(request.getParameter("limit"));
		int page = Integer.parseInt(request.getParameter("page"));
		int offset = limit * (page - 1);
		String sql = ""; // ��ѯ�����ݵ�����sqlƴ�����
		String sqlf = "select firstsub.id,firstsubname,subpartname from firstsub,subpart where (firstsub.subpartid = subpart.id)"; // sqlͷ��
		// ��ҳ��ѯ
		String sqle = "  order by firstsub.id limit " + offset + "," + limit + ""; // sqlβ��
		String sql1 = "select count(*) numbers from firstsub,subpart where (firstsub.subpartid = subpart.id)  "; // �õ�countֵ���ܽ��ֵ
		String str = "";
		if (id == "" || id == null) {

		} else {
			str = str + " and firstsub.id like '%" + id + "%'";
		}

		if (name == "" || name == null) {

		} else {
			str = str + " and firstsubname like '%" + name + "%'";
		}
		if (partid == "" || partid == null) {

		} else {
			str = str + " and subpartid like '%" + partid + "%'";
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

		List<ItemB> ItembList = new ArrayList<ItemB>();
		ResJson resjson = new ResJson();
		resjson.setCode(0);
		resjson.setCount(numbers);
		resjson.setMsg("");

		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = db.getPs(sql);
			ps = db.getPs(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ItemB itemb = new ItemB();
				itemb.setId(rs.getInt(1));
				itemb.setName(rs.getString(2));
				itemb.setForname(rs.getString(3));
				ItembList.add(itemb);
			}
			resjson.setData(ItembList);
			json = gson.toJson(resjson);
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

}
