<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<#assign error=event!"null"/>
<@c.page>
    <@l.login "/registration" true />
    ${event?ifExists}
</@c.page>