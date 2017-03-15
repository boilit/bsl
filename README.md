Boilit Script Language
===
<pre>

<strong>不再维护，不建议使用，该项目废弃, 目前其替代品未开源。</strong>

Bsl全名为Boilit Script Language，是一款主要面向模板引擎方向的脚本语言。

软件目标：模板引擎、脚本语言、高性能
软件特性：
    1、易学易用：类脚本语言，语法简单，敏捷开发；
    2、功能齐全：具备主流及非主流模板引擎的功能；
    3、性能卓越：超越主流及非主流模板引擎的性能；
    4、扩展集成：扩展接口齐全，易与任意框架整合；
    5、开发调试：可独立做单元测试，定位错误行列；
    6、架构轻量：不需要依赖第三方软件包即可运行；
    7、交流方式：GitHub、在线文档、邮件、QQ群等；
    8、升级维护：长期维护，欢迎大家使用、参与改进；
功能简介：
    1、支持弱类型变量、作用域变量定义方式；
    2、支持单行注释、多行注释、静态文本处理及扩展；
    3、支持数学、逻辑、位运算及复杂的表达式运算；
    4、支持Java对象的常量、变量、方法调用及连续调用；
    5、支持多路条件分支、循环、next、break等；
    6、支持引入与布局、模板片段、安全输出、格式化输出；
    7、支持多种模板资源加载方式（File、ClassPath、URL、String等）；
    8、支持独立进行单元测试、模板错误行列定位；
    9、支持通过API编程、配置文件等方式启动引擎；

开发语言：Java
目前版本：2.0.2
类库大小：260K
引擎性能：超越目前主流及非主流模板引擎，速度一流，适合大中型项目应用，请查看在线文档或基准测试内的测试结果;
软件特性：请参考<a href="http://boilit.github.io/bsl">在线文档</a>
在线文档：<a href="http://boilit.github.io/bsl">http://boilit.github.io/bsl</a>
基准测试：<a href="https://github.com/boilit/ebm">https://github.com/boilit/ebm</a>
下载地址: <a href="http://boilit.github.io/bsl/releases/bsl-2.0.2.jar">bsl-2.0.2.jar</a>
交流QQ群：

软件作者：于景洋
</pre>

版本更新
===
<pre>
2.0.2版本更新：
    1、修复Array在Wrapper下Iterator的问题；
    2、更新专用GBKEncoder，改倒序时手误导致的越界异常；
2.0.1版本更新：
    1、修复模板片段参数定位问题；
    2、增加引擎ClassLoader实例化方法；
2.0.0版本更新：
    1、增加arg关键字，用于声明模板或片段需要的参数，显示地规范编码；
       这样做修复了之前版本因Map类型的Model可能因迭代顺序不定造成的隐藏BUG，同时也提升了模板渲染速度；
    2、增加模板片段功能，片段支持参数传递；
       模板内可以定义多个片段，片段内容在调用时输出；
       模板可以include其它模板也可以include其它模板内定义的片段；
    3、增加断点调试功能，可以结合ide工具通过扩展来实现断点监视；
       在无ide支持的情况下也可以通过输出来调试，原生支持调试除jsp之外在其它模板引擎很少见到的功能；
    4、增加对数组对象的读写支持；
    5、修改StringResource及StringResourceLoader，支持接收字符串作为模板；
    6、移除ITextProcessor默认实现，但保留扩展口；
    7、修复Loop循环中next指令执行BUG；
    8、修复多行注释出现语法错误的BUG；

1.2.0:
    修改专用编码器多线程并发BUG（空指针异常）；
1.1.0:
    修改运算单元算法；
    完善错误定位；
    移除Logger适配器接口，改为异常抛出；
    修订loop、next、break的检测机制；
    修改Include，参数可接收一个或两个表达式；
    增强UTF-8专用编码器，由UCS-2支持扩展到UCS-4支持
    修改IResource、IResourceLoader接口及缺省实现；
    增加StringResource、StringResourceLoader资源读取方式；
    静态文本处理接口ITextCompressor更改为ITextProcessor，提供缺省实现，一般用不到该功能；
1.0.2:
    更新字符缓冲实现，转义字符BUG修复；
1.0.1：
    更新专用GBKEncoder；
    更新静态文本输出方法及IO部分IPrinter接口的实现；
1.0.0-SNAPHSOT：
    初始发布版本
</pre>

License(许可证)
===
<pre>
Boilit Script Language is released under the MIT License. 
See the bundled LICENSE file for details.

Boilit Script Language依据MIT许可证发布。
详细请看捆绑的LICENSE文件。
</pre>
