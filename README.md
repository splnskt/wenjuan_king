# wenjuan_king
期末大作业，一个问卷项目





# 2024/06/01更新说明：
## 1.修改了有歧义的请求示例，以下单个pid的请求只要传递变量，不需要传jason，变量类型为int
#### 影响的条例如下↓
2.8 查看问卷数据
2.2 查看问卷
# 更新说明：
## 1.规范了请求url,如关于问卷的请求一律以/paper开头
## 2.对id进行了分类，如用户id修改为uid,问卷id修改为pid
## 3.所有id类字段一律使用int类型
# API

编码方式均为UTF-8

## 1.管理员相关
### 1.1 注册
#### 接口地址
```
/user/register
```
#### 请求方式
HTTP	POST
#### 请求示例
```
{
  "username": "Alice",
  "password": "123456",
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
username|String|Y| 2-64个字符 |用户名（昵称）
password|String|Y| 6-64个字符 |登录密码

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": 0
}
//注册失败，用户名被占用
{
  "code": 1,
  "msg": "username not available",
  "data": null
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
code  |int   |Y         | -          |请求状态码
msg   |String|Y         | -          |消息提示
data  |int   |N         | -          |结果数据

#### code说明
状态值 |说明
:---  |:---  
-1    |token失效或未登录（用于需要登录后才能进行的操作）
0     |请求成功（全文适用，下文不再赘述）
1     |系统异常（全文适用，下文不再赘述）
2     |参数不正确（全文适用，下文不再赘述）
> #### code的补充
* code非0时，无data参数，可通过msg判断问题原因
* code取0时，若data中有值，则data参数存在，否则无data参数


### 1.3 登录
#### 接口地址
```
/user/login
```
#### 请求方式
HTTP	POST
#### 请求示例
```
{  
  "username": "Alice",
  "password": "123456"  
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
username   |String|Y| 5-64个字符 |用户名
password|String|Y| 6-64个字符 |登录密码

#### 返回参数
返回示例
```
//登录成功
{
  "code": 0,
  "msg": "ok",
  "data": null
}
//登录失败，用户名不存在
{
  "code": 1,
  "msg": "username not available",
  "data": null
}
//登录失败,密码错误
{
  "code": 1,
  "msg": "password error",
  "data": null
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
code  |int   |Y         | -          |请求状态码
msg   |String|Y         | -          |消息提示
data  |Object|N         | -          |结果


> header使用token示例
```
    ...
    headers: {'token': token }
    ...
```


## 2.问卷相关
### 2.1 管理员获取问卷列表
#### 接口地址
```
/paper/paper-lists
```
#### 请求方式
HTTP    GET
#### 请求示例
无
> #### 请求参数
无
#### 返回参数
返回示例
```
//成功
{
  "code": 0,
  "msg": "ok",
  "data": [
    {"pid": "12345678910","title": "问卷", "status": 0, "createTime": 1536887397173, "startTime": "2018-09-20", "endTime": "2018-10-01"},
    {"pid": "22345678910","title": "问卷标题", "status": 1, "createTime": 1536887397666, "startTime": "2018-09-10", "endTime": "2018-10-01"},
    {"pid": "32345678910","title": "问题", "status": 2, "createTime": 1536887397888, "startTime": "2018-09-10", "endTime": "2018-09-12"},
    {"pid": "42345678910","title": "标题", "status": 0, "createTime": 1536887397173, "startTime": "", "endTime": ""}
  ]
}
//失败
{ 
  "code": 1,
  "msg": "server exception"
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
pid    |int|Y         | -          |问卷ID
title |String|Y         | -          |问卷标题
status|int   |Y         | -          |问卷状态：0.未发布，1.已发布，2.已结束
createTime|long  |Y         | -          |问卷创建时的时间戳
startTime  |String|Y         | -          |问卷开达日期，若未设置则是空字符串
endTime    |String|Y         | -          |问卷结束日期，若未设置则是空字符串

### 2.2 查看问卷
#### 接口地址
```
/paper/view-paper
```
#### 请求方式
HTTP	POST
#### 请求示例
```
  "pid": 123456
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
pid    |int|Y         | -         | 问卷id

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": {
   "pid": "4askfj1093jfi9348oueir932",
   "title": "你幸福吗的调查",
   "status": 0,
   "createTime": 1536887397173,
   "startTime": "2018-09-12",
   "endTime": "2018-10-01",   
   "questions": [
      {"qid": "1234", "questionType":1, "questionTitle": "你的收入是多少？", "questionOption": ["2000以下", "2000-5000", "5000+"]},
      {"qid": "2234", "questionType":2, "questionTitle": "你家里有哪些家电？", "questionOption": ["冰箱", "洗衣机", "空调", "麻将机"]},
      {"qid": "3234", "questionType":3, "questionTitle": "说一说你觉得最幸福的事", "questionOption": []}
    ]
   }
}
```
> #### data参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
pid    |int|Y         | -          |问卷ID
title |String|Y         | -          |问卷标题
status|int   |Y         | -          |问卷状态：0.未发布，1.已发布，2.已结束
createTime|long  |Y          | -          |问卷创建时的时间戳
startTime  |String|Y         | -          |问卷开达日期，若未设置则是空字符串
endTime    |String|Y         | -          |问卷结束日期，若未设置则是空字符串
questions  |Array |Y         | -          |问题列表     

> #### questions参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
qid    |int|Y         | -          |问题ID
questionType  |int   |Y      | -     |问题类型：1.单选题，2.多选题，3.简答题
questionTitle |String|Y      | -     |问题标题
questionOption|Array |Y      | -     |问题选项：简答题为空的Array


### 2.3 新增问卷
#### 接口地址
```
//与更新问卷共用接口
/paper/update-paper
```
#### 请求方式
HTTP	POST
#### 请求示例
```
{
  "title": "你幸福吗的调查",
  "startTime": "2018-09-12",
  "endTime": "2018-10-01",
  "status": 0,
  "questions": [
      {"questionType":1, "questionTitle": "你的收入是多少？", "questionOption": ["2000以下", "2000-5000", "5000+"]},
      {"questionType":2, "questionTitle": "你家里有哪些家电？", "questionOption": ["冰箱", "洗衣机", "空调", "麻将机"]},
      {"questionType":3, "questionTitle": "说一说你觉得最幸福的事", "questionOption": []}
  ]
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
title       |String|Y| 2-64个字符 | 问卷名称
startTime  |String|Y| 10个字符   | 开始日期，若未设置则是空字符串
endTime    |String|Y| 10个字符   | 结束日期，若未设置则是空字符串
status      |int   |Y| 0或1     | 问卷状态，0：不发布仅保存；1：发布（此时start_time和end_time必须有合法取值）；

> #### questions参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
questionType  |int   |Y      | -           |问题类型：1.单选题，2.多选题，3.简答题
questionTitle |String|Y      | 1-128字符   |问题标题
questionOption|Array |Y      | -     |问题选项， 是选择题则至少有两个元素，简答题无元素

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": 0
}
```
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---  |:---      |:---        |:---
code  |int   |Y         | -          |-1.token失效或未登录，0.请求成功，1.系统异常
msg   |String|Y         | -          |提示消息
data  |int   |N         | -          |0.成功

### 2.4 修改问卷
#### 接口地址
```
//与新增问卷共用接口，仅多一个参数id
/paper/update-paper
```
#### 请求方式
HTTP	PUT
#### 请求示例
```
//页面中的数据来自view-paper接口，若管理员选择更新，则删除原id的paper的问题，再为该id的paper插入questions的新题目
{
   "pid": "4askfj1093jfi9348oueir932",   //在add-paper中无此参数
   "title": "你幸福吗的调查",
   "status": 0,
   "startTime": "2018-09-12",
   "endTime": "2018-10-01",   
   "questions": [
      {"questionType":1, "questionTitle": "你的收入是多少？", "questionOption": ["2000以下", "2000-5000", "5000+"]},
      {"questionType":2, "questionTitle": "你家里有哪些家电？", "questionOption": ["冰箱", "洗衣机", "空调", "麻将机"]},
      {"questionType":3, "questionTitle": "说一说你觉得最幸福的事", "questionOption": []}
    ]
   }
}
```
> #### 请求参数
> #### 参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
pid    |int|Y         | -          |问卷ID
title |String|Y         | -          |问卷标题
status|int   |Y         | -          |问卷状态：0.未发布，1.发布
startTime  |String|Y         | -          |问卷开达日期，若未设置则是空字符串
endTime    |String|Y         | -          |问卷结束日期，若未设置则是空字符串
questions  |Array |Y         | -          |问题列表     

> #### questions参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
questionType  |int   |Y      | -     |问题类型：1.单选题，2.多选题，3.简答题
questionTitle |String|Y      | -     |问题标题
questionOption|Array |Y      | -     |问题选项， 是选择题则至少有两个元素，简答题无元素

#### 返回参数
返回示例
```
{
    "code": 0,
    "msg": "ok",
    "data": 0
}
```
> #### data参数说明
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---  |:---      |:---        |:---
 data  |int   |Y         | -          |0.操作成功，1.操作失败，2.paper的id非法（无此问卷)


### 2.5 删除问卷
#### 接口地址
```
/paper/delete-paper
```
#### 请求方式
HTTP	POST
#### 请求示例
```
{
  "pidList": ["4askfj1093jfi9348oueir932", "sfs6f465vfsdf65sf654s6sf"]
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
pidList    |Array|Y         | -         | 问卷id列表，至少一个元素

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": 0
}
```
> #### data参数说明
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---  |:---      |:---        |:---
 data  |int   |Y         | -          |0.操作成功，1.操作失败，2.paper的id非法（无此问卷)



