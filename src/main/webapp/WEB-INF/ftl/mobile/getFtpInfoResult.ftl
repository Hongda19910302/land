{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"ftp":{
"hostname": "${ftpUtils.ip!''}",
"port": ${ftpUtils.port!21},
"username": "${ftpUtils.account!''}",
"password": "${ftpUtils.password!''}"
},
"picName": "${picNames!''}",
"tempPath": "/${tempPath!''}",
"realPath": "/${realPath!''}"
}
