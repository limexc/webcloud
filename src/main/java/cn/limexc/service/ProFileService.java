package cn.limexc.service;

import cn.limexc.model.ProFile;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-21 23:51
 * @since jdk1.8.0
 */
public interface ProFileService {

    Integer upProFile(String name,String path,String url);

    ProFile getProFile(String url);
}