### 2.6 用户查看问卷（答卷页面）
#### 接口地址
```
/paper/view-paper?id=4askfj1093jfi9348oueir932
```
#### 请求方式
HTTP	GET
#### 请求示例
无
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
pid    |int|Y         | -         | 问卷id

#### 返回参数
返回示例
```
{
  "code": 0,
  "msg": "ok",
  "data": {
   "status": 0,     //只有status为1时才可作答
   "pid": "4askfj1093jfi9348oueir932",
   "title": "你幸福吗的调查",   
   "createTime": 1536887397173,
   "startTime": "2018-09-12",
   "endTime": "2018-10-01",
   "questions": [
      {"qid": "1234", "questionType":1, "questionTitle": "你的收入是多少？", "questionOption": ["2000以下", "2000-5000", "5000+"]},
      {"qid": "2234", "questionType":2, "questionTitle": "你家里有哪些家电？", "questionOption": ["冰箱", "洗衣机", "空调", "麻将机"]},
      {"qid": "3234", "questionType":3, "questionTitle": "说一说你觉得最幸福的事", "questionOption": []}
    ]
   }
}
```
> #### data参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
status|int   |Y         | -          |问卷状态：0.未发布，1.发布中（可作答），2.已结束，3.无此问卷，4.已发布但未到开始时间
pid    |int|N         | -          |问卷ID
title |String|N         | -          |问卷标题
createTime|long  |N          | -          |问卷创建时的时间戳
startTime  |String|N         | -          |问卷开达日期，若未设置则是空字符串
endTime    |String|N         | -          |问卷结束日期，若未设置则是空字符串
questions  |Array |N         | -          |问题列表     

