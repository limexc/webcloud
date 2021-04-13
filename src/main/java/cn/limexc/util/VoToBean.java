package cn.limexc.util;

import cn.limexc.model.UserFile;

import org.springframework.stereotype.Component;

@Component
public class VoToBean {
	public static UserFile fileimToUserFile(UserFile fim, Integer uid, String currentpath) {
		UserFile uf = new UserFile();
		uf.setFilesize(fim.getFilesize());
		uf.setIconsign(fim.getIconsign());
		uf.setUptime(fim.getUptime());
		uf.setUid(uid);
		//uf.setVfname(fim.getVfname());
		uf.setVpath(currentpath+fim.getVfname());
		return uf;
	}
}
