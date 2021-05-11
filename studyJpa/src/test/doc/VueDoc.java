/*
package doc;

public class VueDoc {
    Vue CLI 是一个基于 Vue.js 进行快速开发的完整系统，是一组用于快速原型设计、简化应用程序搭建和进行高效项目管理的工具，确保了各种构建工具能够基于智能的默认配置即可平稳衔接。

    Vue CLI 是一个基于 Vue.js 进行快速开发的完整系统，致力于将 Vue 生态中的工具基础标准化。它确保了各种构建工具能够基于智能的默认配置即可平稳衔接，这样你可以专注在撰写应用上，而不必花好几天去纠结配置的问题。与此同时，它也为每个工具提供了调整配置的灵活性，无需 eject。

    Vue CLI

    Vue CLI 是一组用于快速原型设计、简化应用程序搭建和进行高效项目管理的工具。它由三个主要的工具组成：

    CLI 是一个 npm 包，通过 vue 命令提供核心功能。它可以帮我们轻松地构建一个新项目（vue create）或者快速创建原始构思（vue serve）。如果我们想要对项目进行更具体和可视化的控制，可以通过运行 vue ui 命令打开 CLI 的 GUI。
    CLI Service 是一个开发依赖项（vue-cli-service 二进制文件），安装在使用 CLI 创建的每个项目中。它可以帮助我们开发项目（vue-cli-service serve）、打包（vue-cli-service build），以及检查内部 Webpack 项目的配置（vue-cli-service inspect）。
    CLI 插件也是 npm 包，为项目提供额外的功能。它们的名字以 @vue/cli-plugin-（内置插件）或 vue-cli-plugin-（社区插件）开头。我们可以在开发过程中通过 vue add 命令添加它们。
    后面我们将探讨上述工具和实用程序的所有细节，但现在先让我们看看 Vue CLI 这些强大而灵活的功能。



    Vue CLI 是一个用于提升 Vue.js 开发工作流程的全功能系统
    Vue CLI 凭借其丰富的功能集，可加速和简化 Vue.js 项目开发。让我们看看它都有哪些功能：

    基于插件的架构。Vue CLI 完全围绕插件而构建，所以非常灵活和可扩展。我们可以选择在项目创建过程中添加哪些内置插件，还可以在创建项目后随时添加任意数量的插件。
    Vue CLI 完全可配置、可扩展和可升级。
    提供了一系列官方预装插件，集成了前端生态系统的一流工具（Babel、ESLint、TypeScript、PWA、Jest、Mocha、Cypress 和 Nightwatch）。
    一个默认预设，我们可以在项目创建期间或之后根据我们的需求进行修改。
    无需弹出。与 React 和 Angular CLI 工具相比，我们可以在创建项目后随时安全地检查和调整项目的 Webpack 配置，无需弹出应用程序并切换到手动配置。
    多页面支持。
    无需任何配置即可进行即时原型设计。
    不同的构建目标可以生成不同版本的项目——我们可以使用同一个代码库构建 App、库或 Web 组件。
    现代模式功能。构建适用于现代浏览器的应用程序，同时兼容旧版本的浏览器。
    一个完整的 GUI，可轻松创建、更新和管理复杂项目。
    UI 插件 API。Vue UI 公开了一个插件 API，我们可以用它将自定义功能添加到 CLI 的 GUI 版本中。

vue-cli与@vue/cli
vue脚手架版本目前有2,3,4.不管搭建哪个版本的脚手架，首先都需要安装node.

node的安装
node的安装我们可以直接去node官网(http://nodejs.cn/)下载安装，node安装完之后，node自带的包管理工具npm也就安装好了，我们win系统直接开始在cmd窗口输入node -v查看node版本号，如下图能看到版本号说明node安装成功:

如果当前node版本不够，可以输入下面命令行来把node版本更新到最新的稳定版本:
先清除npm缓存：npm cache clean -f
然后安装n模块：npm install -g n
升级node.js到最新稳定版：n stable
如果是mac 在命令前面加sudo：sudo n stable

vue-cli2的搭建
node安装成功之后，然后检测一下之前是否安装过脚手架vue-cli,检测方法是输入命令vue -V或者vue --version如果能看到版本号说明之前安装过脚手架，如下图所示:

可以看到之前安装的vue-cli是4.1.2版本，如果我们不喜欢此版本可以先卸载此版本然后安装上我们想要的版本，卸载命令是npm uninstall vue-cli -g(这是2点几版本卸载，如果是版本3或4卸载那就是npm uninstall @vue/cli -g)

安装淘宝镜像cnpm
npm的服务器是外国的，所以有时候我们安装“模块”会很慢很慢甚至导致安装失败。淘宝镜像将npm上面的模块同步到国内服务器，提高我们安装模块的时间和成功率。安装完淘宝镜像之后，cnpm和npm命令行皆可使用，二者并不冲突。
桌面新建一个空demo文件夹，进入空demo文件夹后,cmd进入窗口或者同时点击shift和鼠标右键，选择窗口进入，窗口里输入以下命令:
npm install -g cnpm --registry=https://registry.npm.taobao.org
输入cnpm -v可以查看cnpm版本

全局安装webpack和脚手架:
继续在窗口里输入以下命令:
1.npm install webpack -g
2.npm install -g vue-cli
也可以安装指定版本的脚手架，命令行执行如下:
如果是安装3以下版本的脚手架，命令npm install -g vue-cli@版本号
如果是安装3以上版本的脚手架，命令npm install -g @vue/cli@版本号

创建项目:
窗口执行命令vue init webpack demo（demo是你的项目名/文件名），执行之后会自动初始化创建一个文件夹demo，如果说我们桌面本身就有一个demo文件夹，如果我们是在桌面打开的终端而非在demo文件夹下打开的终端，此时输入创建项目命令vue init webpack demo之后就会问Target directory exists. Continue? 目标文件已存在是否将脚手架项目放在已存在的文件夹下面，我们选yes

说明：
Project name：项目名称
Project description：项目描述
Author：作者
Vue build：打包方式（standalone和runtime）,按回车选择默认的就好
Install vue-router?：是否安装路由？肯定是需要用到的，按 y 回车
Use ESLint to lint your code?：是否启用eslint代码检测规则？目前不需要，按 n 回车
Setup unit tests with Karma + Mocha?：是否进行单元测试？目前不需要，按 n 回车
Setup e2e tests with Nightwatch?：是否进行端对端测试？目前不需要，按 n 回车

配置完之后，输入命令cd demo进入我们的项目文件夹，输入命令code . 就能将项目在vscode编辑器里打开，最后输入命令npm run dev启动项目，最后在浏览器地址栏输入localhost:8080就可以访问项目了。

项目结构介绍

1、build：构建脚本目录

1）build.js ==> 生产环境构建脚本；

2）check-versions.js ==> 检查npm，node.js版本；

3）utils.js ==> 构建相关工具方法；

4）vue-loader.conf.js ==> 配置了css加载器以及编译css之后自动添加前缀；

5）webpack.base.conf.js ==> webpack基本配置；

6）webpack.dev.conf.js ==> webpack开发环境配置；

7）webpack.prod.conf.js ==> webpack生产环境配置；
　　　　
2、config：项目配置

1）dev.env.js ==> 开发环境变量；

2）index.js ==> 项目配置文件；

3）prod.env.js ==> 生产环境变量；
3、node_modules：npm 加载的项目依赖模块
4、src：这里是我们要开发的目录，基本上要做的事情都在这个目录里。里面包含了几个目录及文件：
　　　　1）assets：资源目录，放置一些图片或者公共js、公共css。这里的资源会被webpack构建；
　　　　2）components：组件目录，我们写的组件就放在这个目录里面；
　　　　3）router：前端路由，我们需要配置的路由路径写在index.js里面；
　　　　4）App.vue：根组件；
　　　　5）main.js：入口js文件；
5、static：静态资源目录，如图片、字体等。不会被webpack构建
6、babelrc：babel编译参数
7、editorconfig：代码格式
8、gitignore：git上传需要忽略的文件配置
9、postcssrc.js：转换css的工具
10、index.html：首页入口文件，可以添加一些 meta 信息等、
11、package.json：npm包配置文件，定义了项目的npm脚本，依赖包等信息
12、README.md：项目的说明文档，markdown 格式

@vue/cli3搭建
上面介绍了vue-cli2的介绍，现在介绍一下vue-cli3及以上版本脚手架的搭建。

1.安装node,方法也是node官网下载安装。
2.命令npm install -g @vue/cli 和命令npm install webpack -g​
3.vue create project // project是项目名称
4.第3步执行回车后就让我们选择模板:

default (babel, eslint): 默认配置，只有Babel和eslint，其他的都要自己另外再配置
Manually select features: 手动配置
这里我们选择手动配置，也就是选择Manually这个

5.选择配置:
上下键切换，空格键选中是否安装对应选项（前方有*表示已被空格键选中要安装的项），比如我们空格选中安装Babel Router和Vuex这三项，babel是转码器，将es6,es7转成浏览器能识别的es5:

6.路由的两种模式(哈希模式和history模式)，意思是问我们是否路由选择history模式，可以选择yes也可以选no,我们选no,后回车.

7.然后会让我们选择配置文件放在哪里，可以选第一个也可以选第二个，我们选中package.json后，将babel，router这些配置文件就放在package.json里，后回车.

8.是否保存预处理，我们选no,后回车，它会下载对应内容

9.输入命令cd demo然后输入npm run serve启动项目
生成的目录结构如下:
node_modules：这个文件夹里面是我们项目需要的一些依赖；
public：静态文件夹，这个文件夹中的资源不会被webpack编译，构建生产包的时候，会被直接拷贝一份；
assets： 是页面和组件中用到的静态资源，比如公共样式文件，字体文件，图片等，该文件夹与public的区别是：该文件夹中的资源会被webpack编译；
components： 文件夹中存放我们的组件；
views： 文件夹中存放我们的页面；
App.vue： 这个文件是我们所有vue页面的顶层文件；
main.js： 是我们整个项目的入口文件；
router.js： 是路由的配置文件；
store.js： 是vuex的配置文件；
.browserslistrc：文件用于给开发者设置浏览器版本的范围；
.eslintrc.js：eslint配置文件；
.gitignore：需要git忽略的文件；
babel.config.js：babel的配置工具；
package-lock.json：记录项目依赖中各个依赖之间的关系和版本，防止npm包中有不遵守“相同大版本号的同一个库包，其接口符合兼容要求”

这一规范，导致项目运行报错；
package.json：项目的描述文件，包括项目名、依赖的版本、作者、命令、入口文件等信息。

README.md：项目的说明文档，项目介绍、License、一些命令（例如：启动命令、打包命令、单元测试命令等）

@vue/cli3与vue-cli2的区别
@vue/cli3和vue-cli2的区别在于：

创建项目 vue create

启动项目 由npm run dev 改成 npm run serve

移除了配置文件目录 config 和 build 文件夹，如果需要自定义配置，需要自己新建vue.config.js文件

移除了 static 静态资源文件夹，新增 public 文件夹，静态资源转移到public目录中，通过/xx.xx可以直接访问，并且 index.html 移动到 public 中

在 src 文件夹中新增了 views 文件夹，用于分类 视图组件 和 公共组件

安装项目时会自动下载node-model文件夹
————————————————
版权声明：本文为CSDN博主「广漂的明哥」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_37635012/article/details/105522444
}
*/
