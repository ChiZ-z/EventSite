<#import "parts/common.ftl" as c>
<@c.page>
<div class="uk-container uk-container-center" style="padding-top: 50px">
    <form action="/user" method="post">
        <fieldset class="uk-fieldset">
            <h1 class="uk-position-relative uk-position-center">New Event</h1>
            <div class="uk-margin">
                <h4>Username<input class="uk-input" type="text" name="username" value="${user.username}"></h4>

            </div>
            <div class="uk-margin">
                <input class="uk-input" type="email" name="email" value="${user.getEmail()}">
            </div>
                <#list roles as role>
                <div class="uk-margin">
                    <div class="uk-form-label"></div>
                    <div class="uk-form-controls">
                        <label><input class="uk-radio" type="radio" name="radio1"  ${user.roles?seq_contains(role)?string("checked", "")} value="${role.role}"> ${role.role}</label>
                    </div>
                </div>

                </#list>
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <div class="uk-position-relative uk-position-center">
                <button class="uk-button uk-button-default" type="submit">Save</button>
            </div>
    </form>
</div>
</@c.page>