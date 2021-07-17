/**
 * �����������⣺���Ա�����
 * ʵ��һ���������ṩ����������add��size
 * д�����̣߳��߳�1���10��Ԫ�ص������У��߳�2ʵ�ּ��Ԫ�صĸ�������������5��ʱ���߳�2������ʾ������
 *
 * ��lists���volatile֮��t2�ܹ��ӵ�֪ͨ�����ǣ�t2�̵߳���ѭ�����˷�cpu�����������ѭ��������ô���أ�
 *
 * ����ʹ��wait��notify������wait���ͷ�������notify�����ͷ���
 * ��Ҫע����ǣ��������ַ���������Ҫ��֤t2��ִ�У�Ҳ����������t2�����ſ���
 *
 * �Ķ�����ĳ��򣬲�����������
 * ���Զ���������������size=5ʱt2�˳�������t1����ʱt2�Ž��յ�֪ͨ���˳�
 * ��������Ϊʲô��
 *
 * notify֮��t1�����ͷ�����t2�˳���Ҳ����notify��֪ͨt1����ִ��
 * ����ͨ�Ź��̱ȽϷ���
 * @author mashibing
 */
package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class y_train_1_NotifyFreeLock {

	volatile List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}
	
	public static void main(String[] args) {
		y_train_1_NotifyFreeLock c = new y_train_1_NotifyFreeLock();
		
		final Object lock = new Object();
		
		new Thread(() -> {
			synchronized(lock) {
				System.out.println("t2 enter");
				if(c.size() != 5) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("t2 exit");
				lock.notify();
			}
			
		}, "t2").start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		new Thread(() -> {
			System.out.println("t1 enter");
			synchronized(lock) {
				for(int i=0; i<10; i++) {
					c.add(new Object());
					System.out.println("add " + i);
					
					if(c.size() == 5) {
						lock.notify();
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "t1").start();
		
		
	}
}
