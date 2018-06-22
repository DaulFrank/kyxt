package beans;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

/**
 * Servlet implementation class PaperUpLoad
 * 
 * @MultipartConfig �ϴ���ע��
 */
@WebServlet(description = "�ļ��ϴ�����", urlPatterns = { "/PaperUpLoad" })
@MultipartConfig
public class PaperUpLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PaperUpLoad() {
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
		response.setContentType("application/json");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String header, fileName = null, fileNameEnd = null;
		String[] aa;
		int rd;
		rd = (int) ((Math.random() * 9 + 1) * 10000);

		// request.getSession().getServletContext().getRealPath("/WEB-INF/uploadFile")
		String savePath = "G:\\MyJava\\yttweb\\WebRoot\\WEB-INF\\uploadFile";
		// String savePath = "F:\\uploadFile";
		// ��ȡ�ϴ����ļ�����
		Collection<Part> parts = request.getParts();
		// �ϴ������ļ�
		if (parts.size() == 1) {
			// Servlet3.0��multipart/form-data��POST�����װ��Part��ͨ��Part���ϴ����ļ����в�����
			// Part part = parts[0];//���ϴ����ļ������л�ȡPart����
			Part part = request.getPart("file");// ͨ����file�ؼ�(<input type="file"
												// name="file">)������ֱ�ӻ�ȡPart����
			// Servlet3û���ṩֱ�ӻ�ȡ�ļ����ķ���,��Ҫ������ͷ�н�������
			// ��ȡ����ͷ������ͷ�ĸ�ʽ��form-data; name="file"; filename="snmp4j--api.zip"
			header = part.getHeader("content-disposition");
			// ��ȡ�ļ���
			fileName = getFileName(header);
			aa = fileName.split("\\.");
			fileNameEnd = aa[0] + rd + '.' + aa[1]; // ��д�ļ���
			// ���ļ�д��ָ��·��
			// System.out.println();
			part.write(savePath + File.separator + fileNameEnd);
			// System.out.println(savePath + File.separator + fileName);
		} else {
			// һ�����ϴ�����ļ�
			for (Part part : parts) {// ѭ�������ϴ����ļ�
				// ��ȡ����ͷ������ͷ�ĸ�ʽ��form-data; name="file";
				// filename="snmp4j--api.zip"
				header = part.getHeader("content-disposition");
				// ��ȡ�ļ���
				fileName = getFileName(header);
				// ���ļ�д��ָ��·��
				part.write(savePath + File.separator + fileName);

			}
		}
		FileRes res = new FileRes();
		String json = "";
		Gson gson = new Gson();

		res.setCode(0);
		res.setMsg("");
		res.setSrc(fileNameEnd);
		json = gson.toJson(res);
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	/**
	 * ��������ͷ�������ļ��� ����ͷ�ĸ�ʽ�������google������£�form-data; name="file";
	 * filename="snmp4j--api.zip" IE������£�form-data; name="file";
	 * filename="E:\snmp4j--api.zip"
	 * 
	 * @param header
	 *            ����ͷ
	 * @return �ļ���
	 */
	public String getFileName(String header) {
		/**
		 * String[] tempArr1 =
		 * header.split(";");����ִ����֮���ڲ�ͬ��������£�tempArr1���������������������
		 * �������google������£�tempArr1={form-data,name="file",filename="snmp4j--api.zip"}
		 * IE������£�tempArr1={form-data,name="file",filename="E:\snmp4j--api.zip"}
		 */
		String[] tempArr1 = header.split(";");
		/**
		 * �������google������£�tempArr2={filename,"snmp4j--api.zip"}
		 * IE������£�tempArr2={filename,"E:\snmp4j--api.zip"}
		 */
		String[] tempArr2 = tempArr1[2].split("=");
		// ��ȡ�ļ��������ݸ����������д��
		String fileName = tempArr2[1].substring(tempArr2[1].lastIndexOf("\\") + 1).replaceAll("\"", "");
		return fileName;
	}

}
