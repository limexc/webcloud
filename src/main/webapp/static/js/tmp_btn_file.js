var Catalogue = 0;
var currentpath = "/";
var filename;
var data;
var rename = false;
var tablein
var uploadfilename
var table
layui
    .use(
        'table',
        function () {
            table = layui.table;

            tablein = table
                .render({
                    id: 'test',
                    elem: '#test',
                    url: 'getFile?method=index',
                    toolbar: '#toolbarDemo',
                    done: function (res, curr, count) {
                        $(".layui-table-tool").css("display", "none")
                        console.log(res)
                        Catalogue = res.msg["Catalogue"]
                        currentpath = res.msg["currentpath"]
                        data = res.data
                        console.log("Catalogue  :"
                            + Catalogue)
                        console.log("currentpath  :"
                            + currentpath)
                        this.where = {};
                    },

                    cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                    ,
                    cols: [[
                        {
                            type: 'checkbox'
                        },
                        {
                            field: 'filename',
                            title: '文件名',
                            event: 'fileclick',
                            //style:":hover{color:red;}",

                            templet: function (d) {
                                if (rename) {
                                    rename = false;
                                }
                                return "<svg class='icon' aria-hidden='true'><use xlink:href='" + d.iconsign + "'></use></svg>&nbsp;"
                                    + "</span class='filenamecolor'>"
                                    + d.filename
                                    + "</span>";
                            }
                        }, {
                            field: 'filesize',
                            title: '大小'
                        } //width 支持：数字、百分比和不填写。你还可以通过 minWidth 参数局部定义当前单元格的最小宽度，layui 2.2.1 新增
                        , {
                            field: 'date',
                            title: '修改日期',
                            sort: true
                        }, {
                            fixed: 'right',
                            title: '操作',
                            toolbar: '#barDemo',
                        }]]
                });
            //监听行工具事件
            table
                .on(
                    'tool(testEvent)',
                    function (obj) {
                        var data = obj.data;
                        /* console.log(obj.tr) //得到当前行元素对象 */
                        //console.log(obj.data) //得到当前行数据
                        console.log("event="
                            + obj.event)
                        if (obj.event == "fileclick") {
                            console.log(obj.data)
                            if (obj.data.iconsign == "#icon-folder") {
                                tablein
                                    .reload({
                                        url: "getFile?method=getSub",
                                        where: { //设定异步数据接口的额外参数，任意设
                                            "Catalogue": Catalogue,
                                            "currentpath": currentpath,
                                            "filename": data.filename
                                        }
                                    });
                            }
                        }
                        if (obj.event === 'del') {
                            var data = obj.data
                            layer
                                .confirm(
                                    '真的删除行么',
                                    function (
                                        index) {
                                        $
                                            .ajax({
                                                url: "getFile?method=deleteFile",
                                                data: {
                                                    "filename": data.filename,
                                                    "currentpath": currentpath
                                                },
                                                success: function (
                                                    data) {
                                                    console
                                                        .log("delete传输成功")
                                                }
                                            })
                                        obj
                                            .del();
                                        layer
                                            .close(index);
                                    });
                        }
                        if (obj.event === 'edit') {

                            rename = true;
                            layer
                                .prompt(
                                    {
                                        formType: 2,
                                        value: data.filename
                                    },
                                    function (
                                        value,
                                        index) {
                                        console
                                            .log("obj"
                                                + data.filename)
                                        console
                                            .log("value"
                                                + value)
                                        if (value != data.filename) {
                                            $
                                                .ajax({
                                                    url: "getFile?method=rename",
                                                    data: {
                                                        "currentpath": currentpath,
                                                        "newname": value,
                                                        "filename": data.filename
                                                    },
                                                    success: function (
                                                        data) {
                                                        console
                                                            .log(data['flag'])
                                                        console
                                                            .log("rename传输成功")
                                                        if (data['flag'] === false) {
                                                            layer
                                                                .msg('有相同命名文件存在');

                                                        } else {
                                                            obj
                                                                .update({
                                                                    filename: value
                                                                });
                                                        }

                                                    },
                                                    error: function () {
                                                        console
                                                            .log("rename传输失败")
                                                    }
                                                })
                                        } else {
                                            // layer.msg('有相同命名文件存在');
                                        }
                                        layer
                                            .close(index);
                                    });

                        }
                    });
        });
