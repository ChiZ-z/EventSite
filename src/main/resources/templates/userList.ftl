<#import "parts/common.ftl" as c>

<@c.page>
<div class="uk-container uk-container-center" style="padding-top: 50px">
    <fieldset class="uk-fieldset">
        <h1 class="uk-position-relative uk-position-center">User List</h1>
        <table class="uk-table uk-table-small uk-table-divider">
            <thead>
            <tr>
                <th>Name</th>
                <th>Role</th>
                <th>Active</th>
                <th>Edit</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>
                    ${role.role}
                    <#sep>,
                </#list>
                </td>
                <td>${user.active?then('Active','Deleted')}</td>
                <td><a class="uk-button uk-button-text" href="/user/${user.id}">edit</a></td>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </tr>
            </#list>
            </tbody>
        </table>
    </fieldset>
</div>
</@c.page>