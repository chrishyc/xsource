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
 *
 * ʹ��Latch�����ţ����wait notify������֪ͨ
 * �ô���ͨ�ŷ�ʽ�򵥣�ͬʱҲ����ָ���ȴ�ʱ��
 * ʹ��await��countdown�������wait��notify
 * CountDownLatch���漰��������count��ֵΪ��ʱ��ǰ�̼߳�������
 * �����漰ͬ����ֻ���漰�߳�ͨ�ŵ�ʱ����synchronized + wait/notify���Ե�̫����
 * ��ʱӦ�ÿ���countdownlatch/cyclicbarrier/semaphore
 * @author mashibing
 */
package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

//TODO park unpark

public class y_train_1_LockSupport_WithoutSleep {

	volatile List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	static Thread t1 = null, t2 = null;

	public static void main(String[] args) {
		y_train_1_LockSupport_WithoutSleep c = new y_train_1_LockSupport_WithoutSleep();

		t1 = new Thread(() -> {
			System.out.println("t1����");
			for (int i = 0; i < 10; i++) {
				c.add(new Object());
				System.out.println("add " + i);

				if (c.size() == 5) {
					LockSupport.unpark(t2);
					LockSupport.park();
				}
			}
		}, "t1");

		t2 = new Thread(() -> {
			//System.out.println("t2");
			//if (c.size() != 5) {

				LockSupport.park();

			//}
			System.out.println("t2 ");
			LockSupport.unpark(t1);


		}, "t2");

		t2.start();
		t1.start();





	}
}
