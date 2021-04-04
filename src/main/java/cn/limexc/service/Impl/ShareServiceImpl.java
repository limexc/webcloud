package cn.limexc.service.Impl;

import cn.limexc.dao.ShareFileDao;
import cn.limexc.model.ShareFile;
import cn.limexc.service.ShareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhiyuanxzy@gmail.com
 * @version 1
 * @Description
 * @create 2021-04-04 23:00
 * @since jdk1.8.0
 */
@Service
public class ShareServiceImpl implements ShareService {

    @Resource
    private ShareFileDao shareFileDao;

    @Override
    public Integer getMaxId() {
        return null;
    }

    @Override
    public List<ShareFile> getAllUserShare(Integer uid) {
        return null;
    }

    @Override
    public ShareFile getUserShareByUFid(Integer ufid) {

        return shareFileDao.selectUserShareByUFid(ufid);
    }

    @Override
    public ShareFile getUserShareByUrl(String url) {
        return shareFileDao.selectUserShareByUrl(url);
    }

    @Override
    public Integer createShare(ShareFile shareFile) {
        //先这么干，后面把controller中的转过来，先实现功能再说
        return shareFileDao.insertUserShare(shareFile);
    }
}
