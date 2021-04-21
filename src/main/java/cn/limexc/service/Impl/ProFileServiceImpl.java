package cn.limexc.service.Impl;

import cn.limexc.dao.ProFileDao;
import cn.limexc.model.ProFile;
import cn.limexc.service.ProFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-21 23:53
 * @since jdk1.8.0
 */
@Service
public class ProFileServiceImpl implements ProFileService {
    @Resource
    private ProFileDao proFileDao;

    @Override
    public Integer upProFile(String name, String path, String url) {
        return proFileDao.insertProFile(name, path, url);
    }

    @Override
    public ProFile getProFile(String url) {
        return proFileDao.selectProFileUrl(url);
    }
}
