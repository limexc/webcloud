package cn.limexc.service;

import cn.limexc.model.ShareFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhiyuanxzy@gmail.com
 * @version 1
 * @Description
 * @create 2021-04-02 0:28
 * @since jdk1.8.0
 */
public interface ShareService {
    /**
     * 获取share表中最大的id值，用于计算id以及其他
     * @return int值
     */
    Integer getMaxId();

    /**
     * 通过uid获取用户所有分享过的文件
     * @param uid  用户id
     * @return     分享文件list，应该也要改为返回List<userfile>
     */
    List<ShareFile> getAllUserShare(Integer uid);

    /**
     * 通过url获取用户分享过的特定文件
     * @param url   短链接
     * @return      分享的文件信息？ 不不不，要改，返回为文件信息或者要改表？  白天脑子瓦特了。
     */
    ShareFile getUserShareByUrl(String url);

    /**
     * 创建文件分享
     * @param shareFile  文件共享信息
     * @return  影响的行数
     */
    Integer createShare(ShareFile shareFile);

}
