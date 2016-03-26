这是给一个大学生写的，代码段，功能也很简单

android客户端实现了用户的注册，登陆和修改密码

android客户端也能读取手机上的通讯录，用户可以选择其中的某些或全部通讯录上传的服务器，然后用户在其他手机登陆上之后，就可以将以前上传的通讯录获取到

这里就显示一下接口的调用

#运行: 
```shell
➜  api git:(master) ✗ ~/py27env/bin/python2.7 app.py&
[1] 70196
```

#地址不存在时:
```shell
➜  api git:(master) ✗ curl http://localhost:8001
{
  "error": "Not found"
}%
```

#注册:
```shell
➜  api git:(master) ✗ curl -d mobile='18612345678' -d username='xxx' -d password='123456' -X POST http://localhost:8001/register
{
  "register": "success"
}%
```

#注册失败

```shell
➜  ~ curl -d mobile='18612345678' -d username='xxx' -d password='123456' -X POST http://localhost:8001/register
{
  "register": "error, already exists" //失败，因为用户已存在
}%
```

#登陆 (注意token字段)

```shell
➜  api git:(master) ✗ curl -d mobile='18612345678' -d password='123456' -X POST http://localhost:8001/login
{
  "user": {
    "mobile": "18612345678",
    "token": "MTg2MTIzNDU2Nzg6MTIzNDU2\n",
    "username": "xxx"
  }
}%```

#获取通讯录（不带token）
```shell
➜  api git:(master) ✗ curl -X GET http://localhost:8001/contacts
{
  "error": "Unauthorized access"  //报错
}%
```
#获取通讯录（带token）
```shell
➜  api git:(master) ✗ curl -H "Authorization: Basic MTg2MTIzNDU2Nzg6MTIzNDU2" -X GET http://localhost:8001/contacts
{
  "contacts": []   //未上传通讯录，所以获取肯定是空数组
}%
```

#上传通讯录（带token）
```shell
➜  api git:(master) ✗ curl -H "Authorization: Basic MTg2MTIzNDU2Nzg6MTIzNDU2" -d contacts="小红,18698723498;小明,18687638667" -X POST http://localhost:8001/contacts
{
  "upload": "success"
}%
```

#获取通讯录（带token）
```shell
➜  api git:(master) ✗ curl -H "Authorization: Basic MTg2MTIzNDU2Nzg6MTIzNDU2" -X GET http://localhost:8001/contacts
{
  "contacts": [
    {
      "id": 1,
      "name": "\u5c0f\u660e",
      "number": "18687638667"
    },
    {
      "id": 2,
      "name": "\u5c0f\u7ea2",
      "number": "18698723498"
    }
  ]
}%
```
#再次上传（带token）
```shell
➜  api git:(master) ✗ curl -H "Authorization: Basic MTg2MTIzNDU2Nzg6MTIzNDU2" -d contacts="Bob,1869897878;lily,18689878667" -X POST http://localhost:8001/contacts
{
  "upload": "success"
}%
```
#再次获取（带token）
```shell
➜  api git:(master) ✗ curl -H "Authorization: Basic MTg2MTIzNDU2Nzg6MTIzNDU2" -X GET http://localhost:8001/contacts
{
  "contacts": [
    {
      "id": 1,
      "name": "\u5c0f\u660e",
      "number": "18687638667"
    },
    {
      "id": 2,
      "name": "\u5c0f\u7ea2",
      "number": "18698723498"
    },
    {
      "id": 3,
      "name": "Bob",
      "number": "1869897878"
    },
    {
      "id": 4,
      "name": "lily",
      "number": "18689878667"
    }
  ]
}%
```

android端也就是调接口，登陆之后将token存在本地，调需要用户权限的接口时，将token放在http头中带上
