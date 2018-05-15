package paper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownLoadFile
 */
@WebServlet(description = "�����ļ�", urlPatterns = { "/DownLoadFile" })
public class DownLoadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownLoadFile() {
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
		// �ļ���·��"G:\\MyJava\\yttweb\\WebRoot\\WEB-INF\\uploadFile"
		// response.setContentType("application/json");
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String savepath = "G:/MyJava/yttweb/WebRoot/WEB-INF/uploadFile/";
		String fileurl = request.getParameter("filename");
		// System.out.println(fileurl);
		String filename = savepath + fileurl;

		// ��������ļ���

		// System.out.println(filename);

		// �����ļ�MIME����
		response.setContentType(getServletContext().getMimeType(filename));
		// ����Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		// ��ȡĿ���ļ���ͨ��response��Ŀ���ļ�д���ͻ���
		// ��ȡĿ���ļ��ľ���·��

		// System.out.println(fullFileName);
		// ��ȡ�ļ�
		InputStream in = new FileInputStream(filename);
		OutputStream out = response.getOutputStream();

		// д�ļ�
		int b;
		while ((b = in.read()) != -1) {
			out.write(b);
		}

		in.close();
		out.close();
	}

}
