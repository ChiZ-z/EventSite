<#import "parts/common.ftl" as c>

<@c.page>
All users
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Role</th>
        <th></th>
    </tr>
    </thead>
    <tbode>
    <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td><#list user.roles as role>${role}<#sep>, </#list></td>
            <td><a href="/user/${user.id}">edit</a> </td>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </tr>
    </#list>
    </tbode>
</table>
</@c.page>