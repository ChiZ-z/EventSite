<#import "parts/common.ftl" as c>
<#include "parts/Security.ftl">
<@c.page>
<#--
<form action="/event/${event.id}" method="post">
    <#if isAdmin>
    <div>
        <input type="text" name="id" value="${event.id}">
        <input type="text" name="tag" value="${event.tag}">
    </div>
    <#else>
    <label>${event.id}</label>
    <label>${event.tag}</label>
    </#if>
    <#if isEnabled==false>
    <input type="text" name="guistName" value="">
    </#if>
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit">I GO!</button>

</form>
-->





    <div class="tm-middle" xmlns="http://www.w3.org/1999/html">
        <div class="uk-container uk-container-center" style="padding-top: 50px">
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-1-3" style="padding-right: 20px;border-right: 1px solid #c3c3c3;">

                    <article class="uk-article">
                        <figure class="uk-overlay">
                            <h1 class="uk-article-title uk-float-left ">${event.name}</h1>


            <#if event.filename??>
            <img src="/img/${event.filename}" width="600" style="border-radius: 15px;"
                 class="card-img-top uk-margin-medium-bottom">
            </#if>
                            <#if event.confirm==false&&isAdmin>
                                 <form name="eventConfirm" action="/events/${event.id}/confirm" method="post">
                                     <button class="uk-button uk-button-default  ">confirm</button>
                                     <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                 </form>
                            </#if>

                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        </figure>
                    </article>
                </div>
                <div class="uk-width-1-2" style="padding-left: 50px;">

                    <article class="uk-article">
                        <figure class="uk-overlay">
                            <div class="uk-grid-collapse uk-child-width-expand@s uk-text-center" uk-grid>
                                <div class="uk-text-lead"> Author Name:</div>
                                <div class="uk-text-lead"> ${event.authorName}</div>
                                <div class="uk-text-lead"> Tag:</div>
                                <div class="uk-text-lead"> ${event.tag}</div>
                                <div class="uk-text-lead"> Author Name:</div>
                                <div class="uk-text-lead"> ${event.authorName}</div>
                            </div>
                            <#if !isEnabled>
                            <form name="eventPage2" action="/event/${event.id}" method="post">
                            <div class="uk-margin">

                                <div uk-alert><h3>You didn't create Account!</h3>If you want to go to this event you can
                                    input your Email <input class="uk-input " type="text" name="nameGuist"
                                                            placeholder=""></div>
                            </div>

                            <div class="uk-position-relative uk-background-muted	uk-position-center uk-animation-fade">

                                    <button class="uk-button uk-button-default  ">I GO</button>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>


                            </div>
                            </form>
                            <#else >
                             <div class="uk-position-relative uk-background-muted	uk-position-center uk-animation-fade">
                                 <form name="eventPage2" action="/event/${event.id}" method="post">
                                     <button class="uk-button uk-button-default  ">I GO</button>
                                     <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                                 </form>
                             </div>
                            </#if>

                        </figure>

                    </article>

                </div>
            </div>
            <article class="uk-comment">
                <header class="uk-comment-header uk-grid-medium uk-flex-middle" uk-grid>
                    <div class="uk-width-auto">
                        <img class="uk-comment-avatar" src="images/avatar.jpg" width="80" height="80" alt="">
                    </div>
                    <div class="uk-width-expand">
                        <h4 class="uk-comment-title uk-margin-remove"><a class="uk-link-reset" href="#">Author</a></h4>
                        <ul class="uk-comment-meta uk-subnav uk-subnav-divider uk-margin-remove-top">
                            <li><a href="#">12 days ago</a></li>
                            <li><a href="#">Reply</a></li>
                        </ul>
                    </div>
                </header>
                <div class="uk-comment-body">
                    <p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</p>
                </div>
            </article>
        </div>

    </div>


</@c.page>


