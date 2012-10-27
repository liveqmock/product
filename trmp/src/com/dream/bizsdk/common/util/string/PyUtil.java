package com.dream.bizsdk.common.util.string;

/**java ��ȡ����ƴ������ĸ
* ����Ŀ��Ҫ���ܸ���ĳЩ��ѯ����������������������ĸ��Ϊ�������в�ѯ�������һ���С����������ˣ��������롮zs'��д��һ�����������£�
* GB 2312-80 ����¼�ĺ��ֳַ���������һ�������ǳ��ú��֣��� 3755 ���� ���� 16��55
* ����������ƴ����ĸ������˳�����У��ڶ��������Ǵγ��ú��֣� �� 3008 �������� 56��87 ���������ף��ʻ�˳�����У����Ա�����ֻ�ܲ鵽
* ��һ�����ֵ���ĸ��ͬʱ�Է�����ĸ��zh��ch��sh��ֻ��ȡ����ĸ��z��c��s��
*/

public class PyUtil {

	// ���������λ��ת������
	static final int GB_SP_DIFF = 160;
	// ��Ź���һ�����ֲ�ͬ��������ʼ��λ��
	static final int[] secPosvalueList = { 1601, 1637, 1833, 2078, 2274, 2302,
			2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
			4086, 4390, 4558, 4684, 4925, 5249, 5600 };

	// ��Ź���һ�����ֲ�ͬ��������ʼ��λ���Ӧ����
	static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
			'y', 'z' };

	// ��ȡһ���ַ�����ƴ����
	public static String getFirstLetter(String oriStr) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) { // ���δ���str��ÿ���ַ�
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0) {// �Ǻ���
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}
	
	/**
	 * ȡǰ���������һ���ַ���ƴ����
	 * @param oriStr
	 * @return
	 */
	public static String get1stLetterOf4Chars(String oriStr) {
		
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char[] temp;
		int strLen = str.length();
		if(strLen > 4){
			str = str.substring(0,3)+str.substring(str.length()-1);
		}
		char ch ;
		for (int i = 0; i < str.length(); i++) { // ���δ���str��ÿ���ַ�
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0) {// �Ǻ���
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	/**
	 * ��ȡһ�����ֵ�ƴ������ĸ�� GB�������ֽڷֱ��ȥ160��ת����10��������ϾͿ��Եõ���λ��
	 * ���纺�֡��㡱��GB����0xC4/0xE3���ֱ��ȥ0xA0��160������0x24/0x43
	 * 0x24ת��10���ƾ���36��0x43��67����ô������λ�����3667���ڶ��ձ��ж���Ϊ��n��
	 */

	public static char convert(byte[] bytes) {

		char result = '-';
		int secPosvalue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}
		secPosvalue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosvalue >= secPosvalueList[i]
					&& secPosvalue < secPosvalueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(PyUtil.get1stLetterOf4Chars("�Ұ�����"));
	}
}
 

