/**
 * ��Ҫ���ַ���������Ϊ��������
 * ������������У�m1��m2��ʵ��������ͬһ������
 * ����������ᷢ���ȽϹ�������󣬱������õ���һ����⣬�ڸ�����д����������ַ�����Hello����
 * �����������Դ�룬���������Լ��Ĵ�����Ҳ������"Hello",��ʱ����п��ܷ����ǳ����������������
 * ��Ϊ��ĳ�������õ�����ⲻ�����ʹ����ͬһ����
 *
 * jetty
 *
 * @author mashibing
 */
package concurrent;

public class T09_DoNotLockString {
	
	String s1 = "Hello";
	String s2 = "Hello";

	void m1() {
		synchronized(s1) {
		
		}
	}
	
	void m2() {
		synchronized(s2) {
		
		}
	}

	

}
