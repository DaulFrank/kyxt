package beans;

import java.util.List;

//ά��רҵ��ְ�ƣ�ѧ�Ʒ��࣬��Ŀ��Դ����jsonʹ��
public class ItemJson {
	private int code;
	private String msg;
	private int count;
	private List<Item> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Item> getData() {
		return data;
	}

	public void setData(List<Item> data) {
		this.data = data;
	}

	public ItemJson() {
		super();
		// TODO Auto-generated constructor stub
	}

}
