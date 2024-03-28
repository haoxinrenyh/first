# Open-Spider 用户信赖的互联网数据采集器

<br>

## 为什么要做这个开源项目？ 让互联网数据采集更简单！！！


- 1.有用的数据在哪儿？

互联网和移动互联网的信息量实在是太大了，很多有用的信息都在网上，但不是每个人都能准确的找到这些网站地址和具体栏目。

因此，我们会把对所有人有用的信息源都收集起来，并且一一对应、归档整理、定期更新，不断的把资源开放给大家。

- 2.怎么采集有用的数据？

网上有很多免费的数据采集工具，对于大多数人来说想在短时间内上手几乎不可能。原因不是他们的软件本身做的不好，而是无论怎么去开发这类的工具，对于一个普通用户来说，学习的成本太高了。

因此，我们会提供一个“采集应用市场”，让把大家上传、分享、交流经常需要采集的网站，随着分享和交流会有越来越多的用户能快速的获取自己需要采集网站的代码，还可以在自己的电脑、服务器、云端运行。

FIY：
充分利用过去和现在的信息&数据, 对未来的预测?！ 这是我们想为大家做的一点贡献！ 


无论你是使用者还是共同完善的开发者，欢迎 pull request 或者 留言对我们提出建议。 
<br>
您的支持和参与就是我们坚持开源的动力！请 :star:  [star](https://gitee.com/stonedtx/open-spider) 或者 [fork](https://gitee.com/stonedtx/open-spider)!  

无论你是有相关经验、技术可以一起加入我们的开源项目。

<br>


## 产品简介

满足多种业务场景，适合产品、运营、销售、数据分析、电商从业者、学术研究等多种身份职业。

- 舆情监控

  全方位监测公开信息，抢先获取舆论趋势。

- 市场分析

  获取用户真实行为数据，全面把握顾客真实需求。

- 用户反馈

  强力支撑用户调研，准确获取用户反馈和偏好。

- 风险预测

  高效信息采集和数据清洗，及时应对系统风险。


## 主要特点
- 模板采集

  模板采集模式内置上百种主流网站数据源，如京东、天猫、大众点评等热门采集网站，只需参照模板简单设置参数，就可以快速获取网站公开数据。

- 智能采集
 
  采集可根据不同网站，提供多种网页采集策略与配套资源，可自定义配置，组合运用，自动化处理。从而帮助整个采集过程实现数据的完整性与稳定性。

- 自定义采集

  针对不同用户的采集需求，可提供自动生成爬虫的自定义模式，可准确批量识别各种网页元素，还有翻页、下拉、ajax、页面滚动、条件判断等多种功能，支持不同网页结构的复杂网站采集，满足多种采集应用场景。


## 采集范围

![输入图片说明](https://gitee.com/stonedtx/open-spider/raw/master/ProIMG/cai-ji-fan-wei.png)


## 系统架构
![输入图片说明](https://gitee.com/stonedtx/open-spider/raw/master/ProIMG/open-spider-jiagou.png)


## 功能结构

![输入图片说明](https://gitee.com/stonedtx/open-spider/raw/master/ProIMG/xmind-pic.png)



## UI 展示（更新ing）

![输入图片说明](https://gitee.com/stonedtx/open-spider/raw/master/ProIMG/main-page.png)

![输入图片说明](https://gitee.com/stonedtx/open-spider/raw/master/ProIMG/web-site.png)

![输入图片说明](https://gitee.com/stonedtx/open-spider/raw/master/ProIMG/my-spider.png)

## 操作手册


## 安装依赖
1. JavaEE 8 以上版本；
2. MySQL 5.7 以上版本；
3. Redis 4.0 以上版本；

## 运行版本

仅需五步快速安装 Open-Spider：

-  1.安装 MySQL 5.7+、redis 4.0+

-  2.下载源代码，编译jar包，执行  _nohup java -jar ***.jar &_，启动爬虫系统。 
-  3.下载 [front_protal](https://gitee.com/stonedtx/open-spider/attach_files/1693353/download)，进入apache-tomcat-8.5.46/bin文件夹.执行 ./startup.sh
-  4.配置nginx,在http块中增加如下内容:
    ```text
    server {
            listen       8085;
            server_name  127.0.0.1;
    
            location / {
                proxy_pass http://127.0.0.1:8084;
            }
            location /spider_factory {
                proxy_pass http://127.0.0.1:8080;
            }
    }
    ```

-  5.本地访问：http://127.0.0.1:8085/page/login/index.html
 用户名：admin，  密码：Stonedt,123


##  按需定制|数据定制
  当您在开发与研究中遇到  **数据采集、数据处理、舆情系统定制**  等方面的问题，请联系我们，我们会以最快的速度提供专业的解决方案。为您提供必要的专业技术支持。

  服务流程如下

![输入图片说明](https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/data-plan.png)


## 免责声明

请勿将本项目产品应用到任何可能会违反法律规定和道德约束的工作中,请友善使用本项目产品，遵守蜘蛛协议，不要用于任何非法用途。如您选择使用即代表您遵守此协议，作者不承担任何由于您违反此协议带来任何的法律风险和损失，一切后果由您承担。



## 相关开源项目


- **[多模态AI能力平台](https://gitee.com/stonedtx/free-nlp-api)** 
 
  将采集数据采用NLP和文本挖掘技术对此标签，以便于用户分类查看和检索。

- **[监测分析系统](https://gitee.com/stonedtx/yuqing)** 
   
  对采集数据展示分析，提供用户个性化配置，让每个用户获取不同的数据分析展示结果。

 


<br>

## 产品经理微信
   扫描微信二维码，技术交流。

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/%E8%81%94%E7%B3%BB%E6%88%91%E4%BB%AC-%E4%B8%AA%E4%BA%BA%E5%BE%AE%E4%BF%A1.jpg" title="Logo"  width="220">


  **微信公众号**

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/wxOfficialAccount.jpg" title="Logo"  width="220">

<br>


## 联系我们

+ 微信号： javabloger  

+ 电话：13913853100

+ 邮箱： huangyi@stonedt.com

+ 公司官网：[www.stonedt.com](http://www.stonedt.com)

欢迎您在下方留言，或添加微信与我们交流。


## License & Copyright

Copyright (c) 2014-2023 思通数科 StoneDT, All rights reserved.

Licensed under The GNU General Public License version 3 (GPLv3)  (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

<https://www.gnu.org/licenses/gpl-3.0.html>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.