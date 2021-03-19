function change(node) {
    //可以在这里加一些文件简单的判断
    
    
    
    //一个分片设置为100M，刚好为机械硬盘读取上限（大概），cpu和内存也够用
    md5(node.files[0], 100*1024*1024).then(e => {
        // 获取到文件的md5
        console.log("md5=" + e);
        //将获取到的md5发送到服务器进行匹配，若有妙传，若未匹配到则上传文件+md5
        //关于进度条，在未获取到md5之前可显示正则初始化上传。
        
        
        
    }).catch(e => {
        // 处理异常
        console.error(e);
    });
}


/**
 * 计算文件的MD5 
 * @param file 文件
 * @param chunkSize 分片大小
 * @returns Promise
 */
function md5(file, chunkSize) {
    return new Promise((resolve, reject) => {
        let blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;
        let chunks = Math.ceil(file.size / chunkSize);
        let currentChunk = 0;
        let spark = new SparkMD5.ArrayBuffer();
        let fileReader = new FileReader();

        fileReader.onload = function(e) {
            spark.append(e.target.result);
            currentChunk++;
            if (currentChunk < chunks) {
                loadNext();
            } else {
                let md5 = spark.end();
                resolve(md5);
            }
        };

        fileReader.onerror = function(e) {
            reject(e);
        };

        function loadNext() {
            let start = currentChunk * chunkSize;
            let end = start + chunkSize;
            if (end > file.size) {
                end = file.size;
            }
            fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
        }
        loadNext();
    });
}
