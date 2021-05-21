var filesize;
var filename;


function change(node) {

    //可以在这里加一些文件简单的判断
    getfilesize(node);
    getfileName(node);

    //一个分片设置为100M，刚好为机械硬盘读取上限（大概），cpu和内存也够用
    md5(node.files[0], 100*1024*1024).then(e => {
        //获取到文件的md5
        console.log("md5=" + e);
        //将获取到的md5发送到服务器进行匹配，若有秒传，若未匹配到则上传文件+md5
        //关于进度条，在未获取到md5之前可显示正在初始化上传。


        //上传提示框
        layer.alert('<a id="up_tips">正在读取文件</a><br/><progress style="width: 260px"></progress><br/>\n' +
            '<p id="progress">0 bytes</p>\n' +
            '<p id="info"></p>',
        {
            skin: 'layui-layer-molv',  //样式类名
            cancel: function(index, layero){
                if(confirm('确定要关闭么')){ //只有当点击confirm框的确定时，该层才会关闭
                    window.parent.location.reload();
                    layer.close(index)
                }
                return false;
            },
            btn: ['确定']
            ,yes: function(index, layero){
                //按钮的回调
                window.parent.location.reload();
            }
        });



        var jsondata = {
            "filesize":filesize,
            "md5value":e,
            "filename":filename,
            "Catalogue" : Catalogue,
            "currentpath" : currentpath
        };

        $.ajax({

            type:"post",
            url:"http://localhost:8080/CloudWeb/info/getmd5",
            dataType:"json",
            contentType: 'application/json;charset=UTF-8',
            data:JSON.stringify(jsondata),

            success: function (data) {
                //alert(data)
                if (data==="yes"){
                    $("#up_tips").html("上传成功！")
                    //秒传后重新加载窗口
                    //window.parent.location.reload();
                }else if (data==="no"){
                    var formatdata = new FormData();
                    formatdata.append("md5value",e);
                    formatdata.append("currentpath",currentpath);
                    formatdata.append("file",node.files[0]);

                    $.ajax({
                        method:"post",
                        url: "http://localhost:8080/CloudWeb/file/upload",
                        enctype: "multipart/form-data",
                        //不处理数据
                        contentType:false,
                        //不设置内容类型
                        processData: false,
                        data:formatdata,

                        xhr: function () { //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
                            var myXhr = $.ajaxSettings.xhr();
                            if (myXhr.upload) { //检查upload属性是否存在
                                //绑定progress事件的回调函数
                                myXhr.upload.addEventListener('progress', progressHandlingFunction, false);
                            }
                            return myXhr; //xhr对象返回给jQuery使用
                        },

                        success:function (){
                            //alert("上传成功")
                            $("#up_tips").html("上传成功！")
                        },
                        error:function () {
                            alert("上传失败")
                        }
                    })
                }else {
                    layer.alert("您的可以空间不足，请联系管理员扩容！")
                }

            },
            error: function (data, XMLHttpRequest) {
                alert("error"+data+"  "+XMLHttpRequest.status);

            }

        })


        
        
    }).catch(e => {
        // 处理异常
        console.error(e);
    });




}

function getfilesize(file){
    filesize= file.files[0].size;
    console.log("文件大小byte:"+filesize);
}

function getfileName(file){
    filename=file.files[0].name;
}

//上传进度回调函数：
function progressHandlingFunction(e) {
    if (e.lengthComputable) {
        $('progress').attr({value: e.loaded, max: e.total}); //更新数据到进度条
        var percent = e.loaded / e.total * 100;
        $('#progress').html(e.loaded + "/" + e.total + " bytes. " + percent.toFixed(2) + "%");
    }
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
