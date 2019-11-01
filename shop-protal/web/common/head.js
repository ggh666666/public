var head = '<meta http-equiv="X-UA-Compatible" content="IE=edge">\n' +
    '    <meta name="viewport" content="width=device-width, initial-scale=1">\n' +
    '    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->\n' +
    '    <!-- Bootstrap core CSS -->\n' +
    '    <link type="text/css" rel="stylesheet" href="/js/bootstrap-3.3.7-dist/css/bootstrap.css">\n' +
    '\n' +
    '    <link rel="stylesheet" href="/js/bootstrap-validator-0.5.2/css/bootstrapValidator.min.css"/>\n' +
    '\n' +
    '    <link type="text/css" rel="stylesheet" href="/js/DataTables-1.10.18/css/dataTables.bootstrap.css">\n' +
    '    <link type="text/css" rel="stylesheet" href="/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">\n' +
    '    <link rel="stylesheet" href="/js/bootstrap-select-1.13.9/css/bootstrap-select.min.css">\n' +
    '\n' +
    '    <link rel="stylesheet"href="/js/ztree/css/zTreeStyle/zTreeStyle.css">\n' +
    '\n' +
    '    <link rel="stylesheet" href="/js/fileinput5/css/fileinput.min.css"/>';

window.document.write(head);

var html = '<!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->\n' +
    '    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->\n' +
    '    <!--[if lt IE 9]>\n' +
    '      <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>\n' +
    '      <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>\n' +
    '    <![endif]-->';
window.document.head.innerHTML += html;