> #### questions参数说明
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
qid    |int|Y         | -          |问题ID
questionType  |int   |Y      | -     |问题类型：1.单选题，2.多选题，3.简答题
questionTitle |String|Y      | -     |问题标题
questionOption|Array |Y      | -     |问题选项， 是选择题则至少有两个元素，简答题无元素

> #### 补充说明
1. status为必须参数
2. 若status为0或3，则data中除status外无其他参数
3. 若status为1，data中包含全部参数，用户可正常作答
4. 若status为2或4，data中只包含title、startTime、endTime，用于提示用户


### 2.7 提交问卷答案
#### 接口地址
```
/answer/commit-paper
```
#### 请求方式
HTTP	POST
#### 请求示例
```
//页面中的数据来自view-paper接口
{
   "pid": "4askfj1093jfi9348oueir932",
   "answers": [
      {"qid": "1234", "questionType":1,  "answerContent": ["2000-5000"]},  //单选题，Array中仅一个元素
      {"qid": "2234", "questionType":2,  "answerContent": ["空调", "麻将机"]},  //多选，Array中至少一个元素
      {"qid": "3234", "questionType":3,  "answerContent": ["上了王者"]}  //简答
    ]
   }
}
```
> #### 请求参数
参数  |	类型	| 是否必须 |	取值范围	| 说明
:---  |:---|:---|:---|:---
pid    |int|Y         | -          |问卷ID
answers  |Array |Y         | -       |答案列表  

> #### answers参数说明
参数           |类型	  | 是否必须 |	取值范围	| 说明
:---           |:---  |:---     |:---          |:---
qid             |int|Y         | -          |问题id
questionType   |int   |Y         | -          |问题类型：1.单选，2.多选，3.简答
answerContent  |-     |Y         | 0-512字符  |答题选项， 是选择题则至少有一个元素，简答题最多一个元素（不答则为无元素）