$("#download").click(function () {
    var checkStatus = table.checkStatus('test')
    var data = checkStatus.data;
    if (ishasfloder(data)) {
        if (data.length > 1) {
            alert("暂未实现多文件下载")
            /* var filenames=''
            for(var i=0;i<data.length;i++){
                filenames+=data[i]['filename']+"|"
            }
            $.ajax({
                url : "upload?method=batchDownload",
                data : {
                    "currentpath" : currentpath,
                    "filenames" : filenames
                },
                success : function(data) {
                    console.log(data)
                    window.open(data)
                },
                error : function() {
                    alert("下载失败")
                }
            })  */
        } else if (data.length == 1) {
            var filename = ''
            filename = data[0]['filename']
            console.log(filename)
            var path = "download";
            path += "?currentpath=" + currentpath + "&filename=" + filename;
            console.log(path)
            window.open(path)
        } else {
            alert("未选择下载文件")
        }
    } else {
        alert("暂不支持下载文件夹")
    }
    /* console.log(data.length)
    console.log(data) */
})

function ishasfloder(data) {
    for (var i = 0; i < data.length; i++) {
        if (data[i]['iconsign'] == "#icon-folder") {
            return false
        }
    }
    return true;
}

$("#fileupload22").change(function () {
    console.log("ds")
    if (document.getElementById("fileupload22").files.length > 0) {
        getMd5();
    }
    var obj = document.getElementById('fileupload22');
    var array = obj.value.split("\\")
    var index = array.length
    console.log(index - 1)
    uploadfilename = array[index - 1]
    console.log(uploadfilename)
});

function send() {
    var formdata = new FormData(); //form表单。
    var file = document.getElementById("fileupload22").files[0]
    formdata.append("file", file);
    console.log(formdata)
    $.ajax({
        url: "upload",
        type: 'POST',
        cache: false,
        data: formdata, //传到请求体的数据。如果是文件，浏览器会自动设置：
        //Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryfxmGQVueQFHdZmT2
        processData: false, //默认：{age:20,password:123} ==> age=20&password=123
        contentType: false, //一定不要设置。因为默认是application/x-www-form-urlencoded。文件上传必须是multipart/form-data
        /* beforeSend : function() {
        uploading = true;
    }, */
        success: function (data) {
            addfilename(uploadfilename, "false")
            alert(uploadfilename + "上传成功")
        },
        error: function (data) {
            alert("上传失败")
        }
    });
}

function getMd5() {
    var blobSlice = File.prototype.slice || File.prototype.mozSlice
        || File.prototype.webkitSlice, file = document
            .getElementById("fileupload22").files[0], chunkSize = 1024 * 1024 * 1, // Read in chunks of 2MB
        chunks = Math.ceil(file.size / chunkSize), currentChunk = 0, spark = new SparkMD5.ArrayBuffer(),
        fileReader = new FileReader();
    fileReader.onload = function (e) {
        spark.append(e.target.result); // Append array buffer
        currentChunk++;
        if (currentChunk < chunks) {
            loadNext();
        } else {
            var md5_hash = spark.end(); //MD5在这。
            console.log('finished loading');
            console.info('computed hash', md5_hash);
            console.log(md5_hash)
            $.ajax({
                url: "getMD5",
                type: 'POST',
                data: {
                    "MD5": md5_hash
                }, //传到请求体的数据。如果是文件，浏览器会自动设置：
                success: function (data) {
                    console.log(data)

                    if (data == "yes") {
                        send();
                    } else {
                        addfilename(uploadfilename, "false")
                        console.log("存在")
                        alert(uploadfilename + "上传成功")
                    }
                }
            });
        }
    };
    fileReader.onerror = function () {
        console.warn('oops, something went wrong.');
    };

    function loadNext() {
        var start = currentChunk * chunkSize, end = ((start + chunkSize) >= file.size) ? file.size
            : start + chunkSize;
        fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
    }

    loadNext();
}

$("#upper").click(function () {
    if (Catalogue != 0) {
        tablein.reload({
            url: "getFile?method=getSuperior",
            where: {
                "Catalogue": Catalogue,
                "currentpath": currentpath,
            }
        });
    }
    layer.msg('上一级');
})

$("#indexfile").click(function () {
    layer.msg('首页');
    tablein.reload({
        url: "getFile?method=index",
    });
})

$("#makefile").click(function () {
    var name = ""
    layer.prompt({
        formType: 2,
        title: '请输入值'
    }, function (value, index) {
        name = value
        addfilename(name, "true")
        layer.close(index);
    });
    layer.msg('新建文件夹');
})

function addfilename(name, isfloder) {
    tablein.reload({
        url: "getFile?method=getNewFloder",
        where: {
            "file": isfloder,
            "name": name,
            "Catalogue": Catalogue,
            "currentpath": currentpath,
        }
    });
}

$("body").on('mouseover',
    '.layui-table-view td[data-field="filename"]', function () {
        $(this).css("color", "deepskyblue")
        $(this).css("cursor", "pointer")
    })
$("body").on('mouseleave',
    '.layui-table-view td[data-field="filename"]', function () {
        $(this).css("color", "")
    })