{
"result":"${r.result?default('')}",
"describe":"${r.describe?default('')}",
"user":{
"userId":"${(user.userId)!'0'}",
"account":"${(user.account)!''}",
"userName":"${(user.name)!''}",
"companyId":"${(user.companyId)!'0'}",
"companyName":"${(user.findCompany.companyName)!''}",
"departmentId":"${(user.findDepartment.departmentId)!''}",
"departmentName":"${(user.findDepartment.name)!''}",
"topDepartmentId":"${(user.findCompany.getTopDepartment().departmentId)!'0'}",
"topDepartmentName":"${(user.findCompany.getTopDepartment().name)!''}",
<#--"post":"${(user.role.roleName)!''}",-->
"post":"${(user.backRrole.backRoleName)!''}",
"sex":"<#if (user.sex)?? && user.sex==0>女<#else>男</#if>",
"email":"${(user.email)!''}",
"state":"${(user.status)!''}",
"phone":"${(user.phone)!''}",
"mobile":"${(user.tel)!''}",
"address":"${(user.addr)!''}",
"authority":"${(user.getThority())!''}"
}

}