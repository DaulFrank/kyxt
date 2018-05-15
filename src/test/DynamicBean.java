package test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

public class DynamicBean {

	/**
	 * ʵ��Object
	 */
	private Object object = null;

	/**
	 * ����map
	 */
	private BeanMap beanMap = null;

	public DynamicBean() {
		super();
	}

	@SuppressWarnings("unchecked")
	public DynamicBean(Map propertyMap) {
		this.object = generateBean(propertyMap);
		this.beanMap = BeanMap.create(this.object);
	}

	/**
	 * ��bean���Ը�ֵ
	 * 
	 * @param property
	 *            ������
	 * @param value
	 *            ֵ
	 */
	public void setValue(String property, Object value) {
		beanMap.put(property, value);
	}

	/**
	 * ͨ���������õ�����ֵ
	 * 
	 * @param property
	 *            ������
	 * @return ֵ
	 */
	public Object getValue(String property) {
		return beanMap.get(property);
	}

	/**
	 * �õ���ʵ��bean����
	 * 
	 * @return
	 */
	public Object getObject() {
		return this.object;
	}

	/**
	 * @param propertyMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object generateBean(Map propertyMap) {
		BeanGenerator generator = new BeanGenerator();
		Set keySet = propertyMap.keySet();
		for (Iterator i = keySet.iterator(); i.hasNext();) {
			String key = (String) i.next();
			generator.addProperty(key, (Class) propertyMap.get(key));
		}
		return generator.create();
	}

	public static void main(String[] args) throws ClassNotFoundException {

		// �������Ա����
		HashMap propertyMap = new HashMap();

		propertyMap.put("id", Class.forName("java.lang.Integer"));

		propertyMap.put("name", Class.forName("java.lang.String"));

		propertyMap.put("address", Class.forName("java.lang.String"));

		// ���ɶ�̬ Bean
		DynamicBean bean = new DynamicBean(propertyMap);

		// �� Bean ����ֵ
		bean.setValue("id", new Integer(123));

		bean.setValue("name", "454");

		bean.setValue("address", "789");

		// �� Bean �л�ȡֵ����Ȼ�˻��ֵ�������� Object

		// System.out.println(" >> id = " + bean.getValue("id"));

		// System.out.println(" >> name = " + bean.getValue("name"));

		// System.out.println(" >> address = " + bean.getValue("address"));

		// ���bean��ʵ��
		Object object = bean.getObject();

		// ͨ������鿴���з�����
		Class clazz = object.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// System.out.println(methods[i].getName());
		}
	}

}
