<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../include/constants.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>UFP - 广告样式说明文档</title>
<link href="/css/base${build}.css?${constantVersion}" type="text/css" rel="stylesheet" />
<link href="/css/layout${build}.css?${constantVersion}" type="text/css" rel="stylesheet" />
<link href="/css/doc${build}.css?${constantVersion}" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="container">
<%@include file="../include/header.jsp"%>
<div id="main">
    <ul class="listDoc">
        <li>
            <h4>横幅样式：</h4>
            <p>即为传统的横幅广告样式，预览如下：</p>
            <div class="img_galary">
                    <img src="/images/doc/image001.png"/>
            </div>
            <p>该样式支持两种广告形式,一种为标准样式(右图)，即logo+文字的形式；另一种为图片广告（左图），即整个横幅为一张图片。 样式在添加广告时可以选择。</p>
        </li>
        <li>
            <h4>自定义入口样式：</h4>
            <p>即为一个入口，点击后进入一个list的方式，样式预览为：</p>
            <div class="img_galary">
                    <img src="/images/doc/image003.png"/>
            </div>
            <p>点击左图下方的小图标后，会进入右图界面，内容可以是应用，也可以是网页。
                <dl>
                    <dd>若为应用：点击下载时会立刻开始下载，点击某个应用的文字会弹出该应用的详细介绍。</dd>
                    <dd> 若为网页：則按钮会显示”浏览”字样,点击后会进入该广告的landingpage，可以选择用webview打开，也可以选择用浏览器打开（在创建广告时的打开方式部分进行选择）。</dd>
                    <dd>该样式仅支持标准样式的广告。</dd>
                </dl>
            </p>
        </li>
        <li>
            <h4>内嵌入口样式：</h4>
            <p>即list页面内嵌在sdk中，直接暴露在用户的面前，预览如下：</p>
            <div class="img_galary">
                    <img src="/images/doc/image005.png"/>
            </div>
            <p>图中整个为一个应用界面，list嵌入在这个应用界面中。
                <dl>
                    <dd>若为应用：点击下载时会立刻开始下载，点击某个应用的文字会弹出该应用的详细介绍。</dd>
                    <dd> 若为网页：則按钮会显示”浏览”字样,点击后会进入该广告的landingpage，可以选择用webview打开，也可以选择用浏览器打开（在创建广告时的打开方式部分进行选择）。</dd>
                    <dd>该样式自定义空间较大，可以自由的设置广告想如何暴露在用户面前。例如，可以实现美图秀秀下方的效果：</dd>
                    <dd><img src="/images/doc/image007.png" alt="" /></dd>
                </dl>
            </p>
        </li>
        <li>
            <h4>wap样式：</h4>
            <p>即为一个入口，点击后进入一个网页的样式：</p>
            <div class="img_galary">
                    <img src="/images/doc/image009.png"/>
                    <img src="/images/doc/image011.png"/>
            </div>
            <p>
                点击左图下方的小图标后，会进入右图的网页，有众多的网页模板，可供选择，各模板之间可以进行实时的切换。每个模板，调取的物料尺寸可能会不一样，请提前准备好相应的物料。
            </p>
        </li>
        <li>
            <h4>文字链样式：</h4>
            <p>即为一段文字，点击后进入对应的landingpage的样式：</p>
            <div class="img_galary">
                <img src="/images/doc/image013.png"/>
                <img src="/images/doc/image015.png"/>
            </div>
            <p>
                点击左图下方的文字后,会进入右图的网页。可以自由设置字体的大小，颜色，可以可以选择用webview打开，也可以选择用浏览器打开（在创建广告时的打开方式部分进行选择）。
文字链的样式仅能调取文字广告。
            </p>
        </li>
    </ul>
</div>
<%@include file="../include/footer.jsp"%>
</div>



</body>
</html>



