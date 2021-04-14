package cn.limexc.util;

import cn.limexc.model.UserFile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PathAnalysis {

    /**
     * 有问题，因为是前字符串匹配，所以前面相等的都会被涉及到
     * 	 * 如 /文件夹A
     * 	 *    /文件夹B
     * 	 *    /文件夹
     * 	 * 当删除 文件夹 时这 三个都会被删除！！！
     * 初步的修改想法：
     * 		1.通过拆分后的字符串数组进行匹配
     * 		2.通过前匹配后验证后面是否是“/”或后面为空
     * 	    3.判断后面是否是"/"或 内容是否一致
     *
     */

    public List<UserFile> getNewFloder(List<UserFile> ufs, String currentpath, int page){
        List<UserFile> fms = new ArrayList<UserFile>();
        Map<String, String> m = new Hashtable<String, String>();
        for (int i = 0; i < ufs.size(); i++) {
            UserFile temp = ufs.get(i);
            String path = temp.getVpath();
            String[] temps = path.split("/");
            char[] cpath = path.toCharArray();
            int l = currentpath.length();

            if (path.startsWith(currentpath)) {
                if (path.equals(currentpath)|| cpath[l] == '/' ) {
                    if (m.get(temps[page+1]) == null) {
                        UserFile fm = new UserFile();
                        fm.setVfname(temps[page+1]);
                        m.put(temps[page+1], "");
                        fm.setFilesize(temp.getFilesize());
                        fm.setIconsign(temp.getIconsign());
                        fm.setUptime(temp.getUptime());
                        fm.setId(temp.getId());
                        fms.add(fm);
                    }

                }

            }
        }

        return fms;

    }


    public List<UserFile> SuperiorCatalogue(List<UserFile> ufs, String currentpath, int page) {
        List<UserFile> fms = new ArrayList<UserFile>();
        Map<String, String> m = new Hashtable<String, String>();
        for (int i = 0; i < ufs.size(); i++) {
            UserFile temp = ufs.get(i);
            String path = temp.getVpath();
            String temps[] = path.split("/");
            char[] cpath = path.toCharArray();
            int l = currentpath.length();

            if (path.startsWith(currentpath)) {
                if (m.get(temps[page]) == null) {
                    if (path.equals(currentpath)|| cpath[l] == '/' ) {
                        UserFile fm = new UserFile();
                        fm.setVfname(temps[page]);
                        m.put(temps[page], "");
                        fm.setFilesize(temp.getFilesize());
                        fm.setIconsign(temp.getIconsign());
                        fm.setUptime(temp.getUptime());
                        fm.setId(temp.getId());
                        fms.add(fm);
                    }
                }
            }
        }


        return fms;
    }


    public List<UserFile> getSubdirectories(List<UserFile> ufs, String currentpath, int page) {
        List<UserFile> fms = new ArrayList<UserFile>();
        Map<String, String> m = new Hashtable<String, String>();
        for (int i = 0; i < ufs.size(); i++) {
            UserFile temp = ufs.get(i);
            String path = temp.getVpath();
            char[] cpath = path.toCharArray();
            int l = currentpath.length()-1;
            if (path.startsWith(currentpath)) {
                if (path.equals(currentpath)||cpath[l]=='/'){
                    String temps[] = path.split("/");
                    if (temps.length > page + 2) {
                        if (m.get(temps[page + 2]) == null) {
                            UserFile fm = new UserFile();
                            fm.setVfname(temps[page + 2]);
                            m.put(temps[page + 2], "");
                            fm.setFilesize(temp.getFilesize());
                            fm.setIconsign(temp.getIconsign());
                            fm.setUptime(temp.getUptime());
                            fm.setId(temp.getId());
                            fms.add(fm);
                        }
                    }
                }
            }
        }

        return fms;
    }

    public List<UserFile> getIndexPath(List<UserFile> ufs) {
        List<UserFile> ufs1 = new ArrayList<UserFile>();
        Map<String, String> m = new Hashtable<String, String>();
        for (int i = 0; i < ufs.size(); i++) {
            UserFile temp = ufs.get(i);
            UserFile uf = new UserFile();
            String path = temp.getVpath();

            //构造一个用来解析 str 的 StringTokenizer 对象，并提供一个指定的分隔符。
            StringTokenizer st = new StringTokenizer(path, "/");
            //返回从当前位置到下一个分隔符的字符串。
            String filename = st.nextToken();
            if (m.get(filename) == null) {
                m.put(filename, "");
                uf.setVfname(filename);
                uf.setUptime(temp.getUptime());
                //uf.setIconsign(temp.getIconSign());
                uf.setFilesize(temp.getFilesize());
                uf.setId(temp.getId());
                ufs1.add(uf);
            }
        }

        return ufs1;
    }

}
