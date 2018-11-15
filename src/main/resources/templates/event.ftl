<#import "parts/common.ftl" as c>
<#include "parts/Security.ftl">
<@c.page>
<div class="tm-middle" xmlns="http://www.w3.org/1999/html">
    <div class="uk-container uk-container-center">
        <div class="uk-grid" data-uk-grid-margin>
            <div class="uk-width-1-3" style="padding-right: 50px;">

                <article class="uk-article" uk-sticky>

                    <h1 class="uk-article-title" style="padding-top: 20px"><a class="uk-link-reset" href="">Showcase</a>
                    </h1>
                    <h3 class="uk-text-large" style="border-bottom: 1px solid #c3c3c3;padding-bottom: 20px;">A fine
                        collection of websites and themes built with the UIkit framework.</h3>

                    <h2>Suggest a site</h2>
                    <p>You created new Event? Let us know :)</p>
                    <div class="uk-grid-small uk-child-width-auto" uk-grid>
                        <div>
                            <a class="uk-button uk-button-text" href="/addEvent">Add new Event</a>
                        </div>
                    </div>
                    <div>
                        <#if isAdmin>
                            <form class="form-inline" style="padding-top: 20px">
                                <button class="uk-button uk-button-default " name="conf" value="true">Not confirm
                                </button>
                                <button class="uk-button uk-button-default " name="all" value="false">All</button>
                            </form>
                        </#if>
                    </div>

                </article>
            </div>
            <div class="uk-width-1-2" style="padding-left: 50px;
        border-left: 1px solid #c3c3c3;">
<#list events as event>
<#if event.confirm||isAdmin==true>
    <article class="uk-article" style="border-bottom: 1px solid #c3c3c3;">
        <figure class="uk-overlay">
            <h1 class="uk-article-title uk-float-left">${event.name}</h1>


            <div class="uk-inline-clip uk-transition-toggle uk-light" tabindex="0"
                 uk-scrollspy="cls: uk-animation-slide-right; repeat: true">
                <img src="/img/${event.filename}" height="1200" width="1600">
                <div class="uk-transition-fade uk-position-cover uk-overlay uk-overlay-primary uk-flex uk-flex-center uk-flex-middle">
                    <a class="uk-button uk-button-text uk-transition-fade " href="/event/${event.id}">Full Details</a>
                </div>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </figure>
    </article>
</#if>
<#else>
    <h1 class="uk-article-title uk-position-relative uk-position-center">No Events</h1>
</#list>
            </div>
        </div>
    </div>
</div>
</@c.page>