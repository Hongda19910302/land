{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"user":{
"userId":"${(user.userId)!'0'}",
"account":"${(user.account)!''}",
"userName":"${(user.name)!''}",
"companyId":"${(user.company.companyId)!'0'}",
"companyName":"${(user.company.companyName)!''}",
"topDepartmentId":"${(user.company.getTopDepartment().departmentId)!'0'}",
"topDepartmentName":"${(user.company.getTopDepartment().name)!''}",
"post":"${(user.role.roleName)!''}",
"sex":"<#if (user.sex)?? && user.sex==0>女<#else>男</#if>",
"email":"${(user.email)!''}",
"state":"${(user.status)!''}",
"phone":"${(user.phone)!''}",
"mobile":"${(user.tel)!''}",
"address":"${(user.addr)!''}",
"authority":"${(user.getAuthority())!''}"
}

}