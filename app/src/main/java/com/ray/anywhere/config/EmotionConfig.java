package com.ray.anywhere.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ray.anywhere.R;

public class EmotionConfig {
	
	public static final int NUM_PAGE = 6;// �ܹ��ж���ҳ
	public static final int NUM = 20;// ÿҳ20������,�������һ��ɾ��button
	private Map<String, Integer> mFaceMap = new LinkedHashMap<String, Integer>();
	
	public EmotionConfig(){
		initFaceMap();
	}
	
	public Map<String, Integer> getFaceMap() {
		if (!mFaceMap.isEmpty())
			return mFaceMap;
		return null;
	}
	
	private void initFaceMap() {
		// TODO Auto-generated method stub
		mFaceMap.put("[����]", R.drawable.f000);
		mFaceMap.put("[��Ƥ]", R.drawable.f001);
		mFaceMap.put("[����]", R.drawable.f002);
		mFaceMap.put("[͵Ц]", R.drawable.f003);
		mFaceMap.put("[�ټ�]", R.drawable.f004);
		mFaceMap.put("[�ô�]", R.drawable.f005);
		mFaceMap.put("[����]", R.drawable.f006);
		mFaceMap.put("[��ͷ]", R.drawable.f007);
		mFaceMap.put("[õ��]", R.drawable.f008);
		mFaceMap.put("[����]", R.drawable.f009);
		mFaceMap.put("[���]", R.drawable.f010);
		mFaceMap.put("[��]", R.drawable.f011);
		mFaceMap.put("[��]", R.drawable.f012);
		mFaceMap.put("[ץ��]", R.drawable.f013);
		mFaceMap.put("[ί��]", R.drawable.f014);
		mFaceMap.put("[���]", R.drawable.f015);
		mFaceMap.put("[ը��]", R.drawable.f016);
		mFaceMap.put("[�˵�]", R.drawable.f017);
		mFaceMap.put("[�ɰ�]", R.drawable.f018);
		mFaceMap.put("[ɫ]", R.drawable.f019);
		mFaceMap.put("[����]", R.drawable.f020);

		mFaceMap.put("[����]", R.drawable.f021);
		mFaceMap.put("[��]", R.drawable.f022);
		mFaceMap.put("[΢Ц]", R.drawable.f023);
		mFaceMap.put("[��ŭ]", R.drawable.f024);
		mFaceMap.put("[����]", R.drawable.f025);
		mFaceMap.put("[����]", R.drawable.f026);
		mFaceMap.put("[�亹]", R.drawable.f027);
		mFaceMap.put("[����]", R.drawable.f028);
		mFaceMap.put("[ʾ��]", R.drawable.f029);
		mFaceMap.put("[����]", R.drawable.f030);
		mFaceMap.put("[����]", R.drawable.f031);
		mFaceMap.put("[�ѹ�]", R.drawable.f032);
		mFaceMap.put("[����]", R.drawable.f033);
		mFaceMap.put("[����]", R.drawable.f034);
		mFaceMap.put("[˯]", R.drawable.f035);
		mFaceMap.put("[����]", R.drawable.f036);
		mFaceMap.put("[��Ц]", R.drawable.f037);
		mFaceMap.put("[����]", R.drawable.f038);
		mFaceMap.put("[˥]", R.drawable.f039);
		mFaceMap.put("[Ʋ��]", R.drawable.f040);
		mFaceMap.put("[����]", R.drawable.f041);

		mFaceMap.put("[�ܶ�]", R.drawable.f042);
		mFaceMap.put("[����]", R.drawable.f043);
		mFaceMap.put("[�Һߺ�]", R.drawable.f044);
		mFaceMap.put("[ӵ��]", R.drawable.f045);
		mFaceMap.put("[��Ц]", R.drawable.f046);
		mFaceMap.put("[����]", R.drawable.f047);
		mFaceMap.put("[����]", R.drawable.f048);
		mFaceMap.put("[��]", R.drawable.f049);
		mFaceMap.put("[���]", R.drawable.f050);
		mFaceMap.put("[����]", R.drawable.f051);
		mFaceMap.put("[ǿ]", R.drawable.f052);
		mFaceMap.put("[��]", R.drawable.f053);
		mFaceMap.put("[����]", R.drawable.f054);
		mFaceMap.put("[ʤ��]", R.drawable.f055);
		mFaceMap.put("[��ȭ]", R.drawable.f056);
		mFaceMap.put("[��л]", R.drawable.f057);
		mFaceMap.put("[��]", R.drawable.f058);
		mFaceMap.put("[����]", R.drawable.f059);
		mFaceMap.put("[����]", R.drawable.f060);
		mFaceMap.put("[ơ��]", R.drawable.f061);
		mFaceMap.put("[Ʈ��]", R.drawable.f062);

		mFaceMap.put("[����]", R.drawable.f063);
		mFaceMap.put("[OK]", R.drawable.f064);
		mFaceMap.put("[����]", R.drawable.f065);
		mFaceMap.put("[����]", R.drawable.f066);
		mFaceMap.put("[Ǯ]", R.drawable.f067);
		mFaceMap.put("[����]", R.drawable.f068);
		mFaceMap.put("[��Ů]", R.drawable.f069);
		mFaceMap.put("[��]", R.drawable.f070);
		mFaceMap.put("[����]", R.drawable.f071);
		mFaceMap.put("[�]", R.drawable.f072);
		mFaceMap.put("[ȭͷ]", R.drawable.f073);
		mFaceMap.put("[����]", R.drawable.f074);
		mFaceMap.put("[̫��]", R.drawable.f075);
		mFaceMap.put("[����]", R.drawable.f076);
		mFaceMap.put("[����]", R.drawable.f077);
		mFaceMap.put("[����]", R.drawable.f078);
		mFaceMap.put("[����]", R.drawable.f079);
		mFaceMap.put("[����]", R.drawable.f080);
		mFaceMap.put("[����]", R.drawable.f081);
		mFaceMap.put("[��]", R.drawable.f082);
		mFaceMap.put("[����]", R.drawable.f083);

		mFaceMap.put("[��ĥ]", R.drawable.f084);
		mFaceMap.put("[�ٱ�]", R.drawable.f085);
		mFaceMap.put("[����]", R.drawable.f086);
		mFaceMap.put("[�ܴ���]", R.drawable.f087);
		mFaceMap.put("[��ߺ�]", R.drawable.f088);
		mFaceMap.put("[��Ƿ]", R.drawable.f089);
		mFaceMap.put("[�����]", R.drawable.f090);
		mFaceMap.put("[��]", R.drawable.f091);
		mFaceMap.put("[����]", R.drawable.f092);
		mFaceMap.put("[ƹ����]", R.drawable.f093);
		mFaceMap.put("[NO]", R.drawable.f094);
		mFaceMap.put("[����]", R.drawable.f095);
		mFaceMap.put("[���]", R.drawable.f096);
		mFaceMap.put("[תȦ]", R.drawable.f097);
		mFaceMap.put("[��ͷ]", R.drawable.f098);
		mFaceMap.put("[��ͷ]", R.drawable.f099);
		mFaceMap.put("[����]", R.drawable.f100);
		mFaceMap.put("[����]", R.drawable.f101);
		mFaceMap.put("[����]", R.drawable.f102);
		mFaceMap.put("[����]", R.drawable.f103);
		mFaceMap.put("[��̫��]", R.drawable.f104);

		mFaceMap.put("[��̫��]", R.drawable.f105);
		mFaceMap.put("[����]", R.drawable.f106);
	}

}