#### 返回参数
返回示例
```
{
    "code": 0,
    "msg": "ok"
}
```
> #### data参数说明
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---  |:---      |:---        |:---
 code  |int   |Y         | -          |0.操作成功，1.操作失败,2.问卷id无效








### 2.8 查看问卷数据
#### 接口地址
 ```
 /answer/paper-data
 ```
#### 请求方式
HTTP	POST
#### 请求示例
 ```
   "pid": 123
 ```
> #### 请求参数
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---|:---|:---|:---
 pid    |int|Y         | -         | 问卷id

#### 返回参数
返回示例
 ```
 {
   "code": 0,
   "msg": "ok",
   "data": {
    "pid": "4askfj1093jfi9348oueir932",
    "title": "你幸福吗的调查",
    "status": 0,
    "createTime": 1536887397173,
    "startTime": "2018-09-12",
    "endTime": "2018-10-01",   
    "totalCount": 140,
    "questions": [
       {
            "qid": "1234", "questionType":1, 
            "questionTitle": "你的收入是多少？", 
            "questionOption": ["2000以下", "2000-5000", "5000+"],
            "answerContent": [10, 30, 100]
       },
       {    
            "qid": "2234", "questionType":2, 
            "questionTitle": "你家里有哪些家电？", 
            "questionOption": ["冰箱", "洗衣机", "空调", "麻将机"],
            "answerContent": [30, 40, 80, 20]
       },
       {   
            "qid": "3234", "questionType":3, 
            "questionTitle": "说一说你觉得最幸福的事", 
            "questionOption": [],
            "answerContent": [
                "从青铜",
                "到黄金",
                "到王者"              
            ]
       },
       {   
           "qid": "4234", "questionType":3, 
           "questionTitle": "说一说你觉得最难过的事", 
           "questionOption": [],
           "answerContent": [
               "从王者",               
               "到青铜" 
           ]
       }
     ]
    }
 }
 ```
> #### data参数说明
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---|:---|:---|:---
 pid    |int|Y         | -          |问卷ID
 title |String|Y         | -          |问卷标题
 status|int   |Y         | -          |问卷状态：0.未发布，1.已发布，2.已结束
 createTime|long  |Y          | -          |问卷创建时的时间戳
 startTime  |String|Y         | -          |问卷开达日期，若未设置则是空字符串
 endTime    |String|Y         | -          |问卷结束日期，若未设置则是空字符串
 totalCount |int   |Y         | -          |问卷被达总次数（人次）
 questions  |Array |Y         | -          |问题列表     

> #### questions参数说明
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---|:---|:---|:---
 qid    |int|Y         | -          |问题ID
 questionType  |int   |Y      | -     |问题类型：1.单选题，2.多选题，3.简答题
 questionTitle |String|Y      | -     |问题标题
 questionOption|Array |Y      | -     |问题选项，选择题是Array，简答题为空字符串
 answerContent |Array |Y      | -     |答案内容，选择题中的元素为int，简答题为String




# 下面的先不做

### 2.9 下载问卷模板文件
#### 文件地址
```
domain/template.xls
```
#### 使用方式
 ```html
 <a href="url">下载模板</a>
 ```


### 2.10 上传问卷模板文件生成问卷
#### 接口地址
 ```
 domain/api/v1/admin/upload
 ```
#### 请求方式
HTTP	POST
#### 请求示例
 ```
 {
    "file": FILE    //文件
 }
 ```
> #### 请求参数
 参数  |	类型	| 是否必须 |	取值范围	| 说明
 :---  |:---|:---|:---|:---
 file  |FILE|Y         | -          |文件模板文件(Excel)



#### 返回参数
返回示例
 ```
 {
     "code": 0,
     "msg": "ok"
 }
 ```
> #### data参数说明
  参数  |	类型	| 是否必须 |	取值范围	| 说明
  :---  |:---  |:---      |:---        |:---
  code  |int   |Y         | -          |0.操作成功，1.系统异常，2.文件不合法

> #### 常见返回值情形
  code  | msg   
  :---  |:---   |
  -1    | 账号未登录或登录已经失效
  0     | ok     
  1     | 异常的具体信息
  2     | 未选择文件！   
  2     | 文件类型不支持！
  2     | 文件大小限制在100KB以内！
  2     | 文件转换失败，请注意格式要求！

  

