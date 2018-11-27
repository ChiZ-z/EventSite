<#include "Security.ftl">
<#import "login.ftl" as l>
<nav class="uk-navbar  " style="background-color: #c3c3c3" uk-navbar >
    <div class="uk-navbar-left">
        <ul class="uk-navbar-nav">
            <li>
                <a href="/">
                    <div>
                        <div class="uk-logo uk-button uk-button-text">Eventime</div>
                    </div>
                </a>
            </li>
            <li>
                <a href="/event">
                    <div>
                        <div class="uk-navbar-subtitle uk-button uk-button-text">Showcase</div>
                    </div>
                </a>
            </li>
            <li>
                <a href="/addEvent">
                    <div>
                        <div class="uk-navbar-subtitle uk-button uk-button-text">Add Event</div>
                    </div>
                </a>
            </li>
            <#if isAdmin>
            <li>
                <a href="/user">
                    <div>
                        <div class="uk-navbar-subtitle uk-button uk-button-text">User list</div>
                    </div>
                </a>
            </li>
            </#if>
        </ul>
        <form class="form-inline">
            <input type="text" name="filter" class="uk-input uk-form-width-small" value="${filter?ifExists}"
                   placeholder="Search by tag">
        </form>

    </div>
    <div class="uk-navbar-right">
        <a class="" href="/user/userProfile/${id}" uk-icon="icon: user"></a>
        <#if isEnabled>
                    <div class="uk-navbar-subtitle">${name}</div>
        </#if>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <#if isEnabled==false>
    <button class="uk-button uk-button-default uk-margin-small-left uk-margin-medium-right" type="submit">Sign In
    </button>
    <#else>
    <button class="uk-button uk-button-default uk-margin-small-left uk-margin-medium-right" type="submit">Sign Out
    </button>
    </#if>
        </form>
        </li>
        </ul>
    </div>
</nav>