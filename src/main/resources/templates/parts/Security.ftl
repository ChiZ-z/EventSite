<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>
<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    id = user.getId()
    name = user.getUsername()
    isAdmin = user.isAdmin()
    isEnabled =user.isEnabled()
    >
<#else>
    <#assign
    id="null"
    name = "unknown"
    isAdmin = false
    isEnabled=false
    >
</#if>