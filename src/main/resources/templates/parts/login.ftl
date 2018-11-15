<#include "Security.ftl">
<#macro login path isRegisterForm>
<form action="${path}" method="post" style="padding-top: 200px">
    <div class="uk-container-center">
        <div class="uk-grid-margin uk-grid uk-grid-stack" uk-grid>
            <div class="uk-width-1-1@m">
                <div class="uk-margin uk-width-large uk-margin-auto uk-card uk-card-default uk-card-body uk-box-shadow-large">
                    <h3 class="uk-card-title uk-text-center"><#if isRegisterForm>Sign up today. It's free!<#else>
                        Welcome!</#if></h3>
                    <form>
                        <div class="uk-margin">
                            <div class="uk-inline uk-width-1-1">
                                <span class="uk-form-icon" uk-icon="icon: user"></span>
                                <input type="text" name="username" class="uk-input uk-form-large"
                                       placeholder="User name"/>
                            </div>
                        </div>
                    <#if isRegisterForm>
                                <div class="uk-margin">
                                    <div class="uk-inline uk-width-1-1">
                                        <span class="uk-form-icon" uk-icon="icon: user"></span>
                                        <input type="email" name="email" class="uk-input uk-form-large"
                                               placeholder="Email"/>
                                    </div>
                                </div>
                    <#else>

                    </#if>
                        <div class="uk-margin">
                            <div class="uk-inline uk-width-1-1">
                                <span class="uk-form-icon" uk-icon="icon: lock"></span>
                                <input type="password" name="password" class="uk-input uk-form-large"
                                       placeholder="Password"/>
                            </div>
                        </div>
                        <div class="uk-margin">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button class="uk-button uk-button-primary uk-button-large uk-width-1-1"><#if isRegisterForm>
                                Create an account<#else>Sign In</#if></button>
                        </div>
                        <div class="uk-text-small uk-text-center"><#if !isRegisterForm>Not registered? <a
                                href="/registration">Create an account</a><#else>Already have an account? <a
                                href="/login">Log in</a></#if>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</form>
</#macro>

<#macro logout>

</#macro>
