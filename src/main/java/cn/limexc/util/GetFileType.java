package cn.limexc.util;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-14 13:41
 * @since jdk1.8.0
 */
public class GetFileType {

    public String fileType(String name) {
        String type = "";
        String[] names = name.split("\\.");
        String suffix = names[names.length - 1].toLowerCase();
        switch (suffix) {
            //音乐格式
            case "mp3":
                type = "music_mp3";
                break;
            case "flac":
                type = "music_flac";
                break;
            case "mnc":
                type = "music_mnc";
                break;
            case "wav":
                type = "music_wav";
                break;
            //压缩文档
            case "zip":
                type = "archive_zip";
                break;
            case "rar":
                type = "archive_rar";
                break;
            case "7z":
                type = "archive_7z";
                break;
            //视频文件
            case "mp4":
                type = "video_mp4";
                break;
            case "avi":
                type = "video_avi";
                break;
            case "mov":
                type = "video_mov";
                break;
            case "mkv":
                type = "video_mkv";
                break;
            case "flv":
                type = "video_flv";
                break;
            case "wmv":
                type = "video_wmv";
                break;
            //文档类型
            case "doc":
            case "docx":
                type = "dtd_word";
                break;
            case "ppt":
            case "pptx":
                type = "dtd_ppt";
                break;
            case "xls":
            case "xlsx":
                type = "dtd_excel";
                break;
            case "txt":
            case "md":
                type = "dtd_none";
                break;
            case "pdf":
                type = "dtd_pdf";
                break;
            //图片类
            case "jpg":
            case "jpeg":
                type="pic_jpeg";
                break;
            case "webp":
                type="pic_webp";
                break;
            case "bmp":
                type="pic_bmp";
                break;
            case "gif":
                type="pic_gif";
                break;
            case "psd":
                type="pic_psd";
                break;
            case "png":
                type="pic_png";
                break;
            case "ico":
                type="pic_ico";
                break;
            //代码类型
            case "java":
                type="code_java";
                break;
            case "py":
                type="code_python";
                break;
            case "cpp":
                type="code_cpp";
                break;
            case "c":
                type="code_c";
                break;
            case "go":
                type="code_go";
                break;
            case "html":
                type="code_html";
                break;
            case "jsp":
                type="code_jsp";
                break;
            case "xml":
                type="code_xml";
                break;
            case "properties":
                type="code_properties";
                break;
            case "bat":
                type="code_bat";
                break;
            case "sh":
                type="code_sh";
                break;

            //应用等可执行文件
            case "exe":
                type="app_exe";
                break;
            case "apk":
                type="app_apk";
                break;
            case "jar":
                type="app_jar";
                break;
            case "dmg":
                type="app_dmg";
                break;
            case "rpm":
                type="app_rpm";
                break;
            case "msi":
                type="app_msi";
                break;
            case "deb":
                type="app_deb";
                break;



            //其他类型
            default:
                type = "other_"+suffix;
                break;
        }
        return type;
    }

}